package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.Advancements.AdvancementManager;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("deprecation")
public class RoleTask extends BukkitRunnable {

   @Override
   public void run() {
      for (Player player : Bukkit.getOnlinePlayers()) {
         if (player == null) continue;
         addTicksToRolesTimer(player);
         checkIfPeasantIsPure(player);

         KingsButBad.roles.putIfAbsent(player, Role.PEASANT);

         KingsButBad.princeGender.putIfAbsent(player, "Prince");

         Location restrictedAreaStart = KingdomsLoader.activeKingdom.getBm1();
         Location restrictedAreaEnd = KingdomsLoader.activeKingdom.getBm2();
         if (KingsButBad.roles.get(player).isPowerful &&
                 KingsButBad.isInside(player, restrictedAreaStart, restrictedAreaEnd)) {

            for (Entity passenger : player.getPassengers())
               player.removePassenger(passenger);
            if(player.isInsideVehicle())
               player.getVehicle().removePassenger(player);
            Location safeLocation = Cell.findClosestLocation(KingdomsLoader.activeKingdom.getBmSafe(), player.getLocation());
            if (safeLocation.getWorld() != null) {
               player.teleport(safeLocation);
            }

            player.sendMessage(CreateText.addColors("<red><b>>> </b>You can't be in here!"));
         }
         updatePlayerNames(player);
         if (KingsButBad.king2 != null && !KingsButBad.king2.isOnline()) {
            KingsButBad.king2 = null;
         }
         if (KingsButBad.roles.get(player).equals(Role.KING) && !RoleManager.isKingAtAll(player)) {
            KingsButBad.roles.put(player, Role.PEASANT);
            RoleManager.givePlayerRole(player);
         }
      }
   }
   private void checkIfPeasantIsPure(Player p){
      if(Keys.PEASANTTicks.get(p, 0.0) >= 20*60*60*2)
         AdvancementManager.giveAdvancement(p, "purepeasant");
   }
   private void addTicksToRolesTimer(Player p){
      switch (KingsButBad.roles.getOrDefault(p, Role.PEASANT)){
         case PRISONER -> Keys.PRISONERTicks.addDouble(p, 1.0);
         case CRIMINAL -> Keys.CRIMINALTicks.addDouble(p, 1.0);
         case KING -> Keys.KINGTicks.addDouble(p, 1.0);
         case PRINCE -> Keys.PRINCETicks.addDouble(p, 1.0);
         case PEASANT -> Keys.PEASANTTicks.addDouble(p, 1.0);
         case PRISON_GUARD -> Keys.PRISON_GUARDTicks.addDouble(p, 1.0);
         case BODYGUARD -> Keys.BODYGUARDTicks.addDouble(p, 1.0);
         case SERVANT -> Keys.SERVANTTicks.addDouble(p, 1.0);
         case KNIGHT -> Keys.KNIGHTTicks.addDouble(p, 1.0);
         case OUTLAW -> Keys.OUTLAWTicks.addDouble(p, 1.0);
         default -> p.sendActionBar("Your not going up Contact _Aquaotter_ About this!");
      }
   }
   private void updatePlayerNames(Player player) {
      if (player == null) return;

      String prefix = null;
      if (KingsButBad.api.getPlayerAdapter(Player.class).getUser(player).getCachedData().getMetaData().getPrefix() != null) {
         prefix = CreateText.convertAmpersandToMiniMessage(
                 KingsButBad.api.getPlayerAdapter(Player.class).getUser(player).getCachedData().getMetaData().getPrefix());
      }

      String playerListName;
      String vanishMsg = "";
      if (Keys.vanish.get(player, false)) {
         vanishMsg = "<red>(VANISH) ";
      }

      String prefixPart = "";
      if (prefix != null && !prefix.isEmpty()) {
         prefixPart = "<dark_gray>[" + prefix + "<dark_gray>] ";
      }

      String rolePart;
      if (KingsButBad.king2 == player) {
         rolePart = "<dark_gray>[<gradient:#FFFF52:#FFBA52><b>"
                 + KingsButBad.kingGender2.toUpperCase()
                 + "<dark_gray></b><dark_gray>] ";
      } else if (KingsButBad.roles.get(player).equals(Role.PRINCE)) {
         rolePart = "<dark_gray>[<gradient:#FFFF52:#FFBA52>"
                 + KingsButBad.princeGender.get(player).toUpperCase()
                 + "<dark_gray>] ";
      } else {
         rolePart = "<dark_gray>[<gradient:#FFFF52:#FFBA52>"
                 + KingsButBad.roles.get(player).uncompressedColors
                 + "<dark_gray><dark_gray>] ";
      }

      playerListName = CreateText.addColors(
              vanishMsg + prefixPart + rolePart
      ) + KingsButBad.roles.get(player).chatColor
              + player.getName();

      player.setPlayerListName(playerListName+" "+getPing(player));
      player.setDisplayName(playerListName);
   }
   public static String getPing(Player p){
      int ping = p.getPing();
      if(ping >= 1000) return CreateText.addColors("<gray>(<red>"+ping+"ms<gray>)");
      if(ping >= 500) return CreateText.addColors("<gray>(<gold>"+ping+"ms<gray>)");
      if(ping >= 250) return CreateText.addColors("<gray>(<yellow>"+ping+"ms<gray>)");
      return CreateText.addColors("<gray>(<green>"+ ping +"ms<gray>)");
   }
}
