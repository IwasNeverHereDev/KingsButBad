package agmas.kingsbutbad.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

public class EntityMountListener implements Listener {
   @EventHandler
   public void onEntityMountEvent(EntityMountEvent event) {
      if (event.getMount().getCustomName().endsWith("'s horse")) {
         String playername = event.getMount().getCustomName().split("'")[0];
         if (event.getEntity() instanceof Player p && !p.getName().equals(playername)) {
            event.setCancelled(true);
         }
      }
   }
}
