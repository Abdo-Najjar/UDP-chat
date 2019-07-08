package UDPRealChat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Peer2 {

    public static void main(String[] args) throws Exception {
        int port = 9998;
        DatagramSocket ds = new DatagramSocket(port);
        Scanner in = new Scanner(System.in);
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        byte[] reserverMessage = new byte[2048];
                        DatagramPacket dp = new DatagramPacket(reserverMessage, reserverMessage.length);
                        ds.receive(dp);
                        String message = new String(dp.getData());
                        System.out.println("P1: " + message);
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
                    try {
                        InetAddress ip = InetAddress.getLocalHost();
                        String message = in.nextLine();
                        byte[] res = message.getBytes();
                        DatagramPacket dp1 = new DatagramPacket(res, res.length, ip, 9999);
                        ds.send(dp1);
                    } catch (UnknownHostException ex) {
                        System.out.println(ex);
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
            }

        };
        t1.start();
        t2.start();

    }
}
