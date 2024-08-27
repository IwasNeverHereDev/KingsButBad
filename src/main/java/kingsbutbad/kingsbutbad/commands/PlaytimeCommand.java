package kingsbutbad.kingsbutbad.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaytimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        OfflinePlayer player = null;

        if (args.length == 0) {
            // No player name provided, use the sender's name if it's a player
            if (commandSender instanceof Player) {
                player = (Player) commandSender;
            } else {
                // If sender is not a player and no argument is provided
                commandSender.sendMessage(ChatColor.RED + "Please specify a player name.");
                return true;
            }
        } else {
            // Get the OfflinePlayer by name
            player = Bukkit.getOfflinePlayer(args[0]);
        }

        if (player == null || !player.hasPlayedBefore()) {
            commandSender.sendMessage(ChatColor.RED + "Sorry, no player found with that name!");
            return true;
        }

        long ticks;
        try {
            ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        } catch (IllegalArgumentException e) {
            commandSender.sendMessage(ChatColor.RED + "Could not retrieve playtime statistics for " + player.getName() + ".");
            return true;
        }

        // Convert ticks to milliseconds
        long milliseconds = ticks * 50L; // 1 tick = 50 ms

        // Breakdown into components
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000 * 60)) % 60;
        long hours = (milliseconds / (1000 * 60 * 60)) % 24;
        long days = (milliseconds / (1000 * 60 * 60 * 24)) % 7;
        long weeks = (milliseconds / (1000 * 60 * 60 * 24 * 7)) % 4;
        long months = (milliseconds / (1000 * 60 * 60 * 24 * 30)) % 12; // Approximation
        long years = (milliseconds / (1000 * 60 * 60 * 24 * 365)); // Approximation

        // Format the message
        String formattedTime = String.format(
                "%d years, %d months, %d weeks, %d days, %d hours, %d minutes, %d seconds, %d milliseconds",
                years, months, weeks, days, hours, minutes, seconds, milliseconds % 1000
        );

        // Send the message to the command sender
        commandSender.sendMessage(ChatColor.GREEN + player.getName() + "'s playtime is: " + formattedTime);

        return true;
    }
}

