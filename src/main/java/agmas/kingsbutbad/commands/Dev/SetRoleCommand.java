package agmas.kingsbutbad.commands.Dev;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetRoleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        // Check if the correct number of arguments are provided
        if (strings.length < 2) {
            commandSender.sendMessage("Usage: /setrole <player> <role>");
            return false;
        }

        // Retrieve and validate the player
        Player p = Bukkit.getPlayer(strings[0]);
        if (p == null) {
            commandSender.sendMessage("Player not found.");
            if(commandSender instanceof Player)
                p = Bukkit.getPlayer(commandSender.getName());
            else
                return false;
        }
        Role role;
        try {
            role = Role.valueOf(strings[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            commandSender.sendMessage("Invalid role specified.");
            return false;
        }
        KingsButBad.roles.put(p, role);
        RoleManager.givePlayerRole(p);
        commandSender.sendMessage("Role set successfully.");

        return true;
    }
}
