package app;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new HomePageHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server running at http://localhost:8080");
    }
}