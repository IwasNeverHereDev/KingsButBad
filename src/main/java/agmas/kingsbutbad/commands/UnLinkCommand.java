package agmas.kingsbutbad.commands;

import agmas.kingsbutbad.keys.Keys;
import agmas.kingsbutbad.utils.CreateText;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnLinkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSender instanceof Player player) {
            // Check if the player is linked
            if (Keys.link.has(player)) {
                // Remove the link
                Keys.link.remove(player);
                player.sendMessage(CreateText.addColors("<gray>You have been unlinked from Discord."));
            } else {
                // Inform the player they are not linked
                player.sendMessage(CreateText.addColors("<red>You are not linked to any Discord account."));
            }
        } else {
            // Inform non-players (e.g., console) that this command can only be run by players
            commandSender.sendMessage(CreateText.addColors("<red>This command can only be run by a player."));
        }
        return true; // Indicate that the command was handled successfully
    }
}
