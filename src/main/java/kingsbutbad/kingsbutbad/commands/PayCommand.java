package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

public class PayCommand implements CommandExecutor {
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("This command can only be used by players.");
         return true;
      } else if (args.length < 2) {
         return false;
      } else {
         Player player = (Player)sender;
         Player target = Bukkit.getPlayer(args[0]);
         if (target == null) {
            player.sendMessage(CreateText.addColors("<red>Player not found."));
            return true;
         } else {
            double amount;
            try {
               amount = this.parseAmount(args[1]);
            } catch (NumberFormatException var11) {
               player.sendMessage(CreateText.addColors("<red>Invalid amount format."));
               return true;
            }

            if (amount <= 0.0) {
               player.sendMessage(CreateText.addColors("<red>Amount must be greater than zero."));
               return true;
            } else {
               double playerBalance = this.getPlayerBalance(player);
               if (amount > playerBalance) {
                  player.sendMessage(CreateText.addColors("<red>You don't have enough money."));
                  return true;
               } else {
                  if(Keys.vanish.get(target, false)){
                     player.sendMessage(CreateText.addColors("<red>Player not found."));
                     return true;
                  }
                  this.setPlayerBalance(player, playerBalance - amount);
                  this.addPlayerMoney(target, amount);
                  player.sendMessage(
                     CreateText.addColors("<gray>Sent <green>$" + this.formatAmount(amount) + " <gray>to <white>" + target.getName() + "<gray>.")
                  );
                  target.sendMessage(
                     CreateText.addColors("<gray>You have been given <green>$" + this.formatAmount(amount) + " <gray>by <white>" + player.getName() + "<gray>.")
                  );
                  return true;
               }
            }
         }
      }
   }

   private double parseAmount(String amountStr) {
      amountStr = amountStr.toLowerCase();
      double amount;
      if (amountStr.endsWith("k")) {
         amount = Double.parseDouble(amountStr.replace("k", "")) * 1000.0;
      } else if (amountStr.endsWith("m")) {
         amount = Double.parseDouble(amountStr.replace("m", "")) * 1000000.0;
      } else {
         amount = Double.parseDouble(amountStr);
      }

      return amount;
   }

   private String formatAmount(double amount) {
      if (amount >= 1000000.0) {
         return String.format("%.1fm", amount / 1000000.0);
      } else {
         return amount >= 1000.0 ? String.format("%.1fk", amount / 1000.0) : String.format("%.2f", amount);
      }
   }

   private double getPlayerBalance(Player player) {
      PersistentDataContainer container = player.getPersistentDataContainer();
      return Keys.money.get(player, 0.0);
   }

   private void setPlayerBalance(Player player, double amount) {
      Keys.money.set(player, amount);
   }

   private void addPlayerMoney(Player player, double amount) {
      double currentBalance = Keys.money.get(player, 0.0);
      Keys.money.set(player, currentBalance + amount);
   }
}
