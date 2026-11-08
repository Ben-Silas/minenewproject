package com.silas.portfolio;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final int PORT = 8080;
    private static final Path INDEX = Path.of("src", "main", "resources", "index.html");

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/", exchange -> {
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                send(exchange, 405, "text/plain; charset=utf-8", "Method not allowed");
                return;
            }

            if ("/api/health".equals(exchange.getRequestURI().getPath())) {
                send(exchange, 200, "application/json; charset=utf-8",
                        "{\"status\":\"ok\",\"application\":\"Silas Portfolio\"}");
                return;
            }

            if (!"/".equals(exchange.getRequestURI().getPath())) {
                send(exchange, 404, "text/plain; charset=utf-8", "Page not found");
                return;
            }

            if (!Files.exists(INDEX)) {
                send(exchange, 500, "text/plain; charset=utf-8",
                        "Website file not found. Run this application from the project root folder.");
                return;
            }

            byte[] page = Files.readAllBytes(INDEX);
            Headers headers = exchange.getResponseHeaders();
            headers.set("Content-Type", "text/html; charset=utf-8");
            headers.set("X-Content-Type-Options", "nosniff");
            headers.set("X-Frame-Options", "DENY");
            headers.set("Referrer-Policy", "strict-origin-when-cross-origin");
            exchange.sendResponseHeaders(200, page.length);
            try (OutputStream output = exchange.getResponseBody()) {
                output.write(page);
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("Silas Portfolio is running at http://localhost:" + PORT);
        System.out.println("Health check: http://localhost:" + PORT + "/api/health");
    }

    private static void send(HttpExchange exchange, int status, String contentType, String body)
            throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }
}
