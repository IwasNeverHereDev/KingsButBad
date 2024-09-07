package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.commands.Clans.ClansDB;
import kingsbutbad.kingsbutbad.keys.DatabaseManager;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.CacheRequest;
import java.time.Duration;
import java.time.Instant;

public class HelloCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Duration uptime = Duration.between(KingsButBad.startTime, Instant.now());
        long hours = uptime.toHours();
        long minutes = uptime.toMinutes() % 60;
        long seconds = uptime.getSeconds() % 60;

        int onlinePlayers = getOnlinePlayers();
        int maxPlayers = Bukkit.getMaxPlayers();
        int allLifeTimePlayers = Bukkit.getOfflinePlayers().length;
        int clanCount = ClansDB.loadData(new File(ClansDB.getDataFolder(), "gangs.gz").getPath()).getGangs().size();

        String serverVersion = Bukkit.getVersion();

        String playerName = commandSender instanceof Player ? commandSender.getName() : "Console";

        commandSender.sendMessage(CreateText.addColors("<gray>Hello, <white>" + playerName + "<gray>!"));
        commandSender.sendMessage(CreateText.addColors("<gray>Server Version: <white>" + serverVersion));
        commandSender.sendMessage(CreateText.addColors("<gray>Online Players: <white>" + onlinePlayers + "<gray>/<white>" + maxPlayers));
        commandSender.sendMessage(CreateText.addColors("<gray>Unique Players: <white>" + allLifeTimePlayers));
        commandSender.sendMessage(CreateText.addColors("<gray>Clans Count: <white>" + clanCount));
        commandSender.sendMessage(CreateText.addColors("<gray>Server Uptime: <white>" + hours + " hours, " + minutes + " minutes, " + seconds + " seconds"));
        commandSender.sendMessage(CreateText.addColors("<gray>Current Kingdom ID (Map): <white>" + KingdomsLoader.activeKingdom.getName()));
        return true;
    }
    private int getOnlinePlayers(){
        int result = 0;
        for(Player p : Bukkit.getOnlinePlayers())
            if(!Keys.vanish.get(p, false))
                result++;
        return result;
    }
}
