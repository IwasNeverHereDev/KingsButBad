package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.DiscordUtils;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      updateTab();
      if(Keys.vanish.get(event.getPlayer(), false)) {
         event.setJoinMessage(null);
         for(Player p : Bukkit.getOnlinePlayers()){
            if(!p.hasPermission("kbb.staff")) continue;
            p.sendMessage(CreateText.addColors("<red>(Staff) "+ event.getPlayer().getName()+" has joined in vanish!"));
            BotManager.getStafflogChannel().sendMessage("(Staff) "+event.getPlayer().getName()+" has joined in vanish!").queue();
         }
         return;
      }
      event.setJoinMessage(
         LegacyComponentSerializer.legacySection()
            .serialize(MiniMessage.miniMessage().deserialize("<#D49B63>" + event.getPlayer().getName() + " was shipped into the kingdom."))
      );
      if (!Keys.inPrison.get(event.getPlayer(), false)) {
         KingsButBad.roles.put(event.getPlayer(), Role.PEASANT);
         RoleManager.givePlayerRole(event.getPlayer());
         BotManager.getInGameChatChannel().sendMessage("**" + DiscordUtils.deformat(event.getPlayer().getName()) + "**" + " was shipped into the kingdom.").queue();
      } else {
         event.setJoinMessage(
            LegacyComponentSerializer.legacySection()
               .serialize(MiniMessage.miniMessage().deserialize("<gold>" + event.getPlayer().getName() + " was sent back to prison."))
         );
         BotManager.getInGameChatChannel().sendMessage("**" + DiscordUtils.deformat(event.getPlayer().getName()) + "**" + " was sent back to prison.").queue();
         KingsButBad.prisonTimer.put(event.getPlayer(), 2400);
         KingsButBad.roles.put(event.getPlayer(), Role.PRISONER);
         RoleManager.givePlayerRole(event.getPlayer());
      }
   }

   public static void updateTab() {
      int playercount = 0;
      for(Player p : Bukkit.getOnlinePlayers())
         if(!Keys.vanish.get(p, false)) playercount++;
      String header = CreateText.addColors("\n\n<gold>KingsButBad\n\n<green>Online Players<gray>: <green>" + playercount + "\n");
      String footer = CreateText.addColors("\n\n<white>IP<gray>: <gold>KingsButBad.Minehut.GG\n\n<dark_gray>Continued by _Aquaotter_");
      for(Player player : Bukkit.getOnlinePlayers()){
         player.setPlayerListHeader(header);
         player.setPlayerListFooter(footer);
      }
   }
}
