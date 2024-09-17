package kingsbutbad.kingsbutbad.commands.Clans.ClansSubCommands;

import kingsbutbad.kingsbutbad.commands.Clans.ClanSubCommand;
import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.getPlayerGang;

public class LeaveCommand implements ClanSubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan leave"));
            return false;
        }
        if(getPlayerGang((Player) sender) == null){
            sender.sendMessage(CreateText.addColors("<red>Your not in a clan!"));
            return true;
        }
        Clans gang = getPlayerGang(((Player) sender).getPlayer());
        if (gang == null) {
            sender.sendMessage(CreateText.addColors("<red>Clan not found."));
            return false;
        }
        if(gang.getLeaders().contains(((Player) sender).getUniqueId())){
            sender.sendMessage(CreateText.addColors("Only the Member(s) and General(s) can do this!"));
            return true;
        }
        gang.broadcast(sender.getName()+" has left the Clan!");
        gang.getMembers().remove(((Player) sender).getUniqueId());
        gang.getGenerals().remove(((Player) sender).getUniqueId());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
