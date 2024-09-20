package kingsbutbad.kingsbutbad.CommandsCompleters.Dev;

import kingsbutbad.kingsbutbad.Kingdom.Upgrades.UpgradeLoader;
import kingsbutbad.kingsbutbad.commands.Dev.Map.UpgradeCommand;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpgradeTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions = UpgradeLoader.UpgradeList.stream()
                    .map(upgrade -> ChatColor.stripColor(CreateText.addColors(upgrade.getDisplayName())))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            for (UpgradeCommand.Action action : UpgradeCommand.Action.values())
                completions.add(action.name().toLowerCase());
        }
        return completions.stream()
                .filter(s -> s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .collect(Collectors.toList());
    }
}
