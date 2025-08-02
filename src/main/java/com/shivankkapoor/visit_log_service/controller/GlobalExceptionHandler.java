package com.shivankkapoor.visit_log_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNotFound(NoHandlerFoundException ex) {
        String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Oops! - Visit Service</title>
                    <meta charset="UTF-8">
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            text-align: center;
                            background-color: #f5f5f5;
                            margin: 0;
                            padding: 50px;
                        }
                        .error-container {
                            max-width: 600px;
                            margin: 0 auto;
                            background: white;
                            padding: 40px;
                            border-radius: 10px;
                            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                        }
                        .dog-image {
                            max-width: 300px;
                            height: auto;
                            border-radius: 15px;
                            margin: 20px 0;
                        }
                        h1 { color: #333; }
                        p { color: #666; font-size: 18px; }
                        .home-link {
                            display: inline-block;
                            background-color: #007bff;
                            color: white;
                            padding: 10px 20px;
                            text-decoration: none;
                            border-radius: 5px;
                            margin-top: 20px;
                        }
                        .status-code {
                            font-size: 48px;
                            color: #dc3545;
                            font-weight: bold;
                            margin: 10px 0;
                        }
                    </style>
                </head>
                <body>
                    <div class="error-container">
                        <div class="status-code">404</div>
                        <h1>Page Not Found</h1>
                        <img src="/Leo.webp" alt="Leo - My dog" class="dog-image">
                        <p>We couldn't fetch that page</p>
                        <a href="/" class="home-link">Go Home</a>
                    </div>
                </body>
                </html>
                """;

        return ResponseEntity.status(404)
                .header("Content-Type", "text/html")
                .body(html);
    }
}