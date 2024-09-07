package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PingSpoofingTask extends BukkitRunnable {
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers())
            if(p.getPing() >= 150)
                p.closeInventory();

    }
}
