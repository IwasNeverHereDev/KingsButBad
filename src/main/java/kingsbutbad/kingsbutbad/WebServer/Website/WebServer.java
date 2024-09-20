package kingsbutbad.kingsbutbad.WebServer.Website;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class WebServer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>KingsButBad</title>");
        out.println("<style>");
        out.println("body { background-color: gray; font-family: Arial, sans-serif; text-align: center; padding-top: 50px; }");
        out.println("h1 { font-size: 50px; }");
        out.println("p { font-size: 20px; }");
        out.println(".container { width: 80%; margin: 0 auto; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>404 Page</h1>");
        out.println("<p>This is only for API use...</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}

