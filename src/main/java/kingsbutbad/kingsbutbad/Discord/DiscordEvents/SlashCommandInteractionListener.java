package kingsbutbad.kingsbutbad.Discord.DiscordEvents;

import kingsbutbad.kingsbutbad.Discord.Commands.StaffCommands.AltsDiscordCommnad;
import kingsbutbad.kingsbutbad.Discord.Commands.StaffCommands.HistoryDiscordCommand;
import kingsbutbad.kingsbutbad.Discord.Commands.LinkDiscordCommand;
import kingsbutbad.kingsbutbad.Discord.Commands.PlayersDiscordCommand;
import kingsbutbad.kingsbutbad.Discord.Commands.Punishments.*;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.utils.CreateText;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class SlashCommandInteractionListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();

        if (commandName.equals("link"))
            new LinkDiscordCommand().onSlashCommandInteraction(event);
        if(commandName.equals("players"))
            new PlayersDiscordCommand().onSlashCommandInteraction(event);
        if(commandName.equals("whatkingdom") || commandName.equals("whatmap"))
            event.reply("The Server is on the `"+ ChatColor.stripColor(CreateText.addColors(KingdomsLoader.activeKingdom.getDisplayName())) +"` kingdom!").setEphemeral(true);
        if(commandName.equals("alts"))
            new AltsDiscordCommnad().onSlashCommandInteraction(event);
        if(commandName.equals("history"))
            new HistoryDiscordCommand().onSlashCommandInteraction(event);
        if(commandName.equals("mute"))
            new MuteDiscordCommand().onSlashCommandInteraction(event);
        if(commandName.equals("unmute"))
            new UnMuteDiscordCommand().onSlashCommandInteraction(event);
        if(commandName.equals("kick"))
            new KickDiscordCommand().onSlashCommandInteraction(event);
        if(commandName.equals("warn"))
            new WarnDiscordCommand().onSlashCommandInteraction(event);
        if(commandName.equals("unwarn"))
            new UnWarnDiscordCommand().onSlashCommandInteraction(event);
        if(commandName.equals("ban"))
            new BanDiscordCommand().onSlashCommandInteraction(event);
        if(commandName.equals("unban"))
            new UnBanDiscordCommand().onSlashCommandInteraction(event);
    }
}
