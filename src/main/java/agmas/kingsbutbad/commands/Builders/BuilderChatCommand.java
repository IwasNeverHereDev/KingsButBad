package agmas.kingsbutbad.commands.Builders;

import agmas.kingsbutbad.Discord.BotManager;
import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.DiscordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuilderChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /builderchat <message>");
            return false;
        }

        // Combine all arguments into a single message string
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
            prefix = "<gray>("+CreateText.convertAmpersandToMiniMessage(KingsButBad.api.getUserManager().getUser(sender.getName()).getCachedData().getMetaData().getPrefix())+"<gray>) ";
        String messageFormated = CreateText.addColors("<gray>[<yellow>Builder Chat<gray>] "+prefix+sender.getName()+"<gray>: <white>"+msg);
        for (Player player : Bukkit.getOnlinePlayers())
            if (player.hasPermission("kbb.builder"))
                player.sendMessage(messageFormated);
        BotManager.getBuilderChannel().sendMessage("[Builder Chat] "+ChatColor.stripColor(CreateText.addColors(prefix))+ DiscordUtils.deformat(sender.getName()) +": "+DiscordUtils.deformat(msg)).queue();
    }
}
