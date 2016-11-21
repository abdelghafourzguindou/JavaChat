/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zGuindouOS
 */
public class client {

    public static void main(String[] args) throws UnknownHostException, IOException {

        Socket sock = new Socket("localhost", 8001);
        //try {

            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter pw    = new PrintWriter(sock.getOutputStream());
           
            Thread Recepter = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    while (true) {
                        do {
                            try {
                                msg = br.readLine();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } while (msg != null);
                        if (msg != null) {
                            System.out.println(msg);
                        }
                    }
                }
            });

            Thread Sender = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Scanner sc = new Scanner(System.in);
                        pw.println(sc.nextLine());
                    }
                }
            });

            Recepter.start();
            Sender.start();
        //} finally {
          //  sock.close();
        //}

    }

}
