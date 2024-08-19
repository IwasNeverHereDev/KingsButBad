package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class PlayerRespawnListener implements Listener {
   @EventHandler
   public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
      KingsButBad.thirst.put(event.getPlayer(), 300);
      if (KingsButBad.king != null && KingsButBad.king.equals(event.getPlayer())) {
         KingsButBad.king.setItemOnCursor(new ItemStack(Material.AIR));
         KingsButBad.king = null;

         for (Player p : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.roles.get(p) != Role.PEASANT) {
               KingsButBad.roles.put(p, Role.PEASANT);
               RoleManager.givePlayerRole(p);
               event.getPlayer().setNoDamageTicks(300);
               event.getPlayer().addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(300, 0));
            }
         }
      } else {
         RoleManager.givePlayerRole(event.getPlayer());
         event.getPlayer().setNoDamageTicks(300);
         event.getPlayer().addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(300, 0));
      }
   }
}
