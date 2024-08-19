package agmas.kingsbutbad.tasks;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.Role;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Player;
import org.bukkit.entity.TropicalFish;
import org.bukkit.scheduler.BukkitRunnable;

public class FailsafeTask extends BukkitRunnable {
   public void run() {
      for (Player p : Bukkit.getOnlinePlayers()) {
         KingsButBad.roles.putIfAbsent(p, Role.PEASANT);
         if (p.getGameMode().equals(GameMode.SURVIVAL)) {
            p.setGameMode(GameMode.ADVENTURE);
         }
         handleWaterDamage(p);
      }
   }
   public void handleWaterDamage(Player p) {
      if (p.getLocation().getBlock().getType().equals(Material.WATER)) {
         p.setNoDamageTicks(0);
         Entity piranha = Bukkit.getWorld("world").createEntity(p.getLocation().clone().add(0, -5, 0), TropicalFish.class);
         piranha.setInvulnerable(true);
         piranha.setVisibleByDefault(false);
         piranha.setSilent(true);
         piranha.setGravity(false);
         piranha.setCustomName("Piranha");
         p.damage(1.0, piranha);
         piranha.remove();
      }
   }
}
