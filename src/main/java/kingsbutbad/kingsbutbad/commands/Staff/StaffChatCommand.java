package kingsbutbad.kingsbutbad.commands.Staff;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.DiscordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /StaffChat <message>");
            return false;
        }

        String message = String.join(" ", args);
        sendStaffChat(sender, message);
        return true;
    }
    public static void sendStaffChat(CommandSender sender, String msg){
        if(!sender.hasPermission("kbb.staff")) {
            sender.sendMessage(CreateText.addColors("<red>You don't have access to Staff Chat! <gray>(<white>If your Builder change your selected Chat Settings<gray>)"));
            return;
        }
        String prefix = "";
        if(sender instanceof Player)
            prefix = "<gray>("+CreateText.convertAmpersandToMiniMessage(KingsButBad.api.getUserManager().getUser(sender.getName()).getCachedData().getMetaData().getPrefix())+"<gray>) ";
        String messageFormated = CreateText.addColors("<gray>[<red>Staff Chat<gray>] "+prefix+sender.getName()+"<gray>: <white>"+msg);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("kbb.staff")) {
                player.sendMessage(messageFormated);
            }
        }
        BotManager.getStafflogChannel().sendMessage("[Staff Chat] "+ChatColor.stripColor(CreateText.addColors(prefix))+ DiscordUtils.deformat(sender.getName()) +": "+DiscordUtils.deformat(msg)).queue();
    }
}
