package kingsbutbad.kingsbutbad.commands.Dev.Map;

import kingsbutbad.kingsbutbad.Kingdom.Areas.Area;
import kingsbutbad.kingsbutbad.Kingdom.Areas.AreaLoaders;
import kingsbutbad.kingsbutbad.Kingdom.Areas.AreaTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AreaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /area <tp|get|list|reload>");
            return false;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "tp":
                return handleTpCommand(sender, args);
            case "get":
                return handleGetCommand(sender, args);
            case "list":
                return handleListCommand(sender);
            case "reload":
                return handleReloadCommand(sender);
            default:
                sender.sendMessage("Unknown command. Usage: /area <tp|get|list|reload>");
                return false;
        }
    }

    private boolean handleTpCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return false;
        }

        if (args.length < 2) {
            sender.sendMessage("Usage: /area tp <name>");
            return false;
        }

        Player player = (Player) sender;
        String areaName = args[1];

        Area area = getAreaByName(areaName);
        if (area == null) {
            sender.sendMessage("Area not found: " + areaName);
            return false;
        }

        player.teleport(area.getListLoc().get(0));
        sender.sendMessage("Teleported to area " + areaName);
        return true;
    }

    private boolean handleGetCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /area get <name>");
            return false;
        }

        String areaName = args[1];
        Area area = getAreaByName(areaName);
        if (area == null) {
            sender.sendMessage("Area not found: " + areaName);
            return false;
        }

        sender.sendMessage("Area: " + area.getDisplayName());
        sender.sendMessage("Type: " + area.getAreaTypes());
        sender.sendMessage("Locations: " + area.getListLoc().stream()
                .map(loc -> loc.getX() + ", " + loc.getY() + ", " + loc.getZ())
                .collect(Collectors.joining(" | ")));
        return true;
    }

    private boolean handleListCommand(CommandSender sender) {
        List<Area> areas = AreaLoaders.AreasList;
        if (areas.isEmpty()) {
            sender.sendMessage("No areas found.");
            return false;
        }

        sender.sendMessage("Areas:");
        for (Area area : areas) {
            sender.sendMessage("- " + area.getDisplayName() + " (" + area.getAreaTypes() + ")");
        }
        return true;
    }

    private boolean handleReloadCommand(CommandSender sender) {
        sender.sendMessage("Reloading areas...");
        AreaLoaders loader = new AreaLoaders();
        loader.read(Bukkit.getPluginManager().getPlugin("KingsButBad").getDataFolder().toPath().resolve("Areas.json"));
        sender.sendMessage("Areas reloaded.");
        return true;
    }

    private Area getAreaByName(String areaName) {
        return AreaLoaders.AreasList.stream()
                .filter(area -> area.getDisplayName().equalsIgnoreCase(areaName))
                .findFirst()
                .orElse(null);
    }
}
