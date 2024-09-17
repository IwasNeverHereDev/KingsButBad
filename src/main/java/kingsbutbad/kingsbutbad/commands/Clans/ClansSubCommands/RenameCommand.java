package kingsbutbad.kingsbutbad.commands.Clans.ClansSubCommands;

import kingsbutbad.kingsbutbad.NoNoWords;
import kingsbutbad.kingsbutbad.commands.Clans.ClanSubCommand;
import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.getPlayerGang;

public class RenameCommand implements ClanSubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan rename <newName>"));
            return false;
        }
        String newName = args[1];
        if(!NoNoWords.isClean(newName)){
            sender.sendMessage(CreateText.addColors("This Clan name contains Filtered Words!"));
            return true;
        }
        Clans gang = getPlayerGang(((Player) sender).getPlayer());
        if (gang == null) {
            sender.sendMessage(CreateText.addColors("<red>Clan not found."));
            return false;
        }
        if(!gang.getLeaders().contains(((Player) sender).getUniqueId())){
            sender.sendMessage(CreateText.addColors("<red>Only the Leader(s) can do this!"));
            return true;
        }
        if(newName.length() >= 15){
            sender.sendMessage(CreateText.addColors("<red>Your Clan's Name is to long! <gray>(<white>"+newName.length()+"<gray>)"));
            return true;
        }
        db.getGangs().remove(gang.getName());
        db.getGangs().put(newName, gang);
        gang.setName(newName);
        gang.broadcast("Clan's name was changed to "+newName+"!");
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
