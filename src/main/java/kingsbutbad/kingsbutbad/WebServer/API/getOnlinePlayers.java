package kingsbutbad.kingsbutbad.WebServer.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kingsbutbad.kingsbutbad.keys.Keys;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

public class getOnlinePlayers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        JsonArray playersArray = new JsonArray();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(Keys.vanish.get(player, false)) continue;
            JsonObject playerJson = new JsonObject();
            playerJson.addProperty("name", player.getName());
            playersArray.add(playerJson);
        }

        JsonObject responseJson = new JsonObject();
        responseJson.add("players", playersArray);

        PrintWriter out = resp.getWriter();
        out.print(responseJson.toString());
        out.flush();
    }
}
