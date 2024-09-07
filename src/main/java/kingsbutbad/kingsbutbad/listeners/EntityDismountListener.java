package kingsbutbad.kingsbutbad.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EntityDismountListener implements Listener {
   @EventHandler
   public void onEntityDismount(EntityDismountEvent event) {
      if (event.getDismounted().getType().equals(EntityType.HORSE)) {
         event.getEntity().teleport(event.getDismounted());
         event.getDismounted().remove();
      }

      if (event.getDismounted().getType().equals(EntityType.PLAYER)) {
         if(!event.getDismounted().isSneaking()) {
            event.setCancelled(true);
            return;
         }
         event.getEntity().removePassenger(event.getDismounted());
         event.getEntity().teleport(event.getDismounted());
      }
   }
}
