package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffectType;

public class EntityTargetListener implements Listener {
   @EventHandler
   public void onEntityTarget(EntityTargetEvent event) {
      if (event.getTarget() instanceof Player player) {
         EntityType type = event.getEntity().getType();
         Role role = KingsButBad.roles.getOrDefault(player, Role.PEASANT);
         if(type.equals(EntityType.ZOMBIE)) {
            if (role == Role.CRIMINAL || role == Role.OUTLAW) return;
            event.setCancelled(true);
         }else if(type.equals(EntityType.WITHER_SKELETON))
            if(player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) || player.getNoDamageTicks() >= 5) event.setCancelled(true);
      }
   }
}
