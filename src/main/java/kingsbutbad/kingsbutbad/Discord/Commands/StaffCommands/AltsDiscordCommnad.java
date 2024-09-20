package kingsbutbad.kingsbutbad.Discord.Commands.StaffCommands;

import kingsbutbad.kingsbutbad.KingsButBad;
import me.coralise.spigot.players.CBPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class AltsDiscordCommnad extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(!event.getMember().hasPermission(Permission.KICK_MEMBERS)){
            event.reply("Sorry, You don't have permission (KICK_MEMBERS)").setEphemeral(true).queue();
            return;
        }

        String username = event.getOption("username").getAsString();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        OfflinePlayer player = Bukkit.getOfflinePlayer(username);
        if(player.hasPlayedBefore()){
            embedBuilder.setColor(Color.RED);
            embedBuilder.setTitle(username+"'s Alts");
            StringBuilder alts = new StringBuilder();
            for(CBPlayer name : KingsButBad.cbp.getDatabase().getCBPlayer(player.getUniqueId()).getSameIps())
                alts.append("`").append(name.getName()).append("`,\n");
            embedBuilder.setDescription(alts.toString());
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        }else{
            event.reply("This player has never played before!").queue();
        }
    }
}
