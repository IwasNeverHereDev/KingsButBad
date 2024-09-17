package kingsbutbad.kingsbutbad.commands.Clans.ClansSubCommands;

import kingsbutbad.kingsbutbad.commands.Clans.ClanSubCommand;
import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.pendingInvites;

public class InviteCommand implements ClanSubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan invite <playerName>"));
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
            sender.sendMessage(CreateText.addColors("<red>Your not in any Clan!"));
            return false;
        }
        if (!gang.getLeaders().contains(p.getUniqueId())) {
            sender.sendMessage(CreateText.addColors("<red>You are not a Leader."));
            return false;
        }
        if (gang.getMembers().contains(targetPlayer.getUniqueId())) {
            sender.sendMessage(CreateText.addColors("<red>Player is already in the clan!"));
            return false;
        }
        for(String clanNames : db.getGangs().keySet()) {
            Clans clans = db.getGangs().get(clanNames);
            if(clans.getMembers().contains(targetPlayer.getUniqueId())) {
                sender.sendMessage(CreateText.addColors("<red>Players is already apart of another gang! <gray>(<white>"+clans.getName()+"<gray>)"));
                return true;
            }
            if(clans.getGenerals().contains(targetPlayer.getUniqueId())) {
                sender.sendMessage(CreateText.addColors("<red>Players is already a General of another gang! <gray>(<white>"+clans.getName()+"<gray>)"));
                return true;
            }
            if(clans.getLeaders().contains(targetPlayer.getUniqueId())) {
                sender.sendMessage(CreateText.addColors("<red>Players is already a Leader of another gang! <gray>(<white>"+clans.getName()+"<gray>)"));
                return true;
            }
        }
        pendingInvites.put(targetPlayer.getUniqueId(), gang.getName());
        gang.broadcast(targetPlayer.getName() + " was Invited to the Clan!");
        targetPlayer.sendMessage(CreateText.addColors("<red>You have been Invited to a Clan named "+gang.getName()+" by "+sender.getName()+"! <gray>(<white>/clan accept "+gang.getName()+"<gray>)"));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2)
            return getPlayerNames();
        return new ArrayList<>();
    }
    private Clans getPlayerGang(Player player) {
        for (Map.Entry<String, Clans> entry : db.getGangs().entrySet()) {
            if (entry.getValue().getMembers().contains(player.getUniqueId()))
                return entry.getValue();
            if (entry.getValue().getLeaders().contains(player.getUniqueId()))
                return entry.getValue();
            if (entry.getValue().getLeaders().contains(player.getUniqueId()))
                return entry.getValue();
        }
        return null;
    }

    private List<String> getPlayerNames() {
        List<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if(Keys.vanish.get(player, false)) continue;
            playerNames.add(player.getName());
        }
        return playerNames;
    }
}
