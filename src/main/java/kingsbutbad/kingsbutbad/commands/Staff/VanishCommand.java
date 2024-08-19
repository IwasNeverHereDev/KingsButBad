package kingsbutbad.kingsbutbad.commands.Staff;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VanishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player p)) return true;
        boolean isInVanish = Keys.vanish.get(p, false);
        Keys.vanish.set(p, !isInVanish);
        if(!isInVanish)
            commandSender.sendMessage(CreateText.addColors("<gray>You are now in Vanish! <gray>(<white>Only Staff can see you now!<gray>)"));
        else {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.showPlayer(KingsButBad.pl, p);
            });
            commandSender.sendMessage(CreateText.addColors("<gray>You are no longer in Vanish! <gray>(<white>Everyone can see you now!<gray>)"));
        }
        return false;
    }
}
