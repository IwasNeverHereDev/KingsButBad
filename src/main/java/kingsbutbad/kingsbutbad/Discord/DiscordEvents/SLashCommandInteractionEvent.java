package kingsbutbad.kingsbutbad.Discord.DiscordEvents;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.commands.Misc.LinkCommand;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import me.coralise.spigot.players.CBPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SLashCommandInteractionEvent extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();

        if (commandName.equals("link")) {
            if (event.getOption("code") != null) {
                String code = event.getOption("code").getAsString();

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (LinkCommand.codes.containsKey(p) && code.equals(LinkCommand.codes.get(p))) {
                        if (event.getInteraction() != null && event.getInteraction().getMember() != null) {
                            p.sendMessage(CreateText.addColors("<gray>You have been now linked! (<white>" + event.getInteraction().getMember().getEffectiveName() + "<gray>)"));
                            Keys.link.set(p, event.getMember().getId());
                            LinkCommand.codes.remove(p);
                            event.reply("Successfully linked! ("+p.getName()+")").setEphemeral(true).queue();
                        } else {
                            event.reply("Error: Could not retrieve your Discord member details.").setEphemeral(true).queue();
                        }
                        return;
                    }
                }
                event.reply("Invalid code or no player found with that code.").setEphemeral(true).queue();
            } else {
                event.reply("Error: You must provide a code.").setEphemeral(true).queue();
            }
        }
        if(commandName.equals("players")){
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
        if(commandName.equals("whatkingdom") || commandName.equals("whatmap")){
            event.reply("The Server is on the `"+ ChatColor.stripColor(CreateText.addColors(KingdomsLoader.activeKingdom.getDisplayName())) +"` kingdom!").setEphemeral(true);
        }
        if(commandName.equals("alts")){
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
}
