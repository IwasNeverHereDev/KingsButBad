package kingsbutbad.kingsbutbad.Discord.Commands;

import kingsbutbad.kingsbutbad.keys.Keys;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayersDiscordCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        int playerCount = 0;
        int vanishCount = 0;
        StringBuilder players = new StringBuilder();
        StringBuilder vanishedPlayers = new StringBuilder();
        for(Player p : Bukkit.getOnlinePlayers()) {
            String playerlistname = ChatColor.stripColor(p.getPlayerListName());
            if (Keys.vanish.get(p, false)) {
                vanishedPlayers.append(playerlistname).append("\n");
                vanishCount++;
            } else {
                playerCount++;
                players.append(playerlistname).append("\n");
            }
        }
        vanishedPlayers.append("\n(VISIBLE TO STAFF ONLY)\n");
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.addField("**Online Players**: `"+playerCount+"`", players.toString(), false);
        if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE))
            embedBuilder.addField("**Vanish Players **: `"+vanishCount+"`", vanishedPlayers.toString(), false);
        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }
}
