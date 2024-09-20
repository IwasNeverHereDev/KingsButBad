package kingsbutbad.kingsbutbad.CommandsCompleters.Dev;

import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SetRoleTabCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1)
            return null;
        if (args.length == 2)
            return Arrays.stream(Role.values())
                    .map(Role::name)
                    .filter(roleName -> roleName.startsWith(args[1].toUpperCase()))
                    .collect(Collectors.toList());
        return new ArrayList<>();
    }
}
