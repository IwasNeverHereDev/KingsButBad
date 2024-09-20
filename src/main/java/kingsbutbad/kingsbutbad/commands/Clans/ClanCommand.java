package kingsbutbad.kingsbutbad.commands.Clans;

import kingsbutbad.kingsbutbad.commands.Clans.ClansSubCommands.*;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class ClanCommand implements CommandExecutor, TabCompleter {
    public static final File gangDB = new File(ClansDB.getDataFolder(), "gangs.gz");
    public static final Map<String, ClanSubCommand> subCommands = new HashMap<>();
    private static ClansDB db;
    public static final Map<UUID, String> pendingInvites = new HashMap<>();
    public static final HashMap<Clans, HashMap<UUID, Double>> pendingBankNotes = new HashMap<>();
    public ClanCommand() {
        db = ClansDB.loadData(gangDB.getPath());
        subCommands.put("create", new CreateCommand());
        subCommands.put("rename", new RenameCommand());
        subCommands.put("bank", new BankCommand());
        subCommands.put("invite", new InviteCommand());
        subCommands.put("promote", new PromoteCommand());
        subCommands.put("demote", new DemoteCommand());
        subCommands.put("banish", new BanishCommand());
        subCommands.put("info", new InfoCommand());
        subCommands.put("accept", new AcceptCommand());
        subCommands.put("pass", new PassCommand());
        subCommands.put("disband", new DisbandCommand());
        subCommands.put("chat", new ChatCommand());
        subCommands.put("leave", new LeaveCommand());
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0)
            return false;

        String subCommandName = args[0];
        ClanSubCommand subCommand = subCommands.get(subCommandName);

        if (subCommand == null) {
            sender.sendMessage(CreateText.addColors("<red>Unknown subcommand<gray>: <white>" + subCommandName));
            return false;
        }

        boolean result = subCommand.execute(sender, args);

        if (result)
            db.saveData(gangDB.getPath());

        return result;
    }
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1)
            return new ArrayList<>(subCommands.keySet());

        String subCommandName = args[0];
        ClanSubCommand subCommand = subCommands.get(subCommandName);

        if (subCommand == null)
            return new ArrayList<>();

        return subCommand.tabComplete(sender, args);
    }
    public static String formatPlayerNames(List<UUID> uuids, int limit) {
        StringBuilder names = new StringBuilder();
        int count = 0;
        for (UUID uuid : uuids) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            if (!names.isEmpty()) names.append(", ");
            names.append(player.getName());
            count++;

            if (count >= limit) {
                names.append("\n");
                count = 0;
            }
        }
        return names.isEmpty() ? "none" : names.toString();
    }
    public static Clans getPlayerGang(Player player) {
        for (Map.Entry<String, Clans> entry : db.getGangs().entrySet()) {
            if (entry.getValue().getMembers().contains(player.getUniqueId()))
                return entry.getValue();
            if (entry.getValue().getGenerals().contains(player.getUniqueId()))
                return entry.getValue();
            if (entry.getValue().getLeaders().contains(player.getUniqueId()))
                return entry.getValue();
        }
        return null;
    }
    public static List<String> getPlayerNames() {
        List<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers())
            playerNames.add(player.getName());
        return playerNames;
    }
}
