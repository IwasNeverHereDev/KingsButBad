package kingsbutbad.kingsbutbad.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

public class EntityMountListener implements Listener {
   @EventHandler
   @SuppressWarnings("deprecation")
   public void onEntityMountEvent(EntityMountEvent event) {
      if (event.getMount().getCustomName() != null && event.getMount().getCustomName().endsWith("'s horse")) {
         String playerName = event.getMount().getCustomName().split("'")[0];
         if (event.getEntity() instanceof Player p && !p.getName().equals(playerName)) {
            event.setCancelled(true);
         }
      }
   }
}
