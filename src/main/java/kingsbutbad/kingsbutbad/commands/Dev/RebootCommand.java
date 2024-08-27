package kingsbutbad.kingsbutbad.commands.Dev;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RebootCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Bukkit.broadcastMessage(CreateText.addColors("<red>Server Reboot Coming Soon! <gray>(<white>5 Seconds<gray>)"));
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingsButBad.pl, () -> Bukkit.getServer().shutdown(),5*20);
        return false;
    }
}
