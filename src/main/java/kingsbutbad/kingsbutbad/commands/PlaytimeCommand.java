package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.tasks.VanishTask;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
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
        OfflinePlayer player;

        if (args.length == 0) {
            if (commandSender instanceof Player) {
                player = (Player) commandSender;
            } else {
                commandSender.sendMessage(CreateText.addColors("Please specify a player name."));
                return true;
            }
        } else {
            player = Bukkit.getOfflinePlayer(args[0]);
        }

        if (!player.hasPlayedBefore()) {
            commandSender.sendMessage(CreateText.addColors("<red>Sorry, no player found with that name!"));
            return true;
        }

        long ticks;
        try {
            ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        } catch (IllegalArgumentException e) {
            commandSender.sendMessage(CreateText.addColors("Could not retrieve playtime statistics for " + player.getName() + "."));
            return true;
        }

        if(VanishTask.vanishPlayerTimeOfVanish.containsKey(player.getPlayer()))
            ticks = VanishTask.vanishPlayerTimeOfVanish.getOrDefault(player.getPlayer(), 0);

        String formattedTime = getString(ticks);

        commandSender.sendMessage(CreateText.addColors(player.getName() + "'s <gray>playtime is: <white>" + formattedTime));
        return true;
    }

    private static String getString(long ticks) {
        long milliseconds = ticks * 50L; // 1 tick = 50 ms

        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000 * 60)) % 60;
        long hours = (milliseconds / (1000 * 60 * 60)) % 24;
        long days = (milliseconds / (1000 * 60 * 60 * 24)) % 7;
        long weeks = (milliseconds / (1000 * 60 * 60 * 24 * 7)) % 4;
        long months = (milliseconds / (1000L * 60 * 60 * 24 * 30)) % 12;
        long years = (milliseconds / (1000L * 60 * 60 * 24 * 365));

        return String.format(
                "%d years, %d months, %d weeks, %d days, %d hours, %d minutes, %d seconds, %d milliseconds",
                years, months, weeks, days, hours, minutes, seconds, milliseconds % 1000
        );
    }
}

