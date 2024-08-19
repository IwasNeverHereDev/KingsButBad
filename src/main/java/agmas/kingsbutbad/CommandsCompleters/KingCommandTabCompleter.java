package agmas.kingsbutbad.CommandsCompleters;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.keys.Keys;
import agmas.kingsbutbad.utils.Role;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class KingCommandTabCompleter implements TabCompleter {
   private static final List<String> COMMANDS = Arrays.asList("help", "gender", "knight", "fire", "prisonguard", "bodyguard", "sidekick", "prince", "taxes");

   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player player)) {
         return Collections.emptyList();
      } else {
         if (KingsButBad.roles.get(player).equals(Role.KING) || KingsButBad.roles.get(player).equals(Role.PRINCE)) {
            if (args.length == 1) {
               return this.getMatchingSuggestions(args[0], COMMANDS);
            }

            if (args.length == 2) {
               String subCommand = args[0];
               if (subCommand.equals("gender")) {
                  return Arrays.asList("male", "female", "sussy");
               }

               if (subCommand.equals("knight")
                  || subCommand.equals("prince")
                  || subCommand.equals("prisonguard")
                  || subCommand.equals("bodyguard")
                  || subCommand.equals("sidekick")) {
                  List<String> playerNames = new ArrayList<>();

                  for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                     if(Keys.vanish.get(onlinePlayer, false)) continue;
                     if (KingsButBad.roles.get(onlinePlayer) == Role.PEASANT) {
                        playerNames.add(onlinePlayer.getName());
                     }
                  }

                  return this.getMatchingSuggestions(args[1], playerNames);
               }
            }
         }

         return Collections.emptyList();
      }
   }

   private List<String> getMatchingSuggestions(String current, List<String> options) {
      List<String> suggestions = new ArrayList<>();

      for (String option : options) {
         if (option.toLowerCase().startsWith(current.toLowerCase())) {
            suggestions.add(option);
         }
      }

      return suggestions;
   }
}
