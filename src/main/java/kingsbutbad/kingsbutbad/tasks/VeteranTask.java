package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.Advancements.AdvancementManager;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class VeteranTask extends BukkitRunnable {
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers())
            if(p.getStatistic(Statistic.PLAY_ONE_MINUTE) == 20*60*60*100) AdvancementManager.giveAdvancement(p, "veteran");
    }
}
