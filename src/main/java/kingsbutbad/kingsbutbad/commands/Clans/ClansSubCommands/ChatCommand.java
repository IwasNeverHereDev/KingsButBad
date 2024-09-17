package kingsbutbad.kingsbutbad.commands.Clans.ClansSubCommands;

import kingsbutbad.kingsbutbad.NoNoWords;
import kingsbutbad.kingsbutbad.commands.Clans.ClanSubCommand;
import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.getPlayerGang;

public class ChatCommand implements ClanSubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
            return false;
        }

        if (args.length < 2) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan chat <message>"));
            return false;
        }
        if (getPlayerGang((Player) sender) == null) {
            sender.sendMessage(CreateText.addColors("<red>You're not in a clan!"));
            return true;
        }
        Clans gang = getPlayerGang(player);
        if (gang == null) {
            sender.sendMessage(CreateText.addColors("<red>Clan not found."));
            return false;
        }
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++)
            messageBuilder.append(args[i]).append(" ");
        String message = messageBuilder.toString().trim();

        if (!NoNoWords.isClean(message)) {
            sender.sendMessage(CreateText.addColors("<red>Your message contains prohibited words."));
            return true;
        }
        gang.sendChat(player, message);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
