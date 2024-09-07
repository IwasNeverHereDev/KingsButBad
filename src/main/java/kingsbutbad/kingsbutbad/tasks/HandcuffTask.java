package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.KingsButBad;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class HandcuffTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(p.isInsideVehicle() && KingsButBad.thirst.getOrDefault(p, 0) == 0)
                p.getVehicle().removePassenger(p);
            List<Entity> passengers = p.getPassengers();
            if (passengers.size() > 2) {
                Entity toRemove = passengers.get(passengers.size() - 1);
                p.removePassenger(toRemove);
            }
        }
    }
}
