package kingsbutbad.kingsbutbad.commands.Dev;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class TaskListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Plugin pluginToCheck;

        if (args.length > 0) {
            pluginToCheck = Bukkit.getPluginManager().getPlugin(args[0]);
            if (pluginToCheck == null) {
                sender.sendMessage("Plugin " + args[0] + " not found.");
                return true;
            }
        } else {
            pluginToCheck = KingsButBad.pl;
        }

        sender.sendMessage(CreateText.addColors("<gray>Active tasks for plugin: <white>" + pluginToCheck.getName()));

        Bukkit.getScheduler().getActiveWorkers().forEach(bukkitWorker -> {
            if (bukkitWorker.getOwner().equals(pluginToCheck))
                sender.sendMessage(CreateText.addColors("<red>Task ID: <white>" + bukkitWorker.getTaskId()));
        });

        Bukkit.getScheduler().getPendingTasks().forEach(bukkitTask -> {
            if (bukkitTask.getOwner().equals(pluginToCheck))
                sender.sendMessage(CreateText.addColors("<gray>Repeating Task ID: <white>" + bukkitTask.getTaskId()));
        });

        return true;
    }
}

