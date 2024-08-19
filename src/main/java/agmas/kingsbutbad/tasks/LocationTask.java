package agmas.kingsbutbad.tasks;

import agmas.kingsbutbad.KingsButBad;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class LocationTask extends BukkitRunnable {
   public void run() {
      for (Player player : Bukkit.getOnlinePlayers())
         KingsButBad.datedLocations.put(player, player.getLocation().clone().setDirection(new Vector(0, 0, 0)));
   }
}
