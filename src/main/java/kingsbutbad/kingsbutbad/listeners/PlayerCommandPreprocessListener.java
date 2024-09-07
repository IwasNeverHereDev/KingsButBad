package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CBP.CBPUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class PlayerCommandPreprocessListener implements Listener {
    public static HashMap<UUID, Integer> listOfCommandsPerSecond = new HashMap<>();
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
        if(listOfCommandsPerSecond.getOrDefault(event.getPlayer().getUniqueId(), 0) >= 5)
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kick " + event.getPlayer().getName() + " Command Spam [AUTOMATED]");
        listOfCommandsPerSecond.put(event.getPlayer().getUniqueId(), listOfCommandsPerSecond.getOrDefault(event.getPlayer().getUniqueId(), 0) + 1);
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingsButBad.pl, () -> listOfCommandsPerSecond.put(event.getPlayer().getUniqueId(), listOfCommandsPerSecond.getOrDefault(event.getPlayer().getUniqueId(), 0) - 1), 20);
        String name = "`"+event.getPlayer().getName()+"`";
        String cmd = "`"+event.getMessage()+"`";
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.DARK_GRAY);
        embedBuilder.setTitle("name: "+name);
        embedBuilder.setDescription("cmd: "+cmd);
        BotManager.getCommandsChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        String message = event.getMessage();
        if (message.startsWith("/report ")) {
            Player player = event.getPlayer();
            String[] args = message.split(" ", 3); // Split the command into parts
            if (args.length < 3)
                return;
            String targetPlayerName = args[1];  // The player being reported
            String reportMessage = args[2];     // The message/report content
            player.sendMessage("Report submitted for player: " + targetPlayerName);
            player.sendMessage("Message: " + reportMessage);
            BotManager.getReportChannel().createForumPost(targetPlayerName + " - REPORT [AUTOMATED]", MessageCreateData.fromEmbeds(CBPUtils.getReportEmbed(targetPlayerName, reportMessage, player.getName()).build()));
        }
    }
}
