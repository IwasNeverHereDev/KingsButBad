package kingsbutbad.kingsbutbad.commands.Dev;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ResetVillagersCommand implements CommandExecutor {
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      KingdomsLoader.setupVillagers(KingdomsLoader.activeKingdom);
      sender.sendMessage(CreateText.addColors("<green>Successfully Reload Villagers"));
      return true;
   }
}
