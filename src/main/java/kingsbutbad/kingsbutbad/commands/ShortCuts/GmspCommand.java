package kingsbutbad.kingsbutbad.commands.ShortCuts;

import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GmspCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player target = null;

        if (args.length > 0)
            target = Bukkit.getPlayer(args[0]);

        if (target == null && commandSender instanceof Player)
            target = (Player) commandSender;

        if (target == null) {
            commandSender.sendMessage(CreateText.addColors("<red>Player not found or you are not a player."));
            return true;
        }

        target.setGameMode(GameMode.SPECTATOR);
        commandSender.sendMessage(CreateText.addColors("<green>Successfully set " + target.getName() + " to spectator mode!"));

        return true;
    }
}
