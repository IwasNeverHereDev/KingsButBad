package kingsbutbad.kingsbutbad.commands.Dev.Map;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetRoleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length < 2) {
            commandSender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/SetRole <player> <role>"));
            return false;
        }

        Player p = Bukkit.getPlayer(strings[0]);
        if (p == null) {
            commandSender.sendMessage(CreateText.addColors("<red>Player not found."));
            if(commandSender instanceof Player)
                p = Bukkit.getPlayer(commandSender.getName());
            else
                return false;
        }
        Role role;
        try {
            role = Role.valueOf(strings[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            commandSender.sendMessage(CreateText.addColors("<red>Invalid role specified."));
            return false;
        }
        KingsButBad.roles.put(p, role);
        RoleManager.givePlayerRole(p);
        commandSender.sendMessage(CreateText.addColors("<green>Role set successfully."));
        return true;
    }
}
