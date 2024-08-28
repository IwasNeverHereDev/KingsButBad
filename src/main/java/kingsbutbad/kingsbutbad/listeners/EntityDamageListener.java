package kingsbutbad.kingsbutbad.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
   @EventHandler
   public void onEntityDamage(EntityDamageEvent event) {
      if(event.getEntity() instanceof Player p)
         p.closeInventory();
      if (event.getEntity().getType().equals(EntityType.ITEM_FRAME)) {
         event.setCancelled(true);
      }
   }
}
