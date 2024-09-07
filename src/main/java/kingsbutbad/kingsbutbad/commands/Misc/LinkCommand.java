package kingsbutbad.kingsbutbad.commands.Misc;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Random;

public class LinkCommand implements CommandExecutor {
    public static HashMap<Player, String> codes = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player p) {
            if (Keys.link.has(p)) {
                String linkedUserId = Keys.link.get(p);
                if (linkedUserId != null) {
                    var user = BotManager.getBot().getUserById(linkedUserId);
                    if (user != null) {
                        commandSender.sendMessage(CreateText.addColors("<gray>You have already been linked! (<white>" + user.getEffectiveName() + "<gray>)"));
                    } else {
                        commandSender.sendMessage(CreateText.addColors("<red>Error: Linked Discord user could not be found."));
                    }
                } else {
                    commandSender.sendMessage(CreateText.addColors("<red>Error: Linked user ID is null."));
                }
                return true;
            }
            if (codes.containsKey(p)) {
                commandSender.sendMessage(CreateText.addColors("<gray>You have already been given a code! (<white>" + codes.get(p) + "<gray>)"));
                return true;
            }
            Random random = new Random();
            int code = random.nextInt(1000, 9999);
            codes.put(p, String.valueOf(code));
            commandSender.sendMessage(CreateText.addColors("<gray>Your code to link your account is <white>" + code + "<gray> it will timeout in 1 min!"));
            Bukkit.getScheduler().scheduleSyncDelayedTask(KingsButBad.pl, () -> {
                if (codes.containsKey(p)) {
                    p.sendMessage(CreateText.addColors("<gray>Your code has expired!"));
                    codes.remove(p);
                }
            }, 20 * 60);
        } else {
            commandSender.sendMessage(CreateText.addColors("<red>This command can only be executed by a player."));
        }
        return true;
    }
}
