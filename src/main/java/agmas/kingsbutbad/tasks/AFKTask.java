package agmas.kingsbutbad.tasks;

import agmas.kingsbutbad.KingsButBad;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AFKTask extends BukkitRunnable {
   public void run() {
      List<Player> authorities = new ArrayList<>();
      authorities.add(KingsButBad.king);
      authorities.add(KingsButBad.king2);

      for (Player player : authorities) {
         if (player != null && KingsButBad.datedLocations.containsKey(player)) {
            Location datedLocation = KingsButBad.datedLocations.get(player);
            if (player.getLocation().clone().setDirection(new Vector(0, 0, 0)).equals(datedLocation)) {
               KingsButBad.king.setHealth(0.0);
            }
         }
      }
   }
}
