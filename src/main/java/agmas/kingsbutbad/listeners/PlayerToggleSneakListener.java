package agmas.kingsbutbad.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleSneakListener implements Listener {
   @EventHandler
   public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
      if (event.isSneaking()) {
         for (Entity passenger : event.getPlayer().getPassengers()) {
            passenger.leaveVehicle();
         }
      }
   }
}
