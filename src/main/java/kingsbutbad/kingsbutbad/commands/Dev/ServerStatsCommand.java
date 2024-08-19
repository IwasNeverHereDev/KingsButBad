package kingsbutbad.kingsbutbad.commands.Dev;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.utils.CreateText;
import java.util.concurrent.atomic.AtomicInteger;
import net.dv8tion.jda.api.OnlineStatus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ServerStatsCommand implements CommandExecutor {
   public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
      AtomicInteger onlineMemberCountDiscord = new AtomicInteger();
      BotManager.getGuild().getMembers().forEach(member -> {
         if (member.getOnlineStatus().equals(OnlineStatus.ONLINE)) {
            onlineMemberCountDiscord.getAndIncrement();
         }
      });
      commandSender.sendMessage(
         CreateText.addColors(
            "\n     <gold>KingsButBad     \n<white>â€¢ <green>Online Players<gray>: <green>"
               + Bukkit.getOnlinePlayers().size()
               + "/"
               + Bukkit.getMaxPlayers()
               + "\n<white>â€¢ <green>LifeTime Players<gray>: <green>"
               + Bukkit.getOfflinePlayers().length
               + "\n<white>â€¢ <green> Server made by (Goose maybe), Agmass, _Aquaotter_, and all our amazing Builders/Staff!\n<white>â€¢ <green> You are in "
               + KingdomsLoader.activeKingdom.getDisplayName()
               + " <green>kingdom!\n<white>â€¢ <green> Our Discord Server has a total of "
               + BotManager.getGuild().getMembers().size()
               + " members!"
         )
      );
      return true;
   }
}
