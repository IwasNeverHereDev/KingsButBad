package kingsbutbad.kingsbutbad.Discord.Commands;

import kingsbutbad.kingsbutbad.commands.Misc.LinkCommand;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LinkDiscordCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
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
}
