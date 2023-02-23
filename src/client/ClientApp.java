package client;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import server.ServerApp;
import shared.ChatData;
import shared.Constants;

public class ClientApp {
    public static void main(String[] args) {

        try {

            InetAddress myIp = InetAddress.getLocalHost();
            Socket socket = new Socket(myIp, Constants.SERVER_PORT);

            Scanner scanner = new Scanner(System.in);
            ObjectOutputStream toServerStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromServerStream = new ObjectInputStream(socket.getInputStream());
            try {
                ArrayList<ChatData> mensajes = (ArrayList<ChatData>) fromServerStream.readObject();
                System.out.println("Historial del chat");
                for (int i = 0; i < mensajes.size(); i++) {
                    System.out.println(mensajes.get(i));
                }
                System.out.println("-------------------");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("¿Cómo te llamas?");
            String name = scanner.nextLine();

            while (true) {
                ChatData chatData = new ChatData();
                chatData.setName(name);

                System.out.println("Introduce el mensaje pal servidor:");
                String message = scanner.nextLine();

                if (message.startsWith("message:")) {
                    chatData.setTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
                    chatData.setMessage(message.substring(8));
                    toServerStream.writeObject(chatData);

                } else {
                    System.out.println("Debes escribir message: antes del mensaje");
                }

                if (message.equals("bye")) {
                    chatData.setTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
                    chatData.setMessage("bye");
                    toServerStream.writeObject(chatData);
                    break;
                }
            }

            toServerStream.close();
            scanner.close();
            socket.close();

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("==================");
            System.out.println("ERROR DE CONEXION!");
            System.out.println("LEVANTA EL SERVER!!");
            System.out.println("==================");
        }
    }
}
