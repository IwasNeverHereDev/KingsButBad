package agmas.kingsbutbad.tasks;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.commands.KingCommand;
import agmas.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static agmas.kingsbutbad.commands.KingCommand.kinglasttimer;

public class KingLastTask extends BukkitRunnable {
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers())
            if(kinglasttimer.containsKey(p.getUniqueId()))
                if(kinglasttimer.getOrDefault(p.getUniqueId(), 0) > 0)
                    kinglasttimer.put(p.getUniqueId(), kinglasttimer.getOrDefault(p.getUniqueId(), 0) - 1);
                else {
                    if(kinglasttimer.getOrDefault(p.getUniqueId(), 0) == 0){
                        kinglasttimer.remove(p.getUniqueId());
                        if(p == KingsButBad.lastKing) {
                            KingsButBad.lastKing = null;
                            p.sendMessage(CreateText.addColors("<gray>You are no longer last king! (<white>timer ran out<gray>)"));
                        }
                        if(p == KingsButBad.lastKing2) {
                            KingsButBad.lastKing2 = null;
                            p.sendMessage(CreateText.addColors("<gray>You are no longer last king! (<white>timer ran out<gray>)"));
                        }
                    }
                }
    }
}
