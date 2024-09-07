package kingsbutbad.kingsbutbad.commands.Dev;

import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import kingsbutbad.kingsbutbad.commands.Clans.ClansDB;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class DebugClanCommand implements CommandExecutor {
    ClansDB databaseManager = ClansDB.loadData(new File(ClansDB.getDataFolder(), "gangs.gz").getPath());

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!sender.hasPermission("kbb.debug.clans")) {
            sender.sendMessage(CreateText.addColors("<red>You don't have permission to use this command."));
            return false;
        }

        if (args.length < 2) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/DebugClan <action> <parameters>"));
            return false;
        }

        String action = args[0].toLowerCase();
        String gangName = args[1];

        if (!ClansDB.loadData(new File(ClansDB.getDataFolder(), "gangs.gz").getPath()).getGangs().containsKey(gangName)) {
            sender.sendMessage(CreateText.addColors("<red>Clan not found."));
            return false;
        }
        Clans gang = ClansDB.loadData(new File(ClansDB.getDataFolder(), "gangs.gz").getPath()).getGangs().get(gangName);

        if (action.equals("rename")) {
            renameGang(sender, gang, args);
        } else if(action.equals("delete")){
            deleteGang(sender, gang, args);
        } else {
            sender.sendMessage(CreateText.addColors("<red>Unknown action. Valid actions<gray>: <white>rename/delete"));
        }
        return true;
    }

    private void renameGang(CommandSender sender, Clans gang, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/DebugClan rename <newName>"));
            return;
        }
        String newName = args[2];
        if (gang == null) {
            sender.sendMessage(CreateText.addColors("<red>Clan not found."));
            return;
        }
        databaseManager.getGangs().remove(gang.getName());
        databaseManager.getGangs().put(newName, gang);
        gang.setName(newName);
        gang.broadcast("This Clan was Forced rename by "+sender.getName());
        databaseManager.saveData(new File(ClansDB.getDataFolder(), "gangs.gz").getPath());
    }
    private void deleteGang(CommandSender sender, Clans gang, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/DebugClan delete <gangName>"));
            return;
        }
        if (gang == null) {
            sender.sendMessage(CreateText.addColors("<red>Clan not found."));
            return;
        }
        databaseManager.getGangs().remove(gang.getName());
        databaseManager.saveData(new File(ClansDB.getDataFolder(), "gangs.gz").getPath());
        sender.sendMessage(CreateText.addColors("<green>You have successfully Force deletion of Clan named "+ gang.getName()));
    }
}

