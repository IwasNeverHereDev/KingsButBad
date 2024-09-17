package kingsbutbad.kingsbutbad.commands.Clans.ClansSubCommands;

import kingsbutbad.kingsbutbad.commands.Clans.ClanSubCommand;
import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import kingsbutbad.kingsbutbad.commands.Clans.ClansDB;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.getPlayerGang;
import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.pendingBankNotes;

public class DisbandCommand implements ClanSubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan disband"));
            return false;
        }
        if(getPlayerGang((Player) sender) == null){
            sender.sendMessage(CreateText.addColors("<red>Your not in a Clan!"));
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
        gang.broadcast("Clan was deleted... RIP");
        pendingBankNotes.remove(gang);
        pendingBankNotes.remove(gang);
        Keys.money.addDouble(((Player) sender), gang.getBankAmount());
        db.getGangs().remove(gang.getName());
        db.saveData(new File(ClansDB.getDataFolder(), "gangs.gz").getPath());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}