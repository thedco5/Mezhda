import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Scanner;

public class Client {
    private static int port = 24605;
    public static void main(String[] args) {
        InetAddress address;
        try {
            address = InetAddress.getByName("localhost");
            connect(address);
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "unknown host =(",
                    "! ERROR !",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private static void connect(InetAddress address) {
        try ( Socket socket = new Socket(address, port) ) {
            PrintWriter out;
            BufferedReader in;
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String str = JOptionPane.showInputDialog(
                        null,
                        "What will your username be?",
                        "? USERNAME ?",
                        JOptionPane.QUESTION_MESSAGE);
                out.println(str);
                for (;;) {
                    new Thread(() -> {
                        String answer;
                        for (;;) {
                            try {
                                answer = in.readLine();
                                if (null == answer) {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Lost connection.",
                                            "! LOST SERVER !",
                                            JOptionPane.ERROR_MESSAGE);
                                    System.exit(0);
                                }
                                JOptionPane.showMessageDialog(
                                        null,
                                        new String(Base64.getDecoder().decode(answer)),
                                        "~ INVOICE ~",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                    str = JOptionPane.showInputDialog(
                            null,
                            "Write a message:",
                            "MESSAGE",
                            JOptionPane.PLAIN_MESSAGE
                    );
                    if ("exit".equals(str)) break;
                    str = Base64.getEncoder().encodeToString(str.getBytes());
                    out.println(str);
                    str = JOptionPane.showInputDialog(
                            null,
                            "For who is it:",
                            "RECEIVER",
                            JOptionPane.PLAIN_MESSAGE
                    );
                    if ("exit".equals(str)) break;
                    out.println(str);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            int i = JOptionPane.showConfirmDialog(
                    null,
                    "Server CANNOT be reached",
                    "! SERVERSIDE ERROR !",
                    JOptionPane.OK_CANCEL_OPTION);
            if (i == JOptionPane.CANCEL_OPTION)
                return;
            String str = JOptionPane.showInputDialog(
                    null,
                    "What is the IP address:",
                    "IP",
                    JOptionPane.QUESTION_MESSAGE
            );
            try {
                address = InetAddress.getByName(str);
                connect(address);
            } catch (UnknownHostException e2) {
                JOptionPane.showMessageDialog(
                        null,
                        "Unknown host.",
                        "! ERROR !",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
