package kingsbutbad.kingsbutbad.commands.Clans.ClansSubCommands;

import kingsbutbad.kingsbutbad.NoNoWords;
import kingsbutbad.kingsbutbad.commands.Clans.ClanSubCommand;
import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.getPlayerGang;

public class CreateCommand implements ClanSubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan create <clanName>"));
            return false;
        }
        String gangName = args[1];
        if (db.getGangs().containsKey(gangName)) {
            sender.sendMessage(CreateText.addColors("<red>A clan with this name already exists."));
            return false;
        }
        if(!NoNoWords.isClean(gangName)){
            sender.sendMessage(CreateText.addColors("<red>Your not allowed to name your Clan's name that!"));
            return true;
        }
        if(gangName.length() >= 15){
            sender.sendMessage(CreateText.addColors("<red>Your Clan is to long of name! <gray>(<white>"+gangName.length()+"<gray>)"));
            return true;
        }
        if(getPlayerGang(((Player) sender).getPlayer()) != null){
            sender.sendMessage(CreateText.addColors("<red>You can't create a clan while being in one!"));
            return true;
        }
        Clans newGang = new Clans(List.of(p.getUniqueId()), new ArrayList<>(), new ArrayList<>(), gangName, 0.0, 1, 0.0, new HashMap<>(), new ArrayList<>());
        db.getGangs().put(gangName, newGang);
        sender.sendMessage(CreateText.addColors("<green>Clan " + gangName + " created successfully."));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
