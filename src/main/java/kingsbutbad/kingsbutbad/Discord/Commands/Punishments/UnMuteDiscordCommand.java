package kingsbutbad.kingsbutbad.Discord.Commands.Punishments;

import kingsbutbad.kingsbutbad.KingsButBad;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class UnMuteDiscordCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            event.reply("Sorry, You don't have permission! (KICK_MEMBERS)").setEphemeral(true).queue();
            return;
        }

        String playerName = event.getOption("username").getAsString();

        if (playerName == null) {
            event.reply("All options (username) are required.").setEphemeral(true).queue();
            return;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (!player.hasPlayedBefore()) {
            event.reply("Player `" + playerName + "` has never played before.").setEphemeral(true).queue();
            return;
        }

        String command = String.format("unmute %s",
                playerName);

        event.reply("Processing the unmute command...").setEphemeral(true).queue();

        Bukkit.getScheduler().runTask(KingsButBad.pl, () -> {
            boolean success = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            if (success) {
                Bukkit.getScheduler().runTask(KingsButBad.pl, () -> event.getHook().editOriginal("Player `" + playerName + "` has been unmuted.").queue());
            } else {
                Bukkit.getScheduler().runTask(KingsButBad.pl, () -> event.getHook().editOriginal("Failed to execute the unmute command. Please check the command syntax.").queue());
            }
        });
    }
}
