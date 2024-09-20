package kingsbutbad.kingsbutbad.commands.Clans.ClansSubCommands;

import kingsbutbad.kingsbutbad.commands.Clans.ClanSubCommand;
import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

import static kingsbutbad.kingsbutbad.commands.Clans.ClanCommand.*;

public class BankCommand implements ClanSubCommand {
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
        if (args.length == 2)
            return Arrays.asList("add", "subtract", "accept", "denied");
        if(args[1].equalsIgnoreCase("accept") || args[1].equalsIgnoreCase("denied"))
            return getPlayerNames();
        return new ArrayList<>();
    }
}
