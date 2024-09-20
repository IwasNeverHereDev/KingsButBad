package kingsbutbad.kingsbutbad.Loaders;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;

public class LoadDiscordCommands {
    public static void init(){
        loadGuildCommand("link", "Able to link your Ingame to your Discord Account!").addOption(OptionType.STRING, "code", "Type [/link] in chat to get a Link code! (Type that code in here)", true).queue();
        loadGuildCommand("players", "Show online players in style!").queue();
        loadGuildCommand("whatkingdom", "Show what kingdom/map the server is on!").queue();
        loadGuildCommand("whatmap", "Show what kingdom/map the server is on!").queue();
        loadGuildCommand("history", "Displays a palyerName history")
                .addOption(OptionType.STRING, "username", "Type a Minecraft In Game username!", true)
                .addOption(OptionType.INTEGER, "page", "Page number of player's History", true)
                .queue();
        loadGuildCommand("alts", "Show alts of provided username!")
                .addOption(OptionType.STRING, "username", "Type a Minecraft in game username!", true)
                .queue();
        loadGuildCommand("mute", "Mutes a player from in discord!")
                .addOption(OptionType.STRING, "username", "Type a Minecraft ingame username!", true)
                .addOption(OptionType.STRING, "time", "Display a time EX: 1d, perm, 25s, 365d", true)
                .addOption(OptionType.STRING, "reason", "Display the reason of a mute", true)
                .queue();
        loadGuildCommand("unmute", "UnMutes a player from in discord!")
                .addOption(OptionType.STRING, "username", "Type a Minecraft in game username!", true)
                .queue();
        loadGuildCommand("kick", "Kicks a player from in discord!")
                .addOption(OptionType.STRING, "username", "Type a Minecraft in game username!", true)
                .addOption(OptionType.STRING, "reason", "Reason for kicking player", true)
                .queue();
        loadGuildCommand("warn", "Warns a player from in discord!")
                .addOption(OptionType.STRING, "username", "Type a Minecraft in game username!", true)
                .addOption(OptionType.STRING, "reason", "Reason for warning player", true)
                .queue();
        loadGuildCommand("unwarn", "UnWarns a player from in discord!")
                .addOption(OptionType.STRING, "username", "Type a Minecraft in game username!", true)
                .queue();
        loadGuildCommand("ban", "Bans a player from in discord!")
                .addOption(OptionType.STRING, "username", "Type a Minecraft ingame username!", true)
                .addOption(OptionType.STRING, "time", "Display a time EX: 1d, perm, 25s, 365d", true)
                .addOption(OptionType.STRING, "reason", "Display the reason of a ban", true)
                .queue();
        loadGuildCommand("unban", "UnBans a player from in discord!")
                .addOption(OptionType.STRING, "username", "Type a Minecraft in game username!", true)
                .queue();
    }
    private static CommandCreateAction loadGuildCommand(String cmd, String disc){
       return BotManager.getGuild().upsertCommand(cmd, disc);
    }
}
