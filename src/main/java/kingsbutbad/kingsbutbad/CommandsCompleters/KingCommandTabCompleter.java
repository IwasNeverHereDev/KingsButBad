package kingsbutbad.kingsbutbad.CommandsCompleters;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.Role;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KingCommandTabCompleter implements TabCompleter {
   private static final List<String> COMMANDS = Arrays.asList("help", "prefix", "knight", "fire", "prisonguard", "bodyguard", "sidekick", "prince", "taxes");

   public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
       if (sender instanceof Player player) {
           if (KingsButBad.roles.get(player).equals(Role.KING) || KingsButBad.roles.get(player).equals(Role.PRINCE)) {
               if (args.length == 1) {
                   return this.getMatchingSuggestions(args[0], COMMANDS);
               }

               if (args.length == 2) {
                   String subCommand = args[0];
                   if (subCommand.equals("taxes"))
                       return Arrays.asList("0", "25", "50");
                   if (subCommand.equals("prefix")) {
                       if(KingsButBad.roles.getOrDefault(player, Role.PEASANT) == Role.KING) return Arrays.asList("king", "queen", "monarch");
                       return Arrays.asList("prince", "princess", "cringe");
                   }

                   if (subCommand.equals("knight")
                           || subCommand.equals("prince")
                           || subCommand.equals("prisonguard")
                           || subCommand.equals("bodyguard")
                           || subCommand.equals("sidekick")) {
                       List<String> playerNames = new ArrayList<>();

                       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                           if (Keys.vanish.get(onlinePlayer, false)) continue;
                           if (KingsButBad.roles.get(onlinePlayer) == Role.PEASANT) {
                               playerNames.add(onlinePlayer.getName());
                           }
                       }

                       return this.getMatchingSuggestions(args[1], playerNames);
                   }
               }
           }

       }
       return Arrays.asList("Sorry, Your not a King or Sidekick!");
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
