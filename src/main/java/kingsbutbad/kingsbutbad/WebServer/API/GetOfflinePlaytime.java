package kingsbutbad.kingsbutbad.WebServer.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kingsbutbad.kingsbutbad.keys.Keys;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.PrintWriter;

public class GetOfflinePlaytime extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        JsonArray playersArray = new JsonArray();

        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            JsonObject playerJson = new JsonObject();
            playerJson.addProperty(p.getUniqueId().toString(), p.getStatistic(Statistic.PLAY_ONE_MINUTE));
            playersArray.add(playerJson);
        }

        JsonObject responseJson = new JsonObject();
        responseJson.add("playtime", playersArray);

        PrintWriter out = resp.getWriter();
        out.print(responseJson.toString());
        out.flush();
    }
}
