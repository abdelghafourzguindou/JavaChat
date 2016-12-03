/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author zGuindouOS
 */

public class server {

    static private ArrayList<Socket> ClientList = new ArrayList<Socket>();
    static private int ClientNumber = 0;

    public static void main(String[] args) throws IOException {

        ServerSocket SerSock = new ServerSocket(8001);
        try {
            while (true) {

            	Socket sClient = SerSock.accept();

                ClientList.add(sClient);
                ClientNumber++;

                Thread chat_th = new Thread(new Runnable() {

                    private void runChat() throws IOException {

                        try {
                            System.out.println("new client with " + ClientNumber + " like a number");

                            BufferedReader br = new BufferedReader(new InputStreamReader(sClient.getInputStream()));
                            PrintWriter out   = new PrintWriter(sClient.getOutputStream());

                            out.println("\nClient " + ClientNumber + " : Accepted Conexion\n");
                            out.flush();
                            
                            String msg;
                            String str_cl = "Client " + Integer.toString(ClientNumber) + " : ";
                            String str;

                            while (true) {

                                //do {

                                    str = br.readLine();
                                    msg = str_cl + str;
                                //} while (msg != null);

                                    for (Socket sock : ClientList) {
                                        if (sock != sClient) {
                                            PrintWriter outMsg = new PrintWriter(sock.getOutputStream());
                                            outMsg.println(msg);
                                            outMsg.flush();
                                        }
                                    }
                                //}
                            }
                        } finally {
                            sClient.close();
                        }
                    }

                    @Override
                    public void run() {

                        try {
                            runChat();
                        } catch (IOException ex) {
                            System.err.println(ex);
                        }

                    }
                });
                chat_th.start();
            }
        } finally {
            SerSock.close();
        }
    }
}
