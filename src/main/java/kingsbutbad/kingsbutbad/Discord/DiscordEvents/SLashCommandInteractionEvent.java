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
            // Ensure the "code" option is present and not null
            if (event.getOption("code") != null) {
                String code = event.getOption("code").getAsString();

                for (Player p : Bukkit.getOnlinePlayers()) {
                    // Check if the player has a code and if it matches
                    if (LinkCommand.codes.containsKey(p) && code.equals(LinkCommand.codes.get(p))) {
                        // Ensure the interaction and member objects are not null
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

                // If code doesn't match or no player found
                event.reply("Invalid code or no player found with that code.").setEphemeral(true).queue();
            } else {
                event.reply("Error: You must provide a code.").setEphemeral(true).queue();
            }
        }
        if(commandName.equals("players")){
            int playerCount = 0;
            int vanishCount = 0;
            String players = "";
            String vanishedPlayers = "";
            for(Player p : Bukkit.getOnlinePlayers()) {
                String playerlistname = ChatColor.stripColor(p.getPlayerListName());
                if (Keys.vanish.get(p, false)) {
                    vanishedPlayers += playerlistname+"\n";
                    vanishCount++;
                } else {
                    playerCount++;
                    players += playerlistname+"\n";
                }
                vanishedPlayers += "\n(VISIBLE TO STAFF ONLY)\n";
            }
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.addField("**Online Players**: `"+playerCount+"`", players, false);
            if(event.getMember().hasPermission(Permission.MESSAGE_MANAGE))
                embedBuilder.addField("**Vanish Players **: `"+vanishCount+"`", vanishedPlayers, false);
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
                String alts = "";
                for(CBPlayer name : KingsButBad.cbp.getDatabase().getCBPlayer(player.getUniqueId()).getSameIps())
                    alts += "`"+name.getName()+"`,\n";
                embedBuilder.setDescription(alts);
                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            }else{
                event.reply("This player has never played before!").queue();
            }
        }
    }
}
