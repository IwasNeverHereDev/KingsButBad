package kingsbutbad.kingsbutbad.commands.Dev;

import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class SeenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/seen <player>"));
            return false;
        }

        String playerName = args[0];
        Player targetPlayer = Bukkit.getPlayerExact(playerName);

        if (targetPlayer != null && targetPlayer.isOnline()) {
            sender.sendMessage(CreateText.addColors("<green>"+playerName + " is currently online."));
        } else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);

            if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
                long lastSeen = offlinePlayer.getLastPlayed();
                Date lastSeenDate = new Date(lastSeen);

                sender.sendMessage(CreateText.addColors("<white>"+playerName + " <gray>was last seen on: <white>" + lastSeenDate));
            } else {
                sender.sendMessage(CreateText.addColors("<red>Player " + playerName + " has never been on this server."));
            }
        }
        return true;
    }
}
