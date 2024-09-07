package kingsbutbad.kingsbutbad.commands.Clans;

import kingsbutbad.kingsbutbad.NoNoWords;
import kingsbutbad.kingsbutbad.keys.Keys;
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

public class ClanCommand implements CommandExecutor, TabCompleter { // TODO: Clean up This File (ClanCommand.java)
    private final File gangDB = new File(ClansDB.getDataFolder(), "gangs.gz");
    private final Map<String, SubCommand> subCommands = new HashMap<>();
    private final ClansDB databaseManager;
    private final Map<UUID, String> pendingInvites = new HashMap<>(); // Stores player invites
    private final HashMap<Clans, HashMap<UUID, Double>> pendingBankNotes = new HashMap<>();

    public ClanCommand() {
        databaseManager = ClansDB.loadData(gangDB.getPath());
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
        if (args.length == 0) {
            return false;
        }

        String subCommandName = args[0];
        SubCommand subCommand = subCommands.get(subCommandName);

        if (subCommand == null) {
            sender.sendMessage("Unknown subcommand: " + subCommandName);
            return false;
        }

        boolean result = subCommand.execute(sender, args);

        if (result) {
            databaseManager.saveData(gangDB.getPath());
        }

        return result;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(subCommands.keySet());
        }

        String subCommandName = args[0];
        SubCommand subCommand = subCommands.get(subCommandName);

        if (subCommand == null) {
            return new ArrayList<>();
        }

