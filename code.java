import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    static ServerSocket serverSocket;
    public static void main(String[] args) {
        int port = 1234; // Specify the port number here

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is running and listening on port " + port);
            serverSocket.close();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create input and output streams for the client socket
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Read client's request
                String request = in.readLine();

                // Process the request
                String response = processRequest(request);

                // Send the response back to the client
                out.println(response);

                // Close the client socket
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Server is down, please try later.");
        }
    }

    private static String processRequest(String request) {
        if (request == null || request.isEmpty()) {
            return "500 Request is empty";
        }

        String[] parts = request.split(" ");
        if (parts.length != 2) {
            return "300 Bad request";
        }

        String command = parts[0];
        String number = parts[1];

        if (!command.equalsIgnoreCase("B") && !command.equalsIgnoreCase("H")) {
            return "300 Bad request";
        }

        if (number.isEmpty()) {
            return "400 The number is missing";
        }

        if (command.equalsIgnoreCase("B")) {
            try {
                int decimal = Integer.parseInt(number);
                String binary = Integer.toBinaryString(decimal);
                return "200 " + binary;
            } catch (NumberFormatException e) {
                return "400 Invalid number";
            }
        } else if (command.equalsIgnoreCase("H")) {
            try {
                int decimal = Integer.parseInt(number);
                String hex = Integer.toHexString(decimal);
                return "200 " + hex.toUpperCase();
            } catch (NumberFormatException e) {
                return "400 Invalid number";
            }
        }
        return "300 Bad request";
    }
}
