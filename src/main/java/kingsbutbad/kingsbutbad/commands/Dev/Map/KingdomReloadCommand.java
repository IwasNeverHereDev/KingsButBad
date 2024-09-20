package kingsbutbad.kingsbutbad.commands.Dev.Map;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.Loaders.LoadKingdoms;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class KingdomReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        LoadKingdoms.reload();
        new KingdomsLoader(KingdomsLoader.activeKingdom, true);
        commandSender.sendMessage(CreateText.addColors("<green>Successfully Reloaded!"));
        return false;
    }
}
