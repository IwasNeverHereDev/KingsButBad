package kingsbutbad.kingsbutbad.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      sender.sendMessage(ChatColor.BLUE + "https://discord.gg/Smp4xtMH3C");
      return true;
   }
}
