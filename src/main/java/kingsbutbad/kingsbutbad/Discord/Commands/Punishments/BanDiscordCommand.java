package kingsbutbad.kingsbutbad.Discord.Commands.Punishments;

import kingsbutbad.kingsbutbad.KingsButBad;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class BanDiscordCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            event.reply("Sorry, You don't have permission! (KICK_MEMBERS)").setEphemeral(true).queue();
            return;
        }

        String playerName = event.getOption("username").getAsString();
        String time = event.getOption("time").getAsString();
        String reason = event.getOption("reason").getAsString();

        if (playerName == null || time == null || reason == null) {
            event.reply("All options (username, time, reason) are required.").setEphemeral(true).queue();
            return;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (!player.hasPlayedBefore()) {
            event.reply("Player `" + playerName + "` has never played before.").setEphemeral(true).queue();
            return;
        }

        if (player.isOp() || hasBypassPermission(player)) {
            event.reply("Player `" + playerName + "` cannot be banned.").setEphemeral(true).queue();
            return;
        }

        String command = String.format("ban %s %s %s (Banned by %s)",
                playerName,
                time,
                reason,
                event.getMember().getEffectiveName());

        event.reply("Processing the ban command...").setEphemeral(true).queue();

        Bukkit.getScheduler().runTask(KingsButBad.pl, () -> {
            boolean success = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            if (success) {
                Bukkit.getScheduler().runTask(KingsButBad.pl, () -> event.getHook().editOriginal("Player `" + playerName + "` has been banned for `" + time + "` with reason: `" + reason + "`.").queue());
            } else {
                Bukkit.getScheduler().runTask(KingsButBad.pl, () -> event.getHook().editOriginal("Failed to execute the ban command. Please check the command syntax.").queue());
            }
        });
    }

    private boolean hasBypassPermission(OfflinePlayer player) {
        return player.getPlayer() != null && player.getPlayer().hasPermission("custombansplus.bypass");
    }
}