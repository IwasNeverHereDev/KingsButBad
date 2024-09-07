package kingsbutbad.kingsbutbad.commands.Misc;

import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnLinkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSender instanceof Player player) {
            if (Keys.link.has(player)) {
                Keys.link.remove(player);
                player.sendMessage(CreateText.addColors("<gray>You have been unlinked from Discord."));
            } else {
                player.sendMessage(CreateText.addColors("<red>You are not linked to any Discord account."));
            }
        } else {
            commandSender.sendMessage(CreateText.addColors("<red>This command can only be run by a player."));
        }
        return true;
    }
}
