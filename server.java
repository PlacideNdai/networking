package Lab_5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JFrame;

/**
 * server
 */
public class server {
    builder create = new builder();
    JFrame frame = new JFrame();
    Socket clienSocket = null;
    int Port = 30000;
    String plusLine = "\n_________________________________________________";
    static int UserID;

    public static void main(String[] args) {
        new server();
    }

    public server() {
        // creating frame for the sevrer
        create.Serverframe(frame, 350, 500);
        muiltServerMethod();
    }

    static Hashtable<Integer, PrintWriter> writerlist = new Hashtable<>();
    static Hashtable<String, PrintWriter> hashtableName = new Hashtable<>();
    static String clientname;

    public void muiltServerMethod() {
        create.console("Setting up the server");
        try (
                ServerSocket serverSocket = new ServerSocket(Port);) {
            // after connections
            String str1 = ("[] Lauching server... \n[Server] Server lauched!");
            create.textarea.setText(str1 + plusLine);
            // connected here.
            while (true) {
                // connecting with the client
                clienSocket = serverSocket.accept();
                UserID = clienSocket.getPort();

                create.textarea.append("\nClient: " + clienSocket + " connected!" + plusLine);
                // keeping track of all client by adding them to the list
                PrintWriter writer = new PrintWriter(clienSocket.getOutputStream(), true);
                writerlist.put(UserID, writer);

                // System.out.println(writerlist); // printing elements in the hashtable
                // multserver things
                ClientHandler clientHandler = new ClientHandler(clienSocket);
                new Thread(clientHandler).start();
            }

            // code goes above for defaults in the server.
        } catch (IOException e) {
            // on error
            String str = ("[] Failled to lauch the server");
            e.printStackTrace();
            create.console("[] Failled to lauch the server");
            create.textarea.append(str);
        }
    }

    // _________________client handler _________________//
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        // server server = new server();

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try (
                    BufferedReader readerHa = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter writerHa = new PrintWriter(clientSocket.getOutputStream());) {

                // reading the messages from the different clients
                String inputString;

                while ((inputString = readerHa.readLine()) != null) {
                    // the reading
                    // System.out.println(inputString);
                    // sending the message to all clients
                    String[] usernameProcessor = null;

                    if (inputString.contains("_username_")) {
                        // spliting
                        String[] usernameProcesses = inputString.split("_username_");
                        String clientNameString = usernameProcesses[0].trim(); // username
                        int userIDFromClient = Integer.parseInt(usernameProcesses[1].trim()); // userID
                        if (!(hashtableName.containsKey(clientNameString))) {
                            hashtableName.put(clientNameString, writerlist.get(userIDFromClient));
                            System.out.println(hashtableName);
                        }

                    } else if (inputString.contains("___")) {
                        usernameProcessor = inputString.split(" ");
                        clientname = usernameProcessor[0];
                        // checking if user id is in the hashtable and if it is then replace the key
                        // with the username

                    } else if (inputString.contains("__")) {
                        String[] toWhoProcess = inputString.split("<>");
                        String[] lastTrim = toWhoProcess[1].split(" ");
                        if (lastTrim[1].equals("all")) {
                            shareToall(toWhoProcess[0]);
                        } else {
                            toSomeone(toWhoProcess[0], lastTrim[1]);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    System.out.println(clientSocket + " Disconnected");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void shareToall(String message) {
            Enumeration<PrintWriter> writers = writerlist.elements();
            while (writers.hasMoreElements()) {
                PrintWriter writer = writers.nextElement();
                writer.println(message);
            }
        }

        public void toSomeone(String message, String toThisPerson) {
            PrintWriter thePerson = hashtableName.get(toThisPerson);
            if (thePerson != null) {
                thePerson.println(message);
            } else {
                System.out.println(thePerson + "Person not found!");
            }
        }
    }

}