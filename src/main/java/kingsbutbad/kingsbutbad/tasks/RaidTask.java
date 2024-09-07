package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.listeners.EntityDeathListener;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.scheduler.BukkitRunnable;

public class RaidTask extends BukkitRunnable {
    @Override
    public void run() {
        if(KingsButBad.raidCooldown >= 0)
            KingsButBad.raidCooldown--;
        if (!KingsButBad.isRaidActive) {
            if (KingsButBad.raidBossbar != null) {
                KingsButBad.raidBossbar.setVisible(false);
                KingsButBad.raidBossbar = null;
            }
            return;
        }else{
            Bukkit.getWorld("world").getEntities().forEach(entity -> {
                if(entity instanceof WitherSkeleton)
                    if(!KingsButBad.raidEnemies.contains(entity)) entity.remove();
            });
        }

        if (KingsButBad.raidBossbar == null) {
            KingsButBad.raidBossbar = Bukkit.createBossBar(
                    CreateText.addColors("<red>Raid <gray>(<white>" + getRaiderCount() + " Raiders Left<gray>)"),
                    BarColor.RED,
                    BarStyle.SEGMENTED_20,
                    BarFlag.PLAY_BOSS_MUSIC,
                    BarFlag.DARKEN_SKY
            );
            KingsButBad.raidBossbar.setVisible(true);
        }

        if (KingsButBad.raidBossbar != null) {
            for(Player p : Bukkit.getOnlinePlayers())
                if(!KingsButBad.raidBossbar.getPlayers().contains(p))
                    KingsButBad.raidBossbar.addPlayer(p);
            KingsButBad.raidBossbar.setProgress(getBossbarProgress());
        }
    }

    private double getBossbarProgress() {
        EntityDeathListener.checkIfRaidEnds(false);
        int totalEnemies = KingsButBad.raidStartedEnmeiesCount;
        int remainingRaiders = getRaiderCount();
        KingsButBad.raidBossbar.setTitle(CreateText.addColors("<red>Raid <gray>(<white>"+remainingRaiders+" Raiders Left...<gray>)"));

        if (totalEnemies <= 0)
            return 0.0;

        return Math.max(0, Math.min(1, (double) remainingRaiders / totalEnemies));
    }

    public static int getRaiderCount() {
        int count = 0;
       for(Entity entity : Bukkit.getWorld("world").getEntities()) {
           if (entity instanceof WitherSkeleton)
               for (Entity radiers : KingsButBad.raidEnemies) {
                   if (entity.isInWater())
                       entity.remove();
                   if(((WitherSkeleton) entity).getTarget() == null)
                       ((WitherSkeleton) entity).setTarget(getNearestPlayer((Monster) entity));
                   if (entity == radiers) count++;
               }
       }
       return count;
    }
    private static Player getNearestPlayer(Monster monster) {
        Player nearestPlayer = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(!player.getGameMode().equals(GameMode.ADVENTURE)) continue;
            if(Keys.vanish.get(player, false)) continue;
            double distance = player.getLocation().distance(monster.getLocation());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPlayer = player;
            }
        }
        return nearestPlayer;
    }
}