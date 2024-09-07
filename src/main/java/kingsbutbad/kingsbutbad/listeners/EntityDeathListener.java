package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.tasks.RaidTask;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Objects;

public class EntityDeathListener implements Listener {
    @EventHandler
    public void EntityDeathEvent(EntityDeathEvent event){
        event.getDrops().clear();
        if(event.getEntity() instanceof WitherSkeleton) {
            if (KingsButBad.isRaidActive) {
                KingsButBad.raidEnemies.remove(event.getEntity());
                event.getDrops().clear();
                checkIfRaidEnds(true);
                if(event.getEntity().getKiller() != null && event.getEntity().getKiller().getPlayer() != null) {
                    KingsButBad.inRaid.add(event.getEntity().getKiller().getPlayer());
                    Keys.money.addDouble(Objects.requireNonNull(event.getEntity().getKiller().getPlayer()), 150.0);
                }
            }
        }else if (event.getEntity() instanceof Zombie){
            String msg = CreateText.addColors("<red>One of you Royal Patrols has died... <gray>(<white> Royal Patrols Left...");
            if(event.getEntity().getKiller() != null && event.getEntity().getKiller().getPlayer() != null)
                msg = CreateText.addColors("<red>One of you Royal Patrols was killed by <b>"+event.getEntity().getKiller().getPlayer().getName()+"</b>... <gray>(<white>"+getRoyalPatrols()+" Royal Patrols Left...");
            KingsButBad.king.sendMessage(msg);
            KingsButBad.king2.sendMessage(msg);
        }
    }
    private int getRoyalPatrols(){
        int royalPatrols = 0;
        for(Entity entity : getWorld().getEntities())
            if(entity instanceof Zombie) royalPatrols++;
        return royalPatrols;
    }
    private static World getWorld(){
        World world = Bukkit.getWorld("world");
        if(world == null) return Bukkit.getWorlds().get(0);
        return world;
    }
    public static void checkIfRaidEnds(boolean didWin){
        if(RaidTask.getRaiderCount() <= 0) CleanUpRaid(didWin);
    }
    @SuppressWarnings("deprecation")
    private static void CleanUpRaid(boolean didWin){
        for(Entity entity : getWorld().getEntities())
            if(entity instanceof WitherSkeleton)
                entity.remove();
        KingsButBad.raidEnemies.clear();
        KingsButBad.raidCooldown = 20*60*5;
        KingsButBad.isRaidActive = false;
        Bukkit.broadcastMessage(CreateText.addColors("<red>Raid has ended! "+didWinString(didWin)));
        if(didWin) {
            if (KingsButBad.king != null && wasInRaid(KingsButBad.king)) {
                Keys.money.addDouble(KingsButBad.king, 10000.0);
                KingsButBad.king.sendMessage(CreateText.addColors("<gray>The raid has ended! (<white>10k<gray>)"));
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!KingsButBad.roles.getOrDefault(p, Role.PEASANT).isPowerful) {
                    Keys.money.addDouble(p, 1500.0);
                    p.sendMessage(CreateText.addColors("<gray>The raid has ended! (<white>1.5k<gray>)"));
                    continue;}
                if (KingsButBad.king == p) continue;
                if(!wasInRaid(p)) continue;
                Keys.money.addDouble(KingsButBad.king, 2500.0);
                p.sendMessage(CreateText.addColors("<gray>The raid has ended! (<white>2.5k<gray>)"));
            }
        }
    }
    private static String didWinString(boolean DidWin){
        if(DidWin) return "Win";
        return "Lost";
    }
    public static boolean wasInRaid(Player p) {
        for (Player user : KingsButBad.inRaid)
            if (p == user) return true;
        return false;
    }
}
