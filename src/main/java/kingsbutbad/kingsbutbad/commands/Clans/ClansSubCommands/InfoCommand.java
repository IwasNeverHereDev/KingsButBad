package kingsbutbad.kingsbutbad.commands.Clans.ClansSubCommands;

import kingsbutbad.kingsbutbad.commands.Clans.ClanQuests;
import kingsbutbad.kingsbutbad.commands.Clans.ClanSubCommand;
import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.formatPlayerNames;
import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.getPlayerGang;

public class InfoCommand implements ClanSubCommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
            return false;
        }

        Clans clan = getPlayerGang(p);
        if (args.length == 2) {
            String clanName = args[1];
            if (!db.getGangs().containsKey(clanName)) {
                sender.sendMessage(CreateText.addColors("<red>There is no clan by that name!"));
                return true;
            }
            clan = db.getGangs().get(clanName);
        }

        if (clan == null) {
            sender.sendMessage(CreateText.addColors("<red>You are not in any clan."));
            return false;
        }

        sender.sendMessage(CreateText.addColors("<red>Clan's Name<gray>: <white>") + clan.getName());
        sender.sendMessage(CreateText.addColors("<red>Member(s) Count<gray>: <white>") + clan.getMembers().size());
        sender.sendMessage(CreateText.addColors("<red>General(s) Count<gray>: <white>") + clan.getGenerals().size());
        sender.sendMessage(CreateText.addColors("<red>Leader(s) Count<gray>: <white>") + clan.getLeaders().size());
        sender.sendMessage(CreateText.addColors("<red>Clan's Bank<gray>: <white>") + clan.getBankAmount());
        sender.sendMessage(CreateText.addColors("<red>Clan's Level<gray>: <white>") + clan.getLevel());
        sender.sendMessage(CreateText.addColors("<red>Clan's Exp<gray>: <white>") + clan.getExp());

        StringBuilder quests = new StringBuilder();
        for (ClanQuests quest : clan.getActiveGangQuests()) {
            if (!quests.isEmpty()) quests.append(", ");
            quests.append(quest.getName());
        }
        if (quests.isEmpty()) quests.append("none");
        sender.sendMessage(CreateText.addColors("<red>Clan's Active Quest<gray>: <white>") + quests);

        sender.sendMessage(CreateText.addColors("<red>Leader(s) Names<gray>: <white>") + formatPlayerNames(clan.getLeaders(), 5));
        sender.sendMessage(CreateText.addColors("<red>General(s) Names<gray>: <white>") + formatPlayerNames(clan.getGenerals(), 5));
        sender.sendMessage(CreateText.addColors("<red>Member(s) Names<gray>: <white>") + formatPlayerNames(clan.getMembers(), 5));

        return true;
    }
    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 2)
            return new ArrayList<>(db.getGangs().keySet());
        return new ArrayList<>();
    }
}
