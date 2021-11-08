package client;

import lib.message.Message;

import java.io.*;
import java.net.Socket;

public class ClientUtil {
    private static Message deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
    System.out.println("Десериализация байтов");
    return (Message) ois.readObject();
}

    public static Message receive(Socket socket) throws IOException{
        try {
            byte[] tempbytes = new byte[10000];
            System.out.println("Read bytes.");
            socket.getInputStream().read(tempbytes);
            return deserialize(tempbytes);
        }catch (ClassNotFoundException ex){
            System.out.println("ээЭЭЭЭэЭЭЭЭЭЭЭЭЭЭЭЭЭЭээЭЭ Ё;");
            return null;
        }
    }
    private static byte[] serialize(Message msg) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(msg);
            return baos.toByteArray();
        }
    }

    public static void send(Socket socket, Message msg) throws IOException{
            socket.getOutputStream().write(serialize(msg));
            socket.getOutputStream().flush();
            System.out.println("Sended message.");
    }

}
