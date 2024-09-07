package kingsbutbad.kingsbutbad.WebServer;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleError(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleError(req, resp);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) req.getAttribute("javax.servlet.error.message");

        resp.setContentType("text/html");
        resp.setStatus(statusCode != null ? statusCode : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        String errorPage = "<html><head><title>Error</title></head><body>" +
                "<h1>Error Code: " + statusCode + "</h1>" +
                "<p>" + errorMessage + "</p>" +
                "<p>Something went wrong. Please try again later.</p>" +
                "</body></html>";
        resp.getWriter().write(errorPage);
    }
}

