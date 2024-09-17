package kingsbutbad.kingsbutbad.commands.Clans.ClansSubCommands;

import kingsbutbad.kingsbutbad.commands.Clans.ClanSubCommand;
import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.pendingInvites;

public class AcceptCommand implements ClanSubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("This command can only be used by players.");
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage("Usage: /clan accept <gangName>");
            return false;
        }
        String gangName = args[1];
        Clans gang = db.getGangs().get(gangName);
        if (gang == null) {
            sender.sendMessage("Gang not found.");
            return false;
        }
        if (!pendingInvites.containsKey(p.getUniqueId()) || !pendingInvites.get(p.getUniqueId()).equals(gangName)) {
            sender.sendMessage("You have no pending invite from this gang.");
            return false;
        }
        gang.getMembers().add(p.getUniqueId());
        pendingInvites.remove(p.getUniqueId());
        gang.broadcast(sender.getName()+ " has joined the gang!");
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2)
            return new ArrayList<>(db.getGangs().keySet());
        return new ArrayList<>();
    }
}
