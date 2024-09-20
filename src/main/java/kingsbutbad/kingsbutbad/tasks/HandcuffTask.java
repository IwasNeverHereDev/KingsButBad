package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class HandcuffTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(p.isInsideVehicle() && KingsButBad.thirst.getOrDefault(p, 0F) == 0)
                p.getVehicle().removePassenger(p);
            List<Entity> passengers = p.getPassengers();
            if(!passengers.isEmpty()) p.setCooldown(new ItemStack(Material.IRON_SHOVEL).getType(), 20*8);
            if (passengers.size() > 2) {
                Entity toRemove = passengers.get(passengers.size() - 1);
                p.removePassenger(toRemove);
            }
        }
    }
}
