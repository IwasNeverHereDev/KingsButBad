package kingsbutbad.kingsbutbad.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HandcuffTask extends BukkitRunnable {
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()){
            int count = 0;
            for(Entity entity : p.getPassengers()) {
                count++;
                if (count >= 2) p.getPassengers().remove(entity);
            }
        }
    }
}
