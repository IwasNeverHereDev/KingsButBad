package kingsbutbad.kingsbutbad.Discord.Commands.StaffCommands;

import kingsbutbad.kingsbutbad.KingsButBad;
import me.coralise.spigot.objects.HistoryRecord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HistoryDiscordCommand extends ListenerAdapter {

    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String playerName = event.getOption("username").getAsString();
        int pageNumber = event.getOption("page").getAsInt();
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        UUID playerUUID = player.getUniqueId();

        ArrayList<HistoryRecord> history = KingsButBad.cbp.getDatabase().getHistories(playerUUID);
        if (history == null)
            history = new ArrayList<>();

        sendPaginatedHistory(event, history, pageNumber);
    }

    private void sendPaginatedHistory(SlashCommandInteractionEvent event, List<HistoryRecord> history, int page) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED);
        embedBuilder.setTitle(event.getOption("username").getAsString());

        int start = page * RECORDS_PER_PAGE;
        int end = Math.min(start + RECORDS_PER_PAGE, history.size());

        if (start < history.size()) {
            for (int i = start; i < end; i++) {
                HistoryRecord record = history.get(i);
                String fieldTitle = record.getId() + " - " + record.getType().name();
                String fieldValue = "Reason: " + record.getReason() + "\nStatus: " + record.getStatus();
                embedBuilder.addField(fieldTitle, fieldValue, false);
            }
        } else {
            embedBuilder.setDescription("No history found for this page.");
        }
        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }
}