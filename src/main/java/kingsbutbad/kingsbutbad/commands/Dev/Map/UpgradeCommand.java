package kingsbutbad.kingsbutbad.commands.Dev.Map;

import kingsbutbad.kingsbutbad.Kingdom.KingdomManager;
import kingsbutbad.kingsbutbad.Kingdom.Upgrades.Upgrade;
import kingsbutbad.kingsbutbad.Kingdom.Upgrades.UpgradeLoader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class UpgradeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/upgrade <upgrade> <action>"));
            return false;
        }
        Action action;
        try {
            action = Action.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(CreateText.addColors("<red>Invalid action. Valid actions are: UPGRADE, CLEAR, RELOAD"));
            return false;
        }
        if (action == Action.RELOAD) {
            new UpgradeLoader().read(new File(KingsButBad.pl.getDataFolder(), "Upgrades.json").toPath());
            sender.sendMessage(CreateText.addColors("<green>Successfully reloaded Upgrades.json! <gray>(<white>"
                    + UpgradeLoader.UpgradeList.size() + " upgrades found<gray>)"));
            return true;
        }
        String upgradeName = args[0];
        Upgrade targetUpgrade = UpgradeLoader.UpgradeList.stream()
                .filter(upgrade -> ChatColor.stripColor(CreateText.addColors(upgrade.getDisplayName()).toLowerCase())
                        .equals(upgradeName.toLowerCase()))
                .findFirst()
                .orElse(null);

        if (targetUpgrade == null) {
            sender.sendMessage(CreateText.addColors("<red>Upgrade not found<gray>: <white>" + upgradeName));
            return false;
        }

        if (action == Action.UPGRADE) {
            if (sender instanceof Player) {
                new KingdomManager().upgrade((Player) sender, targetUpgrade);
            } else {
                sender.sendMessage(CreateText.addColors("<red>Only players can use this command."));
            }
        } else if (action == Action.CLEAR) {
            KingdomManager.upgrades.remove(targetUpgrade);
            sender.sendMessage(CreateText.addColors("<green>Cleared upgrade<gray>: <white>" + targetUpgrade.getDisplayName()));
        }

        return true;
    }
    public enum Action {
        UPGRADE,
        CLEAR,
        RELOAD
    }
}
