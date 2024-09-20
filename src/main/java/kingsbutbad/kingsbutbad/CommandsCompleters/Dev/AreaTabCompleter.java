package kingsbutbad.kingsbutbad.CommandsCompleters.Dev;

import kingsbutbad.kingsbutbad.Kingdom.Areas.Area;
import kingsbutbad.kingsbutbad.Kingdom.Areas.AreaLoaders;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AreaTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> subCommands = new ArrayList<>();
            subCommands.add("tp");
            subCommands.add("get");
            subCommands.add("list");
            subCommands.add("reload");
            return subCommands.stream().filter(sub -> sub.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        } else if (args.length == 2 && args[0].equalsIgnoreCase("tp")) {
            return AreaLoaders.AreasList.stream()
                    .map(Area::getDisplayName)
                    .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
            return AreaLoaders.AreasList.stream()
                    .map(Area::getDisplayName)
                    .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
