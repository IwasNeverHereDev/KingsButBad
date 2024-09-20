package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Pacts;
import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

public class EntityDamageByEntityListener implements Listener {
   @EventHandler
   @SuppressWarnings("deprecation")
   public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
      if(Keys.vanish.get(event.getDamager(), false)) {
         event.getDamager().sendMessage(CreateText.addColors("<gray>You can't damage in Vanish!"));
         event.setCancelled(true);
         return;
      }
      if(event.getDamager() instanceof Player user && event.getEntity().getType().equals(EntityType.ZOMBIE) && KingsButBad.roles.getOrDefault(user, Role.PEASANT) == Role.PEASANT){
         user.sendTitle(CreateText.addColors("<red>!!! You're now a criminal !!!"), CreateText.addColors("<gray>You hit a Royal Guard."));
         KingsButBad.roles.put(user, Role.CRIMINAL);
         user.playSound(user, Sound.ENTITY_SILVERFISH_DEATH, 1.0F, 0.5F);
      }
      if (event.getEntity() instanceof Player target && event.getDamager() instanceof Player attacker) {
         if(attacker.isSprinting())
            attacker.setSprinting(false);
         if(attacker.isInsideVehicle())
            event.setCancelled(true);
         if (attacker.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
            attacker.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            attacker.setNoDamageTicks(0);
         }

         attacker.setCooldown(Material.RED_STAINED_GLASS, 120);
         target.setCooldown(Material.RED_STAINED_GLASS, 120);

         Role attackerRole = KingsButBad.roles.get(attacker);
         Role targetRole = KingsButBad.roles.get(target);
         if (attackerRole.equals(Role.SERVANT) && targetRole.isPowerful) {
            attacker.sendMessage(CreateText.addColors("<red>You can't do that."));
            event.setCancelled(true);
         }

         if (attackerRole.equals(Role.PEASANT) && targetRole.isPowerful) {
            attacker.sendTitle(CreateText.addColors("<red>!!! You're now a criminal !!!"), CreateText.addColors("<gray>You hit someone of authority."));
            KingsButBad.roles.put(attacker, Role.CRIMINAL);
            attacker.playSound(attacker, Sound.ENTITY_SILVERFISH_DEATH, 1.0F, 0.5F);
         }
         if(attackerRole.equals(Role.PEASANT) && Keys.activePact.get(attacker, "") == Pacts.PEASANT.name()){
            attacker.sendTitle(CreateText.addColors("<red>!!! You're now a criminal !!!"), CreateText.addColors("<gray>You hit someone. (PACT)"));
            KingsButBad.roles.put(attacker, Role.CRIMINAL);
            attacker.playSound(attacker, Sound.ENTITY_SILVERFISH_DEATH, 1.0F, 0.5F);
         }
         if(targetRole.equals(Role.PEASANT) && attackerRole.isPowerful){
            ItemStack card = new ItemStack(Material.PAPER);
            ItemMeta cardMeta = card.getItemMeta();
            cardMeta.setDisplayName(CreateText.addColors("<blue>Get-Out-Of-Jail-Free Card"));
            card.setItemMeta(cardMeta);
            if(target.getInventory().contains(card)) return;
            target.getInventory().addItem(card);
         }
      }
   }
}
