package kingsbutbad.kingsbutbad.commands.Dev;

import kingsbutbad.kingsbutbad.keys.DatabaseManager;
import kingsbutbad.kingsbutbad.keys.Key;
import kingsbutbad.kingsbutbad.keys.Keys;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class KeysCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[0]);
        for(Key key : Keys.values()){
            Object value = DatabaseManager.getKeyPlayer(offlinePlayer, key.name());
            if (value != null) {
                commandSender.sendMessage(key.name()+" = " + value);
            } else {
                commandSender.sendMessage("No data found for this player or key. ("+key.name()+")");
            }
        }
        return false;
    }
}