        return subCommand.tabComplete(sender, args);
    }

    private interface SubCommand {
        boolean execute(CommandSender sender, String[] args);
        List<String> tabComplete(CommandSender sender, String[] args);
    }

    private class AcceptCommand implements SubCommand {
        @Override
        public boolean execute(CommandSender sender, String[] args) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage("This command can only be used by players.");
                return false;
            }
            if (args.length < 2) {
                sender.sendMessage("Usage: /clan accept <gangName>");
                return false;
            }
            String gangName = args[1];
            Clans gang = databaseManager.getGangs().get(gangName);
            if (gang == null) {
                sender.sendMessage("Gang not found.");
                return false;
            }
            if (!pendingInvites.containsKey(p.getUniqueId()) || !pendingInvites.get(p.getUniqueId()).equals(gangName)) {
                sender.sendMessage("You have no pending invite from this gang.");
                return false;
            }
            gang.getMembers().add(p.getUniqueId());
            pendingInvites.remove(p.getUniqueId());
            gang.broadcast(sender.getName()+ " has joined the gang!");
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            if (args.length == 2) {
                return new ArrayList<>(databaseManager.getGangs().keySet());
            }
            return new ArrayList<>();
        }
    }

    private class InviteCommand implements SubCommand {
        @Override
        public boolean execute(CommandSender sender, String[] args) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
                return false;
            }
            if (args.length < 2) {
                sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan invite <playerName>"));
                return false;
            }
            String playerName = args[1];
            Player targetPlayer = Bukkit.getPlayer(playerName);
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(CreateText.addColors("<red>Player not found or offline."));
                return false;
            }
            Clans gang = getPlayerGang(p);
            if (gang == null) {
                sender.sendMessage(CreateText.addColors("<red>Your not in any Clan!"));
                return false;
            }
            if (!gang.getLeaders().contains(p.getUniqueId())) {
                sender.sendMessage(CreateText.addColors("<red>You are not a Leader."));
                return false;
            }
            if (gang.getMembers().contains(targetPlayer.getUniqueId())) {
                sender.sendMessage(CreateText.addColors("<red>Player is already in the clan!"));
                return false;
            }
            for(String clanNames : databaseManager.getGangs().keySet()) {
                Clans clans = databaseManager.getGangs().get(clanNames);
                if(clans.getMembers().contains(targetPlayer.getUniqueId())) {
                    sender.sendMessage(CreateText.addColors("<red>Players is already apart of another gang! <gray>(<white>"+clans.getName()+"<gray>)"));
                    return true;
                }
                if(clans.getGenerals().contains(targetPlayer.getUniqueId())) {
                    sender.sendMessage(CreateText.addColors("<red>Players is already a General of another gang! <gray>(<white>"+clans.getName()+"<gray>)"));
                    return true;
                }
                if(clans.getLeaders().contains(targetPlayer.getUniqueId())) {
                    sender.sendMessage(CreateText.addColors("<red>Players is already a Leader of another gang! <gray>(<white>"+clans.getName()+"<gray>)"));
                    return true;
                }
            }
            pendingInvites.put(targetPlayer.getUniqueId(), gang.getName());
            gang.broadcast(targetPlayer.getName() + " was Invited to the Clan!");
            targetPlayer.sendMessage(CreateText.addColors("<red>You have been Invited to a Clan named "+gang.getName()+" by "+sender.getName()+"! <gray>(<white>/clan accept "+gang.getName()+"<gray>)"));
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            if (args.length == 2) {
                return getPlayerNames();
            }
            return new ArrayList<>();
        }

        private Clans getPlayerGang(Player player) {
            for (Map.Entry<String, Clans> entry : databaseManager.getGangs().entrySet()) {
                if (entry.getValue().getMembers().contains(player.getUniqueId()))
                    return entry.getValue();
                if (entry.getValue().getLeaders().contains(player.getUniqueId()))
                    return entry.getValue();
                if (entry.getValue().getLeaders().contains(player.getUniqueId()))
                    return entry.getValue();
            }
            return null;
        }

        private List<String> getPlayerNames() {
            List<String> playerNames = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if(Keys.vanish.get(player, false)) continue;
                playerNames.add(player.getName());
            }
            return playerNames;
        }
    }
    private class CreateCommand implements SubCommand {
        @Override
        public boolean execute(CommandSender sender, String[] args) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
                return false;
            }
            if (args.length < 2) {
                sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan create <clanName>"));
                return false;
            }
            String gangName = args[1];
            if (databaseManager.getGangs().containsKey(gangName)) {
                sender.sendMessage(CreateText.addColors("<red>A clan with this name already exists."));
                return false;
            }
            if(!NoNoWords.isClean(gangName)){
                sender.sendMessage(CreateText.addColors("<red>Your not allowed to name your Clan's name that!"));
                return true;
            }
            if(gangName.length() >= 15){
                sender.sendMessage(CreateText.addColors("<red>Your Clan is to long of name! <gray>(<white>"+gangName.length()+"<gray>)"));
                return true;
            }
            if(getPlayerGang(((Player) sender).getPlayer()) != null){
                sender.sendMessage(CreateText.addColors("<red>You can't create a clan while being in one!"));
                return true;
            }
            Clans newGang = new Clans(List.of(p.getUniqueId()), new ArrayList<>(), new ArrayList<>(), gangName, 0.0, 1, 0.0, new HashMap<>(), new ArrayList<>());
            databaseManager.getGangs().put(gangName, newGang);
            sender.sendMessage(CreateText.addColors("<green>Clan " + gangName + " created successfully."));
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            return new ArrayList<>();
        }
    }

    private class RenameCommand implements SubCommand {
        @Override
        public boolean execute(CommandSender sender, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
                return false;
            }
            if (args.length < 2) {
                sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan rename <newName>"));
                return false;
            }
            String newName = args[1];
            if(!NoNoWords.isClean(newName)){
                sender.sendMessage(CreateText.addColors("This Clan name contains Filtered Words!"));
                return true;
            }
            Clans gang = getPlayerGang(((Player) sender).getPlayer());
            if (gang == null) {
                sender.sendMessage(CreateText.addColors("<red>Clan not found."));
                return false;
            }
            if(!gang.getLeaders().contains(((Player) sender).getUniqueId())){
                sender.sendMessage(CreateText.addColors("<red>Only the Leader(s) can do this!"));
                return true;
            }
            if(newName.length() >= 15){
                sender.sendMessage(CreateText.addColors("<red>Your Clan's Name is to long! <gray>(<white>"+newName.length()+"<gray>)"));
                return true;
            }
            databaseManager.getGangs().remove(gang.getName());
            databaseManager.getGangs().put(newName, gang);
            gang.setName(newName);
            gang.broadcast("Clan's name was changed to "+newName+"!");
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            return new ArrayList<>();
        }
    }
    private class DisbandCommand implements SubCommand {
        @Override
        public boolean execute(CommandSender sender, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
                return false;
            }
            if (args.length < 1) {
                sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan disband"));
                return false;
            }
            if(getPlayerGang((Player) sender) == null){
                sender.sendMessage(CreateText.addColors("<red>Your not in a Clan!"));
                return true;
            }
            Clans gang = getPlayerGang(((Player) sender).getPlayer());
            if (gang == null) {
                sender.sendMessage(CreateText.addColors("<red>Clan not found."));
                return false;
            }
            if(!gang.getLeaders().contains(((Player) sender).getUniqueId())){
                sender.sendMessage(CreateText.addColors("<red>Only the Leader(s) can do this!"));
                return true;
            }
            gang.broadcast("Clan was deleted... RIP");
            pendingBankNotes.remove(gang);
            pendingBankNotes.remove(gang);
            Keys.money.addDouble(((Player) sender), gang.getBankAmount());
            databaseManager.getGangs().remove(gang.getName());
            databaseManager.saveData(new File(ClansDB.getDataFolder(), "gangs.gz").getPath());
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            return new ArrayList<>();
        }
    }

    private class LeaveCommand implements SubCommand {
        @Override
        public boolean execute(CommandSender sender, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
                return false;
            }
            if (args.length < 1) {
                sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan leave"));
                return false;
            }
            if(getPlayerGang((Player) sender) == null){
                sender.sendMessage(CreateText.addColors("<red>Your not in a clan!"));
                return true;
            }
            Clans gang = getPlayerGang(((Player) sender).getPlayer());
            if (gang == null) {
                sender.sendMessage(CreateText.addColors("<red>Clan not found."));
                return false;
            }
            if(gang.getLeaders().contains(((Player) sender).getUniqueId())){
                sender.sendMessage(CreateText.addColors("Only the Member(s) and General(s) can do this!"));
                return true;
            }
            gang.broadcast(sender.getName()+" has left the Clan!");
            gang.getMembers().remove(((Player) sender).getUniqueId());
            gang.getGenerals().remove(((Player) sender).getUniqueId());
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            return new ArrayList<>();
        }
    }

    private class ChatCommand implements SubCommand {
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
            for (int i = 1; i < args.length; i++) {
                messageBuilder.append(args[i]).append(" ");
            }
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

    private class BankCommand implements SubCommand {
        @Override
        public boolean execute(CommandSender sender, String[] args) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
                return false;
            }

            if (args.length < 3) {
                sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan bank <add|subtract|accept|denied> <amount|player>"));
                return false;
            }

            String action = args[1];
            double amount = 0;
            Player targetPlayer;

            // Parse the amount or check if it's a player
            try {
                amount = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                targetPlayer = Bukkit.getPlayer(args[2]);
                if (targetPlayer == null) {
                    sender.sendMessage(CreateText.addColors("<red>Invalid amount or player. <gray>(<white>"+args[2]+"<gray>)"));
                    return false;
                }
            }

            Clans gang = getPlayerGang(player);
            if (gang == null) {
                sender.sendMessage(CreateText.addColors("<red>You are not in any clan."));
                return false;
            }

            switch (action.toLowerCase()) {
                case "add":
                    if (amount < 1) {
                        sender.sendMessage(CreateText.addColors("<red>Amount must be greater than zero."));
                        return false;
                    }
                    if (Keys.money.get(player, 0.0) < amount) {
                        sender.sendMessage(CreateText.addColors("<red>You don't have enough money for this transaction."));
                        return true;
                    }
                    gang.setBankAmount(gang.getBankAmount() + amount);
                    if(!gang.getLeaders().contains(((Player) sender).getUniqueId()))
                        gang.getAmountPutInBank().put(((Player) sender).getUniqueId(), gang.getAmountPutInBank().getOrDefault(((Player) sender).getUniqueId(), 0.0) + amount);
                    Keys.money.subtractDouble(player, amount);
                    gang.broadcast(player.getName() + " added " + amount + " to the Clan's bank! (Total: " + gang.getBankAmount() + ")");
                    break;

                case "subtract":
                    if (amount < 1) {
                        sender.sendMessage(CreateText.addColors("<red>Amount must be greater than zero."));
                        return false;
                    }
                    if (gang.getBankAmount() < amount) {
                        sender.sendMessage(CreateText.addColors("<red>The Clan's bank doesn't have enough funds."));
                        return true;
                    }
                    if (!gang.getLeaders().contains(player.getUniqueId())) {
                        if (gang.getAmountPutInBank().getOrDefault(player.getUniqueId(), 0.0) <= amount) {
                            HashMap<UUID, Double> requests = pendingBankNotes.getOrDefault(gang, new HashMap<>());
                            requests.put(player.getUniqueId(), amount);
                            pendingBankNotes.put(gang, requests);
                            gang.broadcast(player.getName() + " requested " + amount + " from the clan bank! (/clan bank accept/denied <player>)");
                            return true;
                        }
                    }
                        gang.setBankAmount(gang.getBankAmount() - amount);
                        Keys.money.addDouble(player, amount);
                        gang.getAmountPutInBank().put(((Player) sender).getUniqueId(), gang.getAmountPutInBank().getOrDefault(((Player) sender).getUniqueId(), 0.0) - amount);
                        gang.broadcast(player.getName() + " withdrew " + amount + " from the Clan's bank! (Total: " + gang.getBankAmount() + ")");
                    break;

                case "accept":
                    targetPlayer = Bukkit.getPlayer(args[2]);
                    if (targetPlayer == null) {
                        sender.sendMessage(CreateText.addColors("<red>Player not found."));
                        return false;
                    }
                    if (gang.getMembers().contains(player.getUniqueId())) {
                        sender.sendMessage(CreateText.addColors("<red>You need to be a General or Leader to use this command."));
                        return false;
                    }
                    if (targetPlayer == sender) {
                        sender.sendMessage(CreateText.addColors("<red>You can't accept your own transaction."));
                        return false;
                    }
                    HashMap<UUID, Double> requests = pendingBankNotes.get(gang);
                    if (requests == null || requests.get(targetPlayer.getUniqueId()) == null) {
                        sender.sendMessage(CreateText.addColors("<red>This player hasn't requested any funds."));
                        return true;
                    }
                    double requestedAmount = requests.get(targetPlayer.getUniqueId());
                    if (gang.getBankAmount() < requestedAmount) {
                        sender.sendMessage(CreateText.addColors("<red>The Clan's bank doesn't have enough funds to accept this request. Denying..."));
                        gang.broadcast(targetPlayer.getName() + "'s request was denied due to insufficient funds.");
                        requests.remove(targetPlayer.getUniqueId());
                        return true;
                    }
                    gang.setBankAmount(gang.getBankAmount() - requestedAmount);
                    Keys.money.addDouble(targetPlayer, requestedAmount);
                    requests.remove(targetPlayer.getUniqueId());
                    gang.broadcast(targetPlayer.getName() + "'s request for " + requestedAmount + " was accepted.");
                    break;

                case "denied":
                    targetPlayer = Bukkit.getPlayer(args[2]);
                    if (targetPlayer == null) {
                        sender.sendMessage(CreateText.addColors("<red>Player not found."));
                        return false;
                    }
                    if (gang.getMembers().contains(player.getUniqueId())) {
                        sender.sendMessage(CreateText.addColors("<red>You need to be a General or Leader to use this command."));
                        return false;
                    }
                    requests = pendingBankNotes.get(gang);
                    if (requests != null && requests.get(targetPlayer.getUniqueId()) != null) {
                        gang.broadcast(targetPlayer.getName() + "'s request for " + requests.get(targetPlayer.getUniqueId()) + " was denied.");
                        requests.remove(targetPlayer.getUniqueId());
                    } else {
                        sender.sendMessage(CreateText.addColors("<red>This player doesn't have any pending requests."));
                    }
                    break;

                default:
                    sender.sendMessage(CreateText.addColors("<red>Invalid action. Use<gray>: <white>add, subtract, accept, or denied."));
                    return false;
            }

            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            if (args.length == 2) {
                return Arrays.asList("add", "subtract", "accept", "denied");
            }
            if(args[1].equalsIgnoreCase("accept") || args[1].equalsIgnoreCase("denied"))
                return getPlayerNames();
            return new ArrayList<>();
        }
    }
    private class PassCommand implements SubCommand {
        @Override
        public boolean execute(CommandSender sender, String[] args) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
                return false;
            }
            if (args.length < 2) {
                sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan pass <playerName>"));
                return false;
            }
            String playerName = args[1];
            Player targetPlayer = Bukkit.getPlayer(playerName);
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(CreateText.addColors("<red>Player not found or offline."));
                return false;
            }
            Clans gang = getPlayerGang(p);
            if (gang == null) {
                sender.sendMessage(CreateText.addColors("<red>You are not in any clan."));
                return false;
            }
            if (!gang.getLeaders().contains(p.getUniqueId())) {
                sender.sendMessage(CreateText.addColors("<red>You are not a leader."));
                return false;
            }
            Clans targetGang = getPlayerGang(targetPlayer);
            if (targetGang == null || targetGang != gang) {
                sender.sendMessage(CreateText.addColors("<red>Your target is in another clan or no clan at all!"));
                return false;
            }
            if(gang.getLeaders().contains(targetPlayer.getUniqueId())){
                sender.sendMessage(CreateText.addColors("<red>Your already a leader!"));
                return true;
            }
            List<UUID> leaders = new ArrayList<>(gang.getLeaders());
            leaders.remove(p.getUniqueId());
            leaders.add(targetPlayer.getUniqueId());

            gang.setLeaders(leaders);

            gang.getGenerals().remove(targetPlayer.getUniqueId());
            if (gang.getMembers().contains(targetPlayer.getUniqueId())) {
                gang.getMembers().remove(targetPlayer.getUniqueId());
            }
            gang.getMembers().add(((Player) sender).getUniqueId());

            gang.broadcast(targetPlayer.getName() + " is now the Leader!");
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            if (args.length == 2)
                return getPlayerNames();
            return new ArrayList<>();
        }
    }

    private class PromoteCommand implements SubCommand {
        @Override
        public boolean execute(CommandSender sender, String[] args) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
                return false;
            }
            if (args.length < 2) {
                sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan promote <playerName>"));
                return false;
            }
            String playerName = args[1];
            Player targetPlayer = Bukkit.getPlayer(playerName);
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(CreateText.addColors("<red>Player not found or offline."));
                return false;
            }
            Clans gang = getPlayerGang(p);
            if (gang == null) {
                sender.sendMessage(CreateText.addColors("<red>You are not in any clan."));
                return false;
            }
            if (!gang.getLeaders().contains(p.getUniqueId())) {
                sender.sendMessage(CreateText.addColors("<red>You are not a leader."));
                return false;
            }
            if (!gang.getMembers().contains(targetPlayer.getUniqueId())) {
                sender.sendMessage(CreateText.addColors("<red>Player is not a member of the clan."));
                return false;
            }
            gang.getGenerals().add(targetPlayer.getUniqueId());
            gang.getMembers().remove(targetPlayer.getUniqueId());
            gang.broadcast(targetPlayer.getName()+" was Promoted to General!");
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            if (args.length == 2) {
                return getPlayerNames();
            }
            return new ArrayList<>();
        }
    }

    private class DemoteCommand implements SubCommand {
        @Override
        public boolean execute(CommandSender sender, String[] args) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
                return false;
            }
            if (args.length < 2) {
                sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan demote <playerName>"));
                return false;
            }
            String playerName = args[1];
            Player targetPlayer = Bukkit.getPlayer(playerName);
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(CreateText.addColors("<red>Player not found or offline."));
                return false;
            }
            Clans gang = getPlayerGang(p);
            if (gang == null) {
                sender.sendMessage(CreateText.addColors("<red>You are not in any clan."));
                return false;
            }
            if (!gang.getLeaders().contains(p.getUniqueId())) {
                sender.sendMessage(CreateText.addColors("<red>You are not a leader."));
                return false;
            }
            if (!gang.getGenerals().contains(targetPlayer.getUniqueId())) {
                sender.sendMessage(CreateText.addColors("<red>Player is not a general of the clan."));
                return false;
            }
            gang.getGenerals().remove(targetPlayer.getUniqueId());
            gang.getMembers().add(targetPlayer.getUniqueId());
            gang.broadcast(targetPlayer.getName()+" was Demoted from General!");
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            if (args.length == 2) {
                return getPlayerNames();
            }
            return new ArrayList<>();
        }
    }
    private class BanishCommand implements SubCommand {
        @Override
        public boolean execute(CommandSender sender, String[] args) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
                return false;
            }
            if (args.length < 2) {
                sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/clan banish <playerName>"));
                return false;
            }
            String playerName = args[1];
            Player targetPlayer = Bukkit.getPlayer(playerName);
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(CreateText.addColors("<red>Player not found or offline."));
                return false;
            }
            Clans gang = getPlayerGang(p);
            if (gang == null) {
                sender.sendMessage(CreateText.addColors("<red>You are not in any clan."));
                return false;
            }
            if (!gang.getLeaders().contains(p.getUniqueId())) {
                sender.sendMessage(CreateText.addColors("<red>You are not a leader."));
                return false;
            }
            if (!gang.getMembers().contains(targetPlayer.getUniqueId())) {
                sender.sendMessage(CreateText.addColors("<red>Player is not a member of the clan."));
                return false;
            }
            gang.getMembers().remove(targetPlayer.getUniqueId());
            gang.broadcast(targetPlayer.getName()+" was Banished from the clan!");
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            if (args.length == 2) {
                return getPlayerNames();
            }
            return new ArrayList<>();
        }
    }
    private class InfoCommand implements SubCommand {
        @Override
        public boolean execute(CommandSender sender, String[] args) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage(CreateText.addColors("<red>This command can only be used by players."));
                return false;
            }
            Clans gang = getPlayerGang(p);
            if(args.length == 2) {
                String gangName = args[0];
                if(!databaseManager.getGangs().containsKey(gangName)){
                    sender.sendMessage(CreateText.addColors("<red>There is no clan by that name!"));
                    return true;
                }
                gang = databaseManager.getGangs().get(gangName);
            }
            if (gang == null) {
                sender.sendMessage(CreateText.addColors("<red>You are not in any clan."));
                return false;
            }
            sender.sendMessage(CreateText.addColors("<red>Clan's Name<gray>: <white>") + gang.getName());
            sender.sendMessage(CreateText.addColors("<red>Member(s) Count<gray>: <white>") + gang.getMembers().size());
            sender.sendMessage(CreateText.addColors("<red>General(s) Count<gray>: <white>") + gang.getGenerals().size());
            sender.sendMessage(CreateText.addColors("<red>Leader(s) Count<gray>: <white>") + gang.getLeaders().size());
            sender.sendMessage(CreateText.addColors("<red>Clan's Bank<gray>: <white>") + gang.getBankAmount());
            sender.sendMessage(CreateText.addColors("<red>Clan's Level<gray>: <white>") + gang.getLevel());
            sender.sendMessage(CreateText.addColors("<red>Clan's Exp<gray>: <white>") + gang.getExp());
            StringBuilder quests = new StringBuilder();
            for(ClanQuests gangQuests : gang.getActiveGangQuests())
                quests.append(gangQuests.getName()).append(", ");
            if(quests.toString().isEmpty())
                quests = new StringBuilder("none");
            sender.sendMessage(CreateText.addColors("<red>Clan's Active Quest<gray>: <white>") + quests);
            StringBuilder leadersNames = new StringBuilder();
            StringBuilder generalsNames = new StringBuilder();
            StringBuilder membersNames = new StringBuilder();
            int membersCount = 0;
            int leadersCount = 0;
            int generalsCount = 0;
            for(OfflinePlayer op : Bukkit.getOfflinePlayers()){
                if(gang.getGenerals().contains(op.getUniqueId())) {
                    generalsNames.append(op.getName()).append(",");
                    generalsCount++;
                    if(generalsCount >= 5) generalsNames.append("\n");
                    continue;
                }
                if(gang.getLeaders().contains(op.getUniqueId())) {
                    leadersNames.append(op.getName()).append(",");
                    leadersCount++;
                    if(leadersCount >= 5) leadersNames.append("\n");
                    continue;
                }
                if(gang.getMembers().contains(op.getUniqueId())) {
                    membersNames.append(op.getName()).append(",");
                    membersCount++;
                    if(membersCount >= 5) membersNames.append("\n");
                }
            }
            if(leadersNames.toString().isEmpty()) leadersNames = new StringBuilder("none");
            if(generalsNames.toString().isEmpty()) generalsNames = new StringBuilder("none");
            if(membersNames.toString().isEmpty()) membersNames = new StringBuilder("none");
            sender.sendMessage(CreateText.addColors("<red>Leader(s) Names<gray>: <white>") + leadersNames);
            sender.sendMessage(CreateText.addColors("<red>General(s) Names<gray>: <white>") + generalsNames);
            sender.sendMessage(CreateText.addColors("<red>Member(s) Names<gray>: <white>") + membersNames);
            return true;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String[] args) {
            return new ArrayList<>();
        }
    }
    private Clans getPlayerGang(Player player) {
        for (Map.Entry<String, Clans> entry : databaseManager.getGangs().entrySet()) {
            if (entry.getValue().getMembers().contains(player.getUniqueId())) {
                return entry.getValue();
            }
            if (entry.getValue().getGenerals().contains(player.getUniqueId())) {
                return entry.getValue();
            }
            if (entry.getValue().getLeaders().contains(player.getUniqueId())) {
                return entry.getValue();
            }
        }
        return null;
    }

    private List<String> getPlayerNames() {
        List<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerNames.add(player.getName());
        }
        return playerNames;
    }
}
