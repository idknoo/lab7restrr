package server;

import lib.message.Message;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerUtil {
    private static byte[] serialize(Message msg) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(
                ObjectOutputStream oos = new ObjectOutputStream(baos);
        )
        {
            oos.writeObject(msg);
            return baos.toByteArray();
        }
    }
    public static void send(SocketChannel channel, Message msg) throws IOException{
        channel.write(ByteBuffer.wrap(serialize(msg)));
    }
    private static Message deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (Message) ois.readObject();
        }
    }

    public static Message receive(SocketChannel channel) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ByteBuffer messageBuf = ByteBuffer.allocate(1024 * 32);
            channel.read(messageBuf);
            do {
                messageBuf.flip();

                while (messageBuf.hasRemaining()) {
                    baos.write(messageBuf.get());
                }
                messageBuf.clear();

            } while (channel.read(messageBuf) > 0);

            return deserialize(baos.toByteArray());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
