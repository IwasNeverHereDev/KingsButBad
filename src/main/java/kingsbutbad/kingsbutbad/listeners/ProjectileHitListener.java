package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {
   @EventHandler
   public void onProjectileHit(ProjectileHitEvent event) {
      if (event.getHitEntity() != null) {
         if (event.getEntity().getShooter() instanceof Player shooter && event.getHitEntity() instanceof Player target) {
            Role shooterRole = KingsButBad.roles.get(shooter);
            Role targetRole = KingsButBad.roles.get(target);
            if (shooterRole.equals(Role.PEASANT) && targetRole.isPowerful) {
               shooter.sendTitle(ChatColor.RED + "!!! You're now a criminal !!!", ChatColor.GRAY + "You hit someone of authority.");
               KingsButBad.roles.put(shooter, Role.CRIMINAl);
               shooter.playSound(shooter, Sound.ENTITY_SILVERFISH_DEATH, 1.0F, 0.5F);
            }
         }
      }
   }
}
