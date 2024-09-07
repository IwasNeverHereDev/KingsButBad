package kingsbutbad.kingsbutbad.WebServer;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kingsbutbad.kingsbutbad.WebServer.API.GetOfflinePlaytime;
import kingsbutbad.kingsbutbad.WebServer.API.getOnlinePlayers;
import kingsbutbad.kingsbutbad.WebServer.Website.WebServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;

import static kingsbutbad.kingsbutbad.KingsButBad.server;

public class WebServerManager {

    public static void startWebServer() {
        server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new WebServer()), "/home");
        context.addServlet(new ServletHolder(new getOnlinePlayers()), "/api/getOnlinePlayers");
        context.addServlet(new ServletHolder(new GetOfflinePlaytime()), "/api/getPlaytime");
        context.addServlet(new ServletHolder(new ErrorServlet()), "/error");

        context.setErrorHandler(new ErrorHandler() {
            @Override
            public void handle(String target, org.eclipse.jetty.server.Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.sendRedirect("/error");
            }
        });

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopWebServer() {
        try {
            if (server != null) {
                server.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}