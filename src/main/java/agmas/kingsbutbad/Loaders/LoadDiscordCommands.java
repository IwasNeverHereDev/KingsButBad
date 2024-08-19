package agmas.kingsbutbad.Loaders;

import agmas.kingsbutbad.Discord.BotManager;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;

public class LoadDiscordCommands {
    public static void init(){
        loadGuildCommand("link", "Able to link your Ingame to your Discord Account!").addOption(OptionType.STRING, "code", "Type [/link] in chat to get a Link code! (Type that code in here)", true).queue();
        loadGuildCommand("players", "Show online players in style!").queue();
        loadGuildCommand("whatkingdom", "Show what kingdom/map the server is on!").queue();
        loadGuildCommand("whatmap", "Show what kingdom/map the server is on!").queue();
        loadGuildCommand("alts", "Show alts of provided username!").addOption(OptionType.STRING, "username", "Type a Minecraft in game username!").queue();
    }
    private static CommandCreateAction loadGuildCommand(String cmd, String disc){
       return BotManager.getGuild().upsertCommand(cmd, disc);
    }
}
