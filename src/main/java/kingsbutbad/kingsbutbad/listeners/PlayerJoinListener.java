package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.DatabaseManager;
import kingsbutbad.kingsbutbad.keys.Key;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.lang.reflect.Member;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      UUID playerId = player.getUniqueId();
      File databaseFile = new File(DatabaseManager.getDataFolder(), "playerdata.db");
      DatabaseManager dbManager = DatabaseManager.loadData(databaseFile.getPath());

      savePlayerDataToDatabase(playerId, dbManager);

      updateTab(player);
      sendDiscordMessage(player);
      TabUtils.reload();

      handleJoinMessage(event, player);
      handlePlayerRoles(player, event);
      checkIsBooster(event);
   }

   private void savePlayerDataToDatabase(UUID playerId, DatabaseManager dbManager) {
      for (Key<?> key : Keys.values()) {
         Object value = key.get(Bukkit.getPlayer(playerId));
         if (value != null) {
            dbManager.setValue(playerId, key.name(), value);
         }
      }
      dbManager.saveData(new File(DatabaseManager.getDataFolder(), "playerdata.db").getPath());
   }

   private void handleJoinMessage(PlayerJoinEvent event, Player player) {
      if (Keys.vanish.get(player, false)) {
         event.setJoinMessage(null);
         sendVanishJoinMessage(player);
      } else {
         String joinMessage = CreateText.addColors("<#D49B63>" + player.getName() + " was shipped into the kingdom.");
         event.setJoinMessage(joinMessage);
      }
   }
   private void checkIsBooster(PlayerJoinEvent event) {
      Player player = event.getPlayer();

      if (!Keys.link.has(player)) return;

      String userId = Keys.link.get(player, "000000000000000000000000");
      User member = BotManager.getBot().getUserById(userId);

      if (member == null) return;
      Guild guild = BotManager.getGuild();

      if (guild != null)
          if(guild.getBoosters().contains(member) || guild.getMember(member).getRoles().contains(BotManager.getBoosterRole()))
              Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " promote booster");
   }


   private void sendVanishJoinMessage(Player player) {
      for (Player p : Bukkit.getOnlinePlayers()) {
         if (p.hasPermission("kbb.staff")) {
            p.sendMessage(CreateText.addColors("<red>(Staff) " + player.getName() + " has joined in vanish!"));
            BotManager.getStafflogChannel().sendMessage("(Staff) " + player.getName() + " has joined in vanish!").queue();
         }
      }
   }

   private void handlePlayerRoles(Player player, PlayerJoinEvent event) {
      if(player.getGameMode() == GameMode.SPECTATOR)
         player.setGameMode(GameMode.ADVENTURE);
      if (!Keys.inPrison.get(player, false)) {
         KingsButBad.roles.put(player, Role.PEASANT);
         RoleManager.givePlayerRole(player);
         BotManager.getInGameChatChannel().sendMessage("**" + DiscordUtils.deformat(player.getName()) + "** was shipped into the kingdom.").queue();
      } else {
         if (Keys.vanish.get(player, false)) {
            String prisonMessage = CreateText.addColors("<gold>" + player.getName() + " was sent back to prison.");
            event.setJoinMessage(prisonMessage);
         }
         BotManager.getInGameChatChannel().sendMessage("**" + DiscordUtils.deformat(player.getName()) + "** was sent back to prison.").queue();
         KingsButBad.prisonTimer.put(player, 2400F);
         KingsButBad.roles.put(player, Role.PRISONER);
         RoleManager.givePlayerRole(player);
      }
   }
   @SuppressWarnings("deprecation")
   public static void updateTab(Player target) {
      int playercount = 0;
      for(Player p : Bukkit.getOnlinePlayers())
         if(!Keys.vanish.get(p, false)) playercount++;
      String header = CreateText.addColors("\n<gold>KingsButBad\n\n<green>Online Players<gray>: <green>" + playercount + "\n");
      if(Keys.vanish.get(target, false))
         header = CreateText.addColors("\n<gold>KingsButBad\n\n<green>Online Players<gray>: <green>" + playercount + " <gray>(<white>"+Bukkit.getOnlinePlayers().size()+"<gray>)\n");
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
                 + "\n\n<dark_gray>Credits: agmass/agmas & _Aquaotter_");
         target.setPlayerListHeader(header);
         if(Keys.displayRoleStats.get(target, false)) {
            target.setPlayerListFooter(footerOther);
         }else {
            target.setPlayerListFooter(footer);
         }
   }
   public static String formatTime(double time) {
      long milliseconds = (long) (time * 50L);

      long totalSeconds = milliseconds / 1000;
      long seconds = totalSeconds % 60;
      long totalMinutes = totalSeconds / 60;
      long minutes = totalMinutes % 60;
      long hours = totalMinutes / 60;

      StringBuilder formattedTime = new StringBuilder();

      if (hours > 0)
         formattedTime.append(hours).append("h ");
      if (minutes > 0 || hours > 0)
         formattedTime.append(minutes).append("m ");
      formattedTime.append(seconds).append("s");

      return formattedTime.toString().trim();
   }
   private void sendDiscordMessage(Player p){
      if(Keys.link.has(p)) return;
      p.sendMessage(CreateText.addColors("<gray>You haven't linked your <blue>Discord <white>account<gray>!"));
      Bukkit.dispatchCommand(p, "discord");
   }
}
