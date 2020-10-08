package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 19527);
        final SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(socketAddress);

        try (Scanner scanner = new Scanner(System.in)) {
            final ByteBuffer inputBuf = ByteBuffer.allocate(2 << 10);

            String msg;
            System.out.println("Введите строку для удаления пробелов");
            msg = scanner.nextLine();
            socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));

            int bytesCount = socketChannel.read(inputBuf);
            System.out.println("Исправленная строка: " +
                    new String(inputBuf.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
            inputBuf.clear();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            socketChannel.close();
        }
    }
}