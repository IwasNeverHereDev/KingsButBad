package kingsbutbad.kingsbutbad.commands.Builders.KingdomsCommands;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.Kingdom.Kingdom;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsReader;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.DiscordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SwapKingdomCommand implements CommandExecutor {
   public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
      if (strings.length == 0) {
         commandSender.sendMessage(CreateText.addColors("<red>Please provide the name of the kingdom."));
         return false;
      } else {
         String kingdomName = strings[0].toUpperCase();
         Kingdom targetKingdom = null;

         for (Kingdom kingdom : KingdomsReader.kingdomsList) {
            if (kingdom.getName().toUpperCase().equals(kingdomName)) {
               targetKingdom = kingdom;
               break;
            }
         }

         if (targetKingdom == null) {
            commandSender.sendMessage(CreateText.addColors("<red>Kingdom not found<white>: <gray>" + kingdomName));
            return false;
         } else {
            new KingdomsLoader(targetKingdom, false);
            commandSender.sendMessage(CreateText.addColors("<green>Successfully swapped to kingdom: " + targetKingdom.getName()));
            BotManager.getBuilderChannel().sendMessage(DiscordUtils.deformat(commandSender.getName()) + " has swapped the kingdom! ("+targetKingdom.getName()+")");
            return true;
         }
      }
   }
}
