package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lib.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Server {
    private static Integer port;
    private static Selector selector;
    public static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private CollectionWorkerImpl collectionWorker;
    private static ServerSocketChannel channel;
    private static Database database;
    public static final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    public static final ForkJoinPool forkJoinPool2 = ForkJoinPool.commonPool();
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(1);
    private static boolean selectorIsClosed = false;

    public Server(CollectionWorkerImpl serverCollectionWorker) {
        this.collectionWorker = serverCollectionWorker;
    }

    private static void checkConsole(CollectionWorkerImpl scw) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                String command = bufferedReader.readLine();
                if (command.equals("exit")) {
                    bufferedReader.close();
                    selector.close();
                    threadPool.shutdown();
                    forkJoinPool.shutdown();
                    log("WARNING: Все потоки закрыты.");
                    channel.close();
                    log("WARNING: Сетевой канал закрыт.");
                    log(" Завершение работы.");
                    System.exit(0);
                }
            }
        } catch (Exception ignored) {
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {

        Class.forName("org.postgresql.Driver");
        InputStream input = Server.class.getClassLoader().getResourceAsStream("config.properties");

        Properties prop = new Properties();
        prop.load(input);
        String url = prop.getProperty("db.url");
        String user = prop.getProperty("db.user");
        String password = prop.getProperty("db.password");


        log(" Здравствуйте.");
        log("INFO: Настройка всех систем...");
        Scanner scanner = new Scanner(System.in);
        database = null;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        DatabaseUtil databaseUtil = new DatabaseUtil(objectMapper);

        try {
            database = new Database(url, user, password, databaseUtil);
        } catch (SQLException throwables) {
            log("Не удалось подключиться к базе данных. Программа сдохла. Коду плоха.");
            throwables.printStackTrace();
        }
        CollectionWorkerImpl scw = new CollectionWorkerImpl(database);
        log("INFO: Элементы из базы данных успешно загружены в память");

        try {
            log("INFO: Сервер запускается...");
            log("INFO: Введите свободный порт для подключения:");
            port = getPort(scanner);
            InetAddress hostIP = InetAddress.getLocalHost();
            channel = ServerSocketChannel.open();
            selector = Selector.open();
            InetSocketAddress address = new InetSocketAddress("localhost", port);
            channel.configureBlocking(false);
            channel.bind(address);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            log("INFO: Сервер запущен.");


            log("INFO: Сервер готов к работе.");

            boolean selectorIsOffline = false;

            threadPool.execute(() -> {
                while (true) {
                    try {
                        selector.selectNow();
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();


                        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();
                            keyIterator.remove();
                            if (!key.isValid()) {
                                continue;
                            }

                            if (key.isAcceptable()) {
                                log("INFO: Запрос на подключение клиента...");
                                SocketChannel client = channel.accept();
                                client.configureBlocking(false);
                                client.register(selector, SelectionKey.OP_READ);
                                log("INFO: Клиент успешно подключен.");
                            } else if (key.isReadable()) {
                                log("INFO: Попытка чтения из канала...");
                                try {
                                    SocketChannel client = (SocketChannel) key.channel();
                                    Message clientCommand = ServerUtil.receive(client);
                                    log("INFO: Чтение из канала прошло успешно.");
                                    forkJoinPool.execute(new TaskHolder(clientCommand, key, scw, database));
                                } catch (SocketException e) {
                                    log("WARNING: Клиент отключился");
                                    key.cancel();
                                } catch (ClosedSelectorException e) {
                                    if (!selectorIsClosed) {
                                        log("WARNING: Селектор прекращает работу.");
                                        selectorIsClosed = true;
                                        //Чтобы не была неразбериха в консоли
                                    }

                                } catch (Exception e) {
                                    log("ERROR: " + e.toString());
                                    key.cancel();
                                }

                            } else if (key.isWritable()) {
                                key.interestOps(SelectionKey.OP_READ);
                                forkJoinPool2.execute(() -> {
                                    try {
                                        log("INFO: Попытка отправки ответа клиенту...");
                                        Message temp = (Message) key.attachment();
                                        ServerUtil.send((SocketChannel) key.channel(), temp);
                                        log("INFO: Сообщение клиенту успешно отправлено.");
                                    } catch (ClosedSelectorException e) {
                                        if (!selectorIsClosed) {
                                            log("WARNING: Селектор прекращает работу.");
                                            selectorIsClosed = true;
                                            //Чтобы не была неразбериха в консоли
                                        }

                                    } catch (Exception e) {
                                        log("ERROR: " + e.toString());
                                    }
                                });

                            }

                        }
                    } catch (SocketException e) {
                        log("WARNING: Пользователь отключился1.");
                    } catch (ClosedSelectorException e) {
                        if (!selectorIsClosed) {
                            log("WARNING: Селектор прекращает работу.");
                            selectorIsClosed = true;
                            //Чтобы не была неразбериха в консоли
                        }

                    } catch (Exception i) {
                        log("ERROR: " + i.toString());
                    }

                }
            });
            checkConsole(scw);

        } catch (SocketException e) {
            log("WARNING: Пользователь отключился.");
        } catch (ClosedSelectorException e) {
            if (!selectorIsClosed) {
                log("WARNING: Селектор прекращает работу.");
                selectorIsClosed = true;
                //Чтобы не была неразбериха в консоли
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static int getPort(Scanner scanner) {
        int port = -1;
        do {
            try {
                port = Integer.parseInt(scanner.nextLine());
                if (port < 0) {
                    System.out.println("Порт не может быть меньше 0.");
                    System.out.println("Повторите ввод:");
                } else {
                    break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Неправильный формат ввода. Вводите число без пробелов и разделителей.");
                System.out.println("Повторите ввод:");
            }
        } while (port < 0);
        return port;
    }

    private static void log(String message) {
        System.out.println(message);
    }

}
