package kingsbutbad.kingsbutbad.commands.Dev;

import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetMoneyCommand implements CommandExecutor {
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (!(sender instanceof Player) && !(sender instanceof ConsoleCommandSender)) {
         sender.sendMessage(CreateText.addColors("This command can only be used by a player or the console."));
         return false;
      } else if (args.length < 2) {
         sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/SetMoney <player> <amount>"));
         return false;
      } else {
         Player targetPlayer = Bukkit.getPlayer(args[0]);
         if (targetPlayer == null) {
            sender.sendMessage(CreateText.addColors("<red>Player not found or not online."));
            return false;
         } else {
            String amountString = args[1];

            double amount;
            try {
               if ("inf".equalsIgnoreCase(amountString)) {
                  amount = Double.POSITIVE_INFINITY;
               } else if ("-inf".equalsIgnoreCase(amountString)) {
                  amount = Double.NEGATIVE_INFINITY;
               } else {
                  amount = Double.parseDouble(amountString);
               }
            } catch (NumberFormatException var9) {
               sender.sendMessage(CreateText.addColors("<red>Invalid amount specified. Please enter a valid number."));
               return false;
            }
            Keys.money.set(targetPlayer, amount);
            sender.sendMessage(CreateText.addColors("Balance set for " + targetPlayer.getName() + " to " + amount));
            targetPlayer.sendMessage(CreateText.addColors("Your balance was set by " + targetPlayer.getName() + " to " + amount));
            return true;
         }
      }
   }
}
