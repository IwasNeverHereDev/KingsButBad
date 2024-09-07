package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.DatabaseManager;
import kingsbutbad.kingsbutbad.keys.Key;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.DiscordUtils;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

   @EventHandler
   @SuppressWarnings("deprecation")
   public void onPlayerJoin(PlayerJoinEvent event) {
      DatabaseManager dbManager = DatabaseManager.loadData(new File(DatabaseManager.getDataFolder(), "playerdata.db").getPath());
      UUID playerId = event.getPlayer().getUniqueId();
      for (Key<?> key : Keys.values()) {
         Object value = key.get(event.getPlayer());
         if (value != null) {
            dbManager.setValue(playerId, key.name(), value);
         }
      }
      dbManager.saveData(new File(DatabaseManager.getDataFolder(), "playerdata.db").getPath());
      updateTab(event.getPlayer());
      sendDiscordMessage(event.getPlayer());
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
   @SuppressWarnings("deprecation")
   public static void updateTab(Player target) {
      int playercount = 0;
      for(Player p : Bukkit.getOnlinePlayers())
         if(!Keys.vanish.get(p, false)) playercount++;
      String header = CreateText.addColors("\n<gold>KingsButBad\n\n<green>Online Players<gray>: <green>" + playercount + "\n");
      String footer = CreateText.addColors("\n<white>IP<gray>: <gold>KingsButBad.Minehut.GG\n\n<gray><--->\n\n<dark_gray>Continued by _Aquaotter_");
         String footerOther = CreateText.addColors("\n\n<white>IP<gray>: <gold>KingsButBad.Minehut.GG" +
                 "\n\n<white>Total Playtime<gray>: <white>" + formatTime(target.getStatistic(Statistic.PLAY_ONE_MINUTE)) +
                 "\n<gradient:#FFFF52:#FFBA52><b>King Playtime</b><gray>: <gradient:#FFFF52:#FFBA52><b>" + formatTime(Keys.KINGTicks.get(target, 0.0)) +
                 "</b>\n<gradient:#FFFF52:#FFBA52><b>Prince Playtime</b><gray>: <gradient:#FFFF52:#FFBA52><b>" + formatTime(Keys.PRINCETicks.get(target, 0.0)) +
                 "</b>\n<dark_gray>BodyGuard Playtime<gray>: <dark_gray>"+ formatTime(Keys.BODYGUARDTicks.get(target, 0.0))+
                 "\n<gray>Knight Playtime: " + formatTime(Keys.KNIGHTTicks.get(target, 0.0)) +
                 "\n<blue>Prison Guard Playtime<gray>: <blue>" + formatTime(Keys.PRISON_GUARDTicks.get(target, 0.0)) +
                 "\n<#867143>Servant Playtime<gray>: <#867143>" + formatTime(Keys.SERVANTTicks.get(target, 0.0)) +
                 "\n<gold>Outlaw Playtime<gray>: <gold>" + formatTime(Keys.OUTLAWTicks.get(target, 0.0)) +
                 "\n<red>Criminal Playtime<gray>: <red>" + formatTime(Keys.CRIMINALTicks.get(target, 0.0)) +
                 "\n<gold>Prisoner Playtime<gray>: <gold>" + formatTime(Keys.PRISONERTicks.get(target, 0.0)) +
                 "\n<#59442B>Peasant Playtime<gray>: <#59442B>" + formatTime(Keys.PEASANTTicks.get(target, 0.0))
                 + "\n\n<dark_gray>Continued by _Aquaotter_");
         target.setPlayerListHeader(header);
         if(Keys.displayRoleStats.get(target, false)) {
            target.setPlayerListFooter(footerOther);
         }else {
            target.setPlayerListFooter(footer);
         }
   }
   public static String formatTime(double time) {
      // Convert ticks to milliseconds
      long milliseconds = (long) (time * 50L); // 1 tick = 50 ms

      // Breakdown into components
      long totalSeconds = milliseconds / 1000;
      long seconds = totalSeconds % 60;
      long totalMinutes = totalSeconds / 60;
      long minutes = totalMinutes % 60;
      long hours = totalMinutes / 60;

      // Build the formatted time string dynamically
      StringBuilder formattedTime = new StringBuilder();

      if (hours > 0) {
         formattedTime.append(hours).append("h ");
      }
      if (minutes > 0 || hours > 0) { // Always show minutes if hours are present or minutes are non-zero
         formattedTime.append(minutes).append("m ");
      }
      formattedTime.append(seconds).append("s"); // Always show seconds

      return formattedTime.toString().trim();
   }
   private void sendDiscordMessage(Player p){
      if(Keys.link.has(p)) return;
      p.sendMessage(CreateText.addColors("<gray>You haven't linked your <blue>Discord <white>account<gray>!"));
      Bukkit.dispatchCommand(p, "discord");
   }
}
