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
        broadcastTime(5, 1);
        broadcastTime(4, 2);
        broadcastTime(3, 3);
        broadcastTime(2, 4);
        broadcastTime(1, 5);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingsButBad.pl, () -> Bukkit.getServer().shutdown(),6*20);
        return false;
    }
    private void broadcastTime(int value, int time){
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingsButBad.pl, () -> Bukkit.broadcastMessage(CreateText.addColors("<red>Server Reboot Coming Soon! <gray>(<white>"+value+" Seconds<gray>)")), time* 20L);
    }
}
