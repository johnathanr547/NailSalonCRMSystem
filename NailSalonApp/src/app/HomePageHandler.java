package app;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * The server to make appointments, etc. Might be canned later.
 */
public class HomePageHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.equals("/style.css")) {
            serveFile(exchange, "/static/style.css", "text/css");
        } else {
            serveFile(exchange, "/static/index.html", "text/html");
        }
    }

    private void serveFile(HttpExchange exchange, String resourcePath, String contentType) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);

        if (inputStream == null) {
            String notFound = "404 Not Found: " + resourcePath;
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(404, notFound.getBytes().length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(notFound.getBytes());
            }
            return;
        }

        byte[] response = inputStream.readAllBytes();
        exchange.getResponseHeaders().set("Content-Type", contentType + "; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }
}