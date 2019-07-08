package UDPRealChat;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Peer1 {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        int port = 9999;
        DatagramSocket ds = new DatagramSocket(port);
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String text = in.nextLine();
                        byte[] message = text.getBytes();
                        InetAddress local = InetAddress.getLocalHost();
                        DatagramPacket dp = new DatagramPacket(message, message.length, local, 9998);
                        ds.send(dp);
                    } catch (UnknownHostException ex) {
                        System.out.println(ex);
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
            }

        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    byte[] res = new byte[2048];
                    DatagramPacket dp1 = new DatagramPacket(res, res.length);
                    try {
                        ds.receive(dp1);
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                    String received = new String(res);
                    System.out.println("P2: " + received);
                }
            }

        };
        t1.start();
        t2.start();
    }
}
