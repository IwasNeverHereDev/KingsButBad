package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import org.bukkit.scheduler.BukkitRunnable;

public class ReloadVillagersTask extends BukkitRunnable {
    @Override
    public void run() {
        KingdomsLoader.setupVillagers(KingdomsLoader.activeKingdom);
    }
}
