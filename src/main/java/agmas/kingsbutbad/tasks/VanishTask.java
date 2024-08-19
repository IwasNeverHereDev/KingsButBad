package agmas.kingsbutbad.tasks;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.keys.Keys;
import agmas.kingsbutbad.listeners.PlayerJoinListener;
import agmas.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class VanishTask extends BukkitRunnable {
    @Override
    public void run() {
        PlayerJoinListener.updateTab();
        for(Player p : Bukkit.getOnlinePlayers())
            vanishPlayer(p);
    }
    private void vanishPlayer(Player p){
        if(!Keys.vanish.get(p, false)) return;
        p.sendActionBar(CreateText.addColors("<red>You are in vanish! <gray>(<white>Only staff can see you!<gray>)"));
        for(Player player : Bukkit.getOnlinePlayers())
            if(!player.hasPermission("kbb.staff")) player.hidePlayer(KingsButBad.pl, p);
    }
}
