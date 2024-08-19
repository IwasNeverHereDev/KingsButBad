package agmas.kingsbutbad.commands.Dev;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.keys.Keys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class SetMoneyCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender)) {
         sender.sendMessage(ChatColor.RED + "This command can only be used by a player or the console.");
         return false;
      } else if (args.length < 2) {
         sender.sendMessage(ChatColor.RED + "Usage: /setmoney <player> <amount>");
         return false;
      } else {
         Player targetPlayer = Bukkit.getPlayer(args[0]);
         if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Player not found or not online.");
            return false;
         } else {
            String amountString = args[1];

            Double amount;
            try {
               if ("inf".equalsIgnoreCase(amountString)) {
                  amount = Double.POSITIVE_INFINITY;
               } else if ("-inf".equalsIgnoreCase(amountString)) {
                  amount = Double.NEGATIVE_INFINITY;
               } else {
                  amount = Double.valueOf(amountString);
               }
            } catch (NumberFormatException var9) {
               sender.sendMessage(ChatColor.RED + "Invalid amount specified. Please enter a valid number.");
               return false;
            }
            Keys.money.set(targetPlayer, amount);
            sender.sendMessage(ChatColor.GREEN + "Balance set for " + targetPlayer.getName() + " to " + amount);
            sender.sendMessage(ChatColor.GREEN + "You balance was set by " + targetPlayer.getName() + " to " + amount);
            return true;
         }
      }
   }
}
