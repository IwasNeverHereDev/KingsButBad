package kingsbutbad.kingsbutbad.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Alert {
   public static void send(Alert.AlertType type, Player exe, String message) {
      for (Player p : Bukkit.getOnlinePlayers()) {
         if (p.hasPermission("kbb.staff")) {
            sendFilterAlert(p, exe, message);
         }
      }
   }

   public static void sendFilterAlert(Player executor, Player p, String msg) {
      String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      String filteredMessage = ChatColor.stripColor(msg);
      String alertMessage = String.format(
         "&8[&c%s&8] &cALERT&7: &f%s's &7message was filtered due to inappropriate content.\nFiltered Message: &f\"%s\"\n",
         timestamp,
         p.getName(),
         filteredMessage
      );
      executor.sendMessage(ChatColor.translateAlternateColorCodes('&', alertMessage));
   }

   public enum AlertType {
      FILTERs
   }
}
