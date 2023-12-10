package Lab_5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;

/**
 * client
 */
public class client {

    int ServerPort = 30000;
    JFrame frame = new JFrame();
    builder create = new builder();
    Socket clientSocket = null;
    String username = "";
    String towho = "";
    int userId;

    public static void main(String[] args) {
        new client();
    }

    public client() {
        // creating the client frame
        create.Clientframe(frame, 350, 450);
        create.consoleField.setText("Connecting to the server...\n");
        ServerConnection();
    }

    public void ServerConnection() {
        // message getter
        try {
            // printing that the user is connected to the server
            clientSocket = new Socket("localhost", ServerPort);
            create.consoleField.append("Connected to the server! \n");

            // closing before moving on

            // defaults
            // create.usernamefield.setText("" + clientSocket.getLocalPort());
            create.toWhofield.setText("ALL");
            username = create.usernamefield.getText();

            // the thread that gets the message
            Thread messagegetterThread = new Thread(() -> {
                // getting the client input stream
                try (Scanner scans = new Scanner(clientSocket.getInputStream())) {
                    while (scans.hasNextLine()) {
                        username = create.usernamefield.getText();
                        String themessage = scans.nextLine();
                        System.out.println(themessage);
                        create.areatxt.append(themessage + "\n");

                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    scans.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // starting the messgae getter
            messagegetterThread.start();

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            // sending user id on connections.
            // while true that we are connected
            System.out.println(clientSocket.getLocalPort());
            while (true) {
                // printing a local port
                // sending the message
                // actionlistener send
                create.send.addActionListener(new ActionListener() {
                    // the methord
                    public void actionPerformed(ActionEvent e) {
                        towho = create.toWhofield.getText();
                        towho = towho.toLowerCase().trim();
                        Scanner clientMessage = new Scanner(create.messageField.getText());
                        username = create.usernamefield.getText();
                        writer.println(username + "_username_" + clientSocket.getLocalPort()); // username and client

                        // username to which we are sending to
                        String tothisperson = create.toWhofield.getText();
                        tothisperson = tothisperson.toLowerCase().trim();

                        //
                        if (!(username.equals(""))) {
                            // if username is not empty.
                            if (!(clientMessage.equals(""))) {
                                String message = clientMessage.nextLine();
                                writer.println(username + ":>" + message + "<>__ " + tothisperson);
                                create.messageField.setText("");
                            } else {
                                // nothing happens
                                System.out.println("Waiting");
                            }
                        } else {
                            System.out.println("Input username, please!");
                        }

                    }
                });
            }

            // the closing after use
            // clientMessage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}