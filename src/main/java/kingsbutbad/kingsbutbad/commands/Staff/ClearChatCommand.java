package kingsbutbad.kingsbutbad.commands.Staff;

import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ClearChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Bukkit.broadcastMessage("\n\n\n\n".repeat(255));
        Bukkit.broadcastMessage(CreateText.addColors("<red>Cleared Chat by "+commandSender.getName()));
        return false;
    }
}
