package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityTargetListener implements Listener {
   @EventHandler
   public void onEntityTarget(EntityTargetEvent event) {
      if (!event.getReason().equals(TargetReason.TARGET_ATTACKED_ENTITY)
         && event.getTarget() instanceof Player player
         && !KingsButBad.roles.get(player).equals(Role.CRIMINAl)) {
         event.setCancelled(true);
      }
   }
}
