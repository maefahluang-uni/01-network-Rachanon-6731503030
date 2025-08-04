package th.mfu;

import java.io.*;
import java.net.*;

public class MockWebServer implements Runnable {

    private int port;

    public MockWebServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        int port = 8080;
        String header = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
         // Web server that response Hello using ServerSocket
          try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Hello Web! on port " + port);
            
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)){

                        out.println(header + "<html><body>Hello, Web! on Port 8080</body></html>");
                    }
                }
        } catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }

    }
    

    public static void main(String[] args) {
        Thread server1 = new Thread(new MockWebServer(8080));
        server1.start();

        Thread server2 = new Thread(new MockWebServer(8081));
        server2.start();

        // type any key to stop the server
        // Wait for any key press to stop the mock web server
        System.out.println("Press any key to stop the server...");
        try {
            System.in.read();

            // Stop the mock web server
            server1.stop();
            server1.interrupt();
            server2.stop();
            server2.interrupt();
            System.out.println("Mock web server stopped.");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
