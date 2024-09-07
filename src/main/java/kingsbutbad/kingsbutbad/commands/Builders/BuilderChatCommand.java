package kingsbutbad.kingsbutbad.commands.Builders;

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

import java.util.Objects;

public class BuilderChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(CreateText.addColors("<red>Usage<gray>: <white>/BuilderChat <message>"));
            return false;
        }

        String message = String.join(" ", args);
        sendBuilderChat(sender, message);

        return true;
    }
    public static void sendBuilderChat(CommandSender sender, String msg){
        if(!sender.hasPermission("kbb.builder")) {
            sender.sendMessage(CreateText.addColors("<red>You don't have access to Builder Chat! <gray>(<white>If your Staff change your selected Chat Settings<gray>)"));
            return;
        }
        String prefix = "";
        if(sender instanceof Player)
            prefix = "<gray>("+CreateText.convertAmpersandToMiniMessage(Objects.requireNonNull(Objects.requireNonNull(KingsButBad.api.getUserManager().getUser(sender.getName())).getCachedData().getMetaData().getPrefix()))+"<gray>) ";
        String messageFormated = CreateText.addColors("<gray>[<yellow>Builder Chat<gray>] "+prefix+sender.getName()+"<gray>: <white>"+msg);
        for (Player player : Bukkit.getOnlinePlayers())
            if (player.hasPermission("kbb.builder"))
                player.sendMessage(messageFormated);
        BotManager.getBuilderChannel().sendMessage("[Builder Chat] "+ChatColor.stripColor(CreateText.addColors(prefix))+ DiscordUtils.deformat(sender.getName()) +": "+DiscordUtils.deformat(msg)).queue();
    }
}
