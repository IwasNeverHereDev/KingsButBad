package kingsbutbad.kingsbutbad.commands.Misc;

import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DiscordCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      sender.sendMessage(CreateText.addColors("Click here -> https://discord.gg/Smp4xtMH3C"));
      return true;
   }
}
