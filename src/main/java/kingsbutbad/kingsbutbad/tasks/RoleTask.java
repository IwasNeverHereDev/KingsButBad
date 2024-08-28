package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
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
         if (player == null) continue; // Skip if player is null
         addTicksToRolesTimer(player);

         // Assign default role if not already assigned
         KingsButBad.roles.putIfAbsent(player, Role.PEASANT);

         // Assign default prince gender if not already assigned
         KingsButBad.princeGender.putIfAbsent(player, "Prince");

         // Check if the player is in a restricted area and has a powerful role
         Location restrictedAreaStart = KingdomsLoader.activeKingdom.getBm1();
         Location restrictedAreaEnd = KingdomsLoader.activeKingdom.getBm2();
         if (KingsButBad.roles.get(player).isPowerful &&
                 KingsButBad.isInside(player, restrictedAreaStart, restrictedAreaEnd)) {

            // Teleport player to a safe location
            Location safeLocation = KingdomsLoader.activeKingdom.getBmSafe();
            if (safeLocation.getWorld() != null) {
               player.teleport(safeLocation);
            }

            // Remove all passengers (if any)
            for (Entity passenger : player.getPassengers()) {
               if (passenger != null) {
                  passenger.leaveVehicle();
               }
            }

            // Notify the player
            player.sendMessage(CreateText.addColors("<red><b>>> </b>You can't be in here!"));
         }

         // Update the player's display name and list name based on their role and prefix
         updatePlayerNames(player);

         // Check if the second king is online, if not, reset the king
         if (KingsButBad.king2 != null && !KingsButBad.king2.isOnline()) {
            KingsButBad.king2 = null;
         }

         // If the player is no longer king, revert their role to Peasant
         if (KingsButBad.roles.get(player).equals(Role.KING) && !RoleManager.isKingAtAll(player)) {
            KingsButBad.roles.put(player, Role.PEASANT);
            RoleManager.givePlayerRole(player);
         }
      }
   }
   private void addTicksToRolesTimer(Player p){
      switch (KingsButBad.roles.getOrDefault(p, Role.PEASANT)){
         case PRISONER -> Keys.PRISONERTicks.addDouble(p, 1.0);
         case CRIMINAl -> Keys.CRIMINALTicks.addDouble(p, 1.0);
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

   /**
    * Updates the player's display name and player list name based on their role and prefix.
    *
    * @param player The player whose name is being updated.
    */
   private void updatePlayerNames(Player player) {
      if (player == null) return; // Skip if player is null

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
         rolePart = "[<gradient:#FFFF52:#FFBA52>"
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

      // Set player list name and display name
      player.setPlayerListName(playerListName+" "+getPing(player));
      player.setDisplayName(playerListName);
   }
   public static String getPing(Player p){
      int ping = p.getPing();
      String pingText = "<green>"+ping;
      if(ping >= 1000) return CreateText.addColors("<red>"+ping);
      if(ping >= 500) return CreateText.addColors("<gold>"+ping);
      if(ping >= 250) return CreateText.addColors("<yellow>"+ping);
      return CreateText.addColors("<gray>("+pingText+"ms<gray>)");
   }
}
