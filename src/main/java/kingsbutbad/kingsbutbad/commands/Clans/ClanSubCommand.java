package kingsbutbad.kingsbutbad.commands.Clans;

import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.List;

public interface ClanSubCommand {
    ClansDB db = new ClansDB(ClansDB.loadData(new File(ClansDB.getDataFolder(), "gangs.gz").getPath()));
    boolean execute(CommandSender sender, String[] args);
    List<String> tabComplete(CommandSender sender, String[] args);
}
