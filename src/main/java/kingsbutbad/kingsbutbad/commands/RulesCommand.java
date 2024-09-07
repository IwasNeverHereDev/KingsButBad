package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.utils.CreateText;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RulesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        try {
            Optional<Message> messageOptional = BotManager.getRulesChannel().getHistory().retrievePast(1).complete().stream().findFirst();

            if (messageOptional.isPresent()) {
                String rules = messageOptional.get().getContentDisplay();
                commandSender.sendMessage(CreateText.addColors("<gray>" + parseFormatting(rules)));
            } else {
                commandSender.sendMessage(CreateText.addColors("<gray>No rules available at the moment."));
            }
        } catch (Exception e) {
            commandSender.sendMessage(CreateText.addColors("<red>Failed to retrieve the rules. Please contact an administrator."));
            e.printStackTrace();
        }
        return true;
    }

    public static String parseFormatting(String message) {
        return message.replaceAll("\\*\\*(.*?)\\*\\*", "<b>$1</b>")
                .replaceAll("_(.*?)_", "<i>$1</i>");
    }
}


