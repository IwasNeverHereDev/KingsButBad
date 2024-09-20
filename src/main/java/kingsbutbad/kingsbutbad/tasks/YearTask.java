package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.KingsButBad;
import org.bukkit.scheduler.BukkitRunnable;

public class YearTask extends BukkitRunnable {
    @Override
    public void run() {
        KingsButBad.year++;
    }
}
