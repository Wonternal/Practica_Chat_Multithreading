package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import server.threads.ClientHandler;
import shared.ChatData;
import shared.Constants;


public class ServerApp {
    private static ArrayList<ChatData> mensajes = new ArrayList<>();
    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);

            while (true) {
                System.out.println("========================");
                System.out.println("Esperando por cliente...");
                Socket clientSocket = serverSocket.accept();
                clientSocket.getOutputStream();
                ClientHandler newClient = new ClientHandler(clientSocket, mensajes);
                newClient.start();
            }

            // System.out.println("========================");
            // System.out.println("Cerrando el servidor");
            // serverSocket.close();
        
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void addMessage(ChatData message) {
        mensajes.add(message);
    }
}
