package kingsbutbad.kingsbutbad.CommandsCompleters;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import kingsbutbad.kingsbutbad.keys.Keys;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PayCommandTabCompleter implements TabCompleter {
   private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+(\\.\\d+)?");

   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
      List<String> suggestions = new ArrayList<>();
      if (args.length == 1) {
         for (Player player : Bukkit.getOnlinePlayers()) {
            if(Keys.vanish.get(player, false)) continue;
            if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
               suggestions.add(player.getName());
            }
         }
      } else if (args.length == 2 && (Bukkit.getPlayer(args[0]) != null || args[0].equalsIgnoreCase("ALL"))) {
         suggestions.add("1k");
         suggestions.add("5k");
         suggestions.add("10k");
         suggestions.add("25k");
         suggestions.add("50k");
         suggestions.add("100k");
         suggestions.add("500k");
         suggestions.add("1m");
      }

      return suggestions;
   }
}
