package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 19527));

        System.out.println("Server started!");
        try (SocketChannel socketChannel = serverSocketChannel.accept()) {
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

            while (socketChannel.isConnected()) {
                int byteCount = socketChannel.read(inputBuffer);
                if (byteCount == -1) {
                    break;
                }

                final String msg = new String(inputBuffer.array(), 0, byteCount, StandardCharsets.UTF_8);
                inputBuffer.clear();
                System.out.println("Строка для обработки: " + msg);

                String deleteSpacesMsg = deleteSpaces(msg);
                System.out.println("Исправленная строка: " + deleteSpacesMsg);
                        socketChannel.write(ByteBuffer.wrap((deleteSpacesMsg).getBytes(StandardCharsets.UTF_8)));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    static String deleteSpaces(String msg) {
        return msg.trim().replace(" ", "");
    }
}