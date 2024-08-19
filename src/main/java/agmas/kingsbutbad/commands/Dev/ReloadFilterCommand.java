package agmas.kingsbutbad.commands.Dev;

import agmas.kingsbutbad.NoNoWords;
import agmas.kingsbutbad.utils.CreateText;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadFilterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        NoNoWords.reload();
        commandSender.sendMessage(CreateText.addColors("<gray>Reloaded Filter Successfully!"));
        return false;
    }
}
