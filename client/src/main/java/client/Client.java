package client;

import lib.commands.Command;
import lib.commands.ExecuteScript;
import lib.commands.Exit;
import lib.message.CollectionMessage;
import lib.message.CommandMessage;
import lib.message.Message;
import lib.organization.Organization;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Client {
    private static int port;
    public Socket socket;
    private static LocalDateTime time;

    public Client(String host, int port) throws IOException {
        this.port = port;
        this.socket = new Socket(host, port);
    }

    public static void main(String[] args) {
        try {
            boolean isAuthenticated = false;
            Scanner scan = new Scanner(System.in);

            Client client = null;
            while (true) {
                try {
                    System.out.println("Введите порт на котором запущен сервер.");
                    int port = Integer.parseInt(scan.nextLine());
                    client = new Client("localhost", port);
                    break;
                } catch (ConnectException e) {
                    System.out.println("Порт веден неверно");
                }
            }

            CommandListener commandListener = new CommandListener(System.in, false);
            boolean isDone = false;
            do {
                System.out.println("Введите команду.");
                System.out.print("> ");
                Command cmd = commandListener.readCommand();
                if (cmd instanceof Exit) {
                    isDone = true;
                    System.out.println("Оффаем клиент.");
                    scan.close();
                    System.out.println("Завершение работы.");
                    client.socket.close();
                    System.exit(0);
                } else {
                    if (cmd instanceof ExecuteScript) {
                        for (Command c : ((ExecuteScript) cmd).getCommands()) {
                            ClientUtil.send(client.socket, new CommandMessage("больше чем три", c));
                            client.processResponse(ClientUtil.receive(client.socket));
                        }
                    } else {
                        try {
                            ClientUtil.send(client.socket, new CommandMessage("квадратные треугольники", cmd));
                            client.processResponse(ClientUtil.receive(client.socket));
                        } catch (Exception e) {
                            System.out.println("Серверу плоха");
                            System.out.println("Попробуйте переподключиться к серверу позже");
                            System.exit(-1);
                        }
                    }
                }
            } while (!isDone);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void log(String message) {
        time = LocalDateTime.now();
        System.out.println(time.toString() + "\n" + message);
    }

    private void processResponse(Message msg) {
        if (msg instanceof CollectionMessage) {
            for (Organization c : ((CollectionMessage) msg).getOrganizations()) {
                System.out.println(c);
            }
        } else {
            System.out.println(msg.getContent());
        }
    }

}

