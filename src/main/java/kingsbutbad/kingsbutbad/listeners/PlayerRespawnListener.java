package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
      KingsButBad.thirst.put(event.getPlayer(), 300F);
      event.getPlayer().setGameMode(GameMode.SPECTATOR);
      event.getPlayer().sendTitle("", CreateText.addColors("<red>Respawning... <gray>(<white>In 5 Seconds<gray>)"), 0, 20*3, 0);
      Bukkit.getScheduler().scheduleSyncDelayedTask(KingsButBad.pl, () -> {
         RoleManager.givePlayerRole(event.getPlayer());
      }, 20*5);
      Bukkit.getScheduler().scheduleSyncDelayedTask(KingsButBad.pl, () -> {
         if(event.getPlayer().getGameMode() == GameMode.SPECTATOR)
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
      }, 20*5+11);
      if (KingsButBad.king != null && KingsButBad.king.equals(event.getPlayer())) {
         KingsButBad.king.setItemOnCursor(new ItemStack(Material.AIR));
         KingsButBad.king = null;

         for (Player p : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.roles.get(p).isPowerful) {
               KingsButBad.roles.put(p, Role.PEASANT);
               RoleManager.givePlayerRole(p);
               event.getPlayer().setNoDamageTicks(20*3);
               event.getPlayer().addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(300, 0));
            }
         }
      } else {
         RoleManager.givePlayerRole(event.getPlayer());
         event.getPlayer().setNoDamageTicks(20*3);
         event.getPlayer().addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(300, 0));
      }
   }
}
