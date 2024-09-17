package kingsbutbad.kingsbutbad.commands.Clans.ClansSubCommands;

import kingsbutbad.kingsbutbad.commands.Clans.ClanSubCommand;
import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.getPlayerGang;
import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.getPlayerNames;

public class DemoteCommand implements ClanSubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan demote <playerName>"));
            return false;
        }
        String playerName = args[1];
        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            sender.sendMessage(CreateText.addColors("<red>Player not found or offline."));
            return false;
        }
        Clans gang = getPlayerGang(p);
        if (gang == null) {
            sender.sendMessage(CreateText.addColors("<red>You are not in any clan."));
            return false;
        }
        if (!gang.getLeaders().contains(p.getUniqueId())) {
            sender.sendMessage(CreateText.addColors("<red>You are not a leader."));
            return false;
        }
        if (!gang.getGenerals().contains(targetPlayer.getUniqueId())) {
            sender.sendMessage(CreateText.addColors("<red>Player is not a general of the clan."));
            return false;
        }
        gang.getGenerals().remove(targetPlayer.getUniqueId());
        gang.getMembers().add(targetPlayer.getUniqueId());
        gang.broadcast(targetPlayer.getName()+" was Demoted from General!");
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2)
            return getPlayerNames();
        return new ArrayList<>();
    }
}
