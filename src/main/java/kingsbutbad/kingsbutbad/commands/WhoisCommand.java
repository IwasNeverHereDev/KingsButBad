package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WhoisCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            String playerName = args[0];
            Player target = Bukkit.getPlayer(playerName);

            if (target != null && target.isOnline()) {
                String discordId = Keys.link.get(target);
                String discordInfo;

                if (discordId != null && !discordId.isEmpty()) {
                    try {
                        User user = BotManager.getBot().getUserById(discordId);
                        if (user != null) {
                            // Fetching Discord username and discriminator
                            String username = user.getName();
                            discordInfo = "Linked to Discord ID: " + username;
                        } else {
                            discordInfo = "Linked to Discord ID: " + discordId + " (Discord user info not available)";
                        }
                    } catch (Exception e) {
                        discordInfo = "Linked to Discord ID: " + discordId + " (Error fetching Discord user info)";
                        e.printStackTrace(); // Log the exception
                    }
                } else {
                    discordInfo = "Not linked to a Discord account";
                }

                sender.sendMessage(CreateText.addColors(
                        "<gray>Player: <white>" + target.getName() +
                                "<gray>\nUUID: <white>" + target.getUniqueId().toString() +
                                "<gray>\n" + discordInfo
                ));
            } else if (target == null) {
                sender.sendMessage(CreateText.addColors("<red>Player not found."));
            } else {
                sender.sendMessage(CreateText.addColors("<red>Player is offline."));
            }
        } else {
            sender.sendMessage(CreateText.addColors("<red>Usage: /whois <playername>"));
        }
        return true;
    }
}