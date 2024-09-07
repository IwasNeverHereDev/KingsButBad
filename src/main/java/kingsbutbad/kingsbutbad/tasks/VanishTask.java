package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.listeners.PlayerJoinListener;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class VanishTask extends BukkitRunnable {
    public static HashMap<Player, Integer> vanishPlayerTimeOfVanish = new HashMap<>();
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            PlayerJoinListener.updateTab(p);
            vanishPlayer(p);
        }
    }
    private void vanishPlayer(Player p){
        if(!Keys.vanish.get(p, false)) return;
        p.sendActionBar(CreateText.addColors("<red>You are in vanish! <gray>(<white>Only staff can see you!<gray>)"));
        for(Player player : Bukkit.getOnlinePlayers())
            if(!player.hasPermission("kbb.staff")) player.hidePlayer(KingsButBad.pl, p);
    }
}
