package kingsbutbad.kingsbutbad.commands.Builders;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.DiscordUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuilderModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player targetPlayer = null;
        if(strings.length == 0 && commandSender instanceof Player p) targetPlayer=p;
        if(targetPlayer == null) targetPlayer = Bukkit.getPlayer(strings[0]);
        if(targetPlayer == null) return true;
        boolean isBuilderMode = Keys.isBuilderMode.get(targetPlayer, false);
        Keys.isBuilderMode.set(targetPlayer, !isBuilderMode);
        targetPlayer.sendMessage(CreateText.addColors("<gray>Your builder mode has been <white>"+isEnabled(!isBuilderMode)+" <gray>by <white>"+commandSender.getName()+"<gray>!"));
        BotManager.getBuilderChannel().sendMessage(DiscordUtils.deformat(targetPlayer.getName()) + " builder mode has been "+isEnabled(!isBuilderMode)+" by "+DiscordUtils.deformat(commandSender.getName())+"!").queue();
        return false;
    }
    private String isEnabled(boolean isEnabled){
        if(isEnabled) return "Enabled";
        return "Disabled";
    }
}
