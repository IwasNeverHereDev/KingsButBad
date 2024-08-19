package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.keys.Keys;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

public class EntityDamageByEntityListener implements Listener {
   @EventHandler
   public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
      if(Keys.vanish.get(event.getDamager(), false)) {
         event.getDamager().sendMessage(CreateText.addColors("<gray>You can't damage in Vanish!"));
         event.setCancelled(true);
         return;
      }
      if (event.getEntity() instanceof Player target && event.getDamager() instanceof Player attacker) {
         if (attacker.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
            attacker.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            attacker.setNoDamageTicks(0);
         }

         attacker.setCooldown(Material.RED_STAINED_GLASS, 120);
         target.setCooldown(Material.RED_STAINED_GLASS, 120);
         if (attacker.getInventory().getItemInMainHand().getType().equals(Material.IRON_SHOVEL) && !KingsButBad.king.equals(target)) {
            event.setCancelled(true);
            attacker.addPassenger(target);
         }

         Role attackerRole = KingsButBad.roles.get(attacker);
         Role targetRole = KingsButBad.roles.get(target);
         if (attackerRole.equals(Role.SERVANT) && targetRole.isPowerful) {
            attacker.sendMessage(ChatColor.RED + "You can't do that.");
            event.setCancelled(true);
         }

         if (attackerRole.equals(Role.PEASANT) && targetRole.isPowerful) {
            attacker.sendTitle(ChatColor.RED + "!!! You're now a criminal !!!", ChatColor.GRAY + "You hit someone of authority.");
            KingsButBad.roles.put(attacker, Role.CRIMINAl);
            attacker.playSound(attacker, Sound.ENTITY_SILVERFISH_DEATH, 1.0F, 0.5F);
         }

         return;
      }
   }
}
