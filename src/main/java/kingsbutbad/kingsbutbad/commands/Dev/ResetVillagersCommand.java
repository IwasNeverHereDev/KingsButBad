package kingsbutbad.kingsbutbad.commands.Dev;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResetVillagersCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      KingdomsLoader.setupVillagers(KingdomsLoader.activeKingdom);
      return true;
   }
}
