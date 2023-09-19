import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class PingPongClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
//        if (args.length != 2) {
//            System.err.println("Usage: java PingPongClient <server-hostname> <server-port>");
//            System.exit(1);
//        }

        String serverHostname =null;
        int serverPort =8080;
        Socket socket = new Socket(serverHostname, serverPort);

        try (
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Connected to server: " + serverHostname + ":" + serverPort);
            int i=1;
            while (i<10) {
                out.println("ping");
                System.out.println("Sent 'ping' to server");

                String response = in.readLine();
                if ("pong".equals(response)) {
                    System.out.println("Received 'pong' from server");
                } else {
                    System.out.println("Unexpected response from server: " + response);
                }

                // Wait for 1 second before sending the next "ping"
                Thread.sleep(1000);
                i++;
            }
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }
}
