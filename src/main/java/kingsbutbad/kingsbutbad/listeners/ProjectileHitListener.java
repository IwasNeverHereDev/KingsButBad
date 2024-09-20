package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.tasks.HandcuffTask;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Items.GetOutOfJailFreeCard;
import kingsbutbad.kingsbutbad.utils.Items.RoleItems;
import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {
   @EventHandler
   @SuppressWarnings("deprecation")
   public void onProjectileHit(ProjectileHitEvent event) {
      if (event.getHitEntity() != null) {
         if (event.getEntity().getShooter() instanceof Player shooter && event.getHitEntity() instanceof Player target) {
            Role shooterRole = KingsButBad.roles.get(shooter);
            Role targetRole = KingsButBad.roles.get(target);
            if(shooter.isInsideVehicle())
               event.setCancelled(true);
            if (shooterRole.equals(Role.SERVANT) && targetRole.isPowerful) {
               shooter.sendMessage(CreateText.addColors("<red>You can't do that."));
               event.setCancelled(true);
            }
            if(shooterRole.isPowerful && targetRole.equals(Role.PEASANT) && !target.getInventory().contains(GetOutOfJailFreeCard.get()))
               target.getInventory().addItem(GetOutOfJailFreeCard.get());
            if (shooterRole.equals(Role.PEASANT) && targetRole.isPowerful) {
               shooter.sendTitle(CreateText.addColors("<red>!!! You're now a criminal !!!"), CreateText.addColors("<gray>You hit someone of authority."));
               KingsButBad.roles.put(shooter, Role.CRIMINAL);
               shooter.playSound(shooter, Sound.ENTITY_SILVERFISH_DEATH, 1.0F, 0.5F);
            }
         }
      }
   }
}
