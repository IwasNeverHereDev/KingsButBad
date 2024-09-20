package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static kingsbutbad.kingsbutbad.commands.KingCommand.kingLastTimer;

public class KingLastTask extends BukkitRunnable {
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers())
            if(kingLastTimer.containsKey(p.getUniqueId()))
                if(kingLastTimer.getOrDefault(p.getUniqueId(), 0) > 0)
                    kingLastTimer.put(p.getUniqueId(), kingLastTimer.getOrDefault(p.getUniqueId(), 0) - 1);
                else {
                    if(kingLastTimer.getOrDefault(p.getUniqueId(), 0) == 0){
                        kingLastTimer.remove(p.getUniqueId());
                        if(p.getUniqueId() == KingsButBad.lastKing) {
                            KingsButBad.lastKing = null;
                            p.sendMessage(CreateText.addColors("<gray>You are no longer last king! (<white>timer ran out<gray>)"));
                        }
                        if(p.getUniqueId() == KingsButBad.lastKing2) {
                            KingsButBad.lastKing2 = null;
                            p.sendMessage(CreateText.addColors("<gray>You are no longer last king! (<white>timer ran out<gray>)"));
                        }
                    }
                }
    }
}
