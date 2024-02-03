
    import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

    public class TCPClient {
        public static void main(String[] args) {
            String serverIP = "localhost"; // Specify the server IP address here
            int serverPort = 1234; // Specify the server port number here

            try {
                Socket socket = new Socket(serverIP, serverPort);
                System.out.println("Connected to server at " + serverIP + ":" + serverPort);

                BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    System.out.println("Enter command (B for binary, H for hexadecimal, Q to quit):");
                    String command = userInputReader.readLine();

                    if (command.equalsIgnoreCase("Q")) {
                        break;
                    }

                    if (!command.equalsIgnoreCase("B") && !command.equalsIgnoreCase("H")) {
                        System.out.println("Invalid command. Please try again.");
                        continue;
                    }

                    System.out.println("Enter a number:");
                    String number = userInputReader.readLine();

                    if (number.isEmpty()) {
                        System.out.println("Number is missing. Please try again.");
                        continue;
                    }

                    // Send the request to the server
                    out.println(command + " " + number);

                    // Receive and process the server's response
                    String response = in.readLine();

                    if (response.startsWith("200")) {
                        String value = response.substring(4);
                        if (command.equalsIgnoreCase("B")) {
                            System.out.println("Binary value: " + value);
                        } else if (command.equalsIgnoreCase("H")) {
                            System.out.println("Hexadecimal value: " + value);
                        }
                    } else {
                        System.out.println("Error: " + response);
                    }
                }

                // Close the socket
                socket.close();
            } catch (IOException e) {
                System.out.println("Server is down, please try later.");
            }
        }
    }

