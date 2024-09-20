package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class ScheduleTask extends BukkitRunnable {
    Integer timer1 = 0;
    Integer timer2 = 1000;
    public static BossBar bossbar = Bukkit.createBossBar("??? TIME", BarColor.WHITE, BarStyle.SOLID);
    @Override
    public void run() {
        if (Bukkit.getWorld("world").getTime() > 0L && Bukkit.getWorld("world").getTime() < 2000L) {
            this.timer1 = 0;
            this.timer2 = 2500;
            bossbar.setColor(BarColor.RED);
            bossbar.setTitle("ROLL CALL");

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.roles.getOrDefault(p, Role.PEASANT).equals(Role.PRISONER)) {
                    WorldBorder rollborder = Bukkit.createWorldBorder();
                    rollborder.setCenter(new Location(Bukkit.getWorld("world"), -140.0, -57.0, 15.0));
                    rollborder.setSize(3.0);
                    rollborder.setDamageAmount(0.4);
                    rollborder.setDamageBuffer(0.0);
                    if (KingsButBad.isInside(
                            p, new Location(Bukkit.getWorld("world"), -139.0, -57.0, 16.0), new Location(Bukkit.getWorld("world"), -142.0, -57.0, 13.0)
                    )) {
                        if (!rollborder.isInside(p.getLocation())) {
                            p.damage(1.0);
                        }
                        p.setWorldBorder(rollborder);
                    } else if (!Objects.equals(p.getWorldBorder(), rollborder)) {
                        p.setWorldBorder(null);
                    }
                }
            }
        }

        if (Bukkit.getWorld("world").getTime() > 2000L && Bukkit.getWorld("world").getTime() < 4000L) {
            this.timer1 = 2000;
            this.timer2 = 4000;
            bossbar.setColor(BarColor.WHITE);

            for (Player px : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.roles.get(px).equals(Role.PRISONER)) {
                    WorldBorder rollborder = Bukkit.createWorldBorder();
                    rollborder.setCenter(new Location(Bukkit.getWorld("world"), -153.0, -58.0, 3.0));
                    rollborder.setSize(18.0);
                    rollborder.setDamageAmount(0.4);
                    rollborder.setDamageBuffer(0.0);
                    if (KingsButBad.isInside(
                            px, new Location(Bukkit.getWorld("world"), -144.0, -58.0, 5.0), new Location(Bukkit.getWorld("world"), -155.0, -53.0, -10.0)
                    )) {
                        if (!rollborder.isInside(px.getLocation())) {
                            px.damage(1.0);
                        }

                        px.setWorldBorder(rollborder);
                    } else if (!Objects.equals(px.getWorldBorder(), rollborder)) {
                        px.setWorldBorder(null);
                    }
                }
            }

            bossbar.setTitle("Breakfast");
        }

        if (Bukkit.getWorld("world").getTime() > 4000L && Bukkit.getWorld("world").getTime() < 7000L) {
            this.timer1 = 4000;
            this.timer2 = 7000;
            bossbar.setColor(BarColor.WHITE);
            bossbar.setTitle("Free Time");

            for (Player pxx : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.roles.get(pxx).equals(Role.PRISONER) && pxx.getWorldBorder() != null) {
                    pxx.setWorldBorder(null);
                }
            }
        }

        if (Bukkit.getWorld("world").getTime() >= 7000L && Bukkit.getWorld("world").getTime() <= 7005L) {
            for (Player pxxx : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.roles.get(pxxx).equals(Role.PRISONER)) {
                    KingsButBad.prisonQuota.put(pxxx, 30);
                }
            }
        }

        if (Bukkit.getWorld("world").getTime() == 10000L) {
            for (Player pxxxx : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.prisonQuota.getOrDefault(pxxxx, 0) > 0 && KingsButBad.roles.get(pxxxx).equals(Role.PRISONER)) {
                    pxxxx.sendTitle(ChatColor.RED + "MISSED QUOTA.", ChatColor.DARK_RED + "+80s to prison time.");
                    KingsButBad.prisonTimer.put(pxxxx, KingsButBad.prisonTimer.get(pxxxx) + 80);
                }
            }
        }

        if (Bukkit.getWorld("world").getTime() > 7000L && Bukkit.getWorld("world").getTime() < 10000L) {
            this.timer1 = 7000;
            this.timer2 = 10000;
            bossbar.setColor(BarColor.WHITE);
            bossbar.setTitle("Job Time");

            for (Player player : Bukkit.getOnlinePlayers()) {
                WorldBorder rollborder = Bukkit.createWorldBorder();
                rollborder.setCenter(new Location(Bukkit.getWorld("world"), -150.0, -49.0, 13.0));
                rollborder.setSize(15.0);
                rollborder.setDamageAmount(0.4);
                rollborder.setDamageBuffer(0.0);
                if (KingsButBad.roles.get(player).equals(Role.PRISONER)) {
                    if (KingsButBad.isInside(
                            player, new Location(Bukkit.getWorld("world"), -142.0, -50.0, 6.0), new Location(Bukkit.getWorld("world"), -157.0, -58.0, 20.0)
                    )) {
                        if (!rollborder.isInside(player.getLocation())) {
                            player.damage(1.0);
                        }

                        player.setWorldBorder(rollborder);
                    } else if (!Objects.equals(player.getWorldBorder(), rollborder)) {
                        player.setWorldBorder(null);
                    }
                }
            }
        }

        if (Bukkit.getWorld("world").getTime() > 10000L && Bukkit.getWorld("world").getTime() < 13000L) {
            this.timer1 = 10000;
            this.timer2 = 13000;
            bossbar.setColor(BarColor.WHITE);

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.roles.get(player).equals(Role.PRISONER)) {
                    WorldBorder rollborder = Bukkit.createWorldBorder();
                    rollborder.setCenter(new Location(Bukkit.getWorld("world"), -153.0, -58.0, 3.0));
                    rollborder.setSize(18.0);
                    rollborder.setDamageAmount(0.4);
                    rollborder.setDamageBuffer(0.0);
                    if (KingsButBad.isInside(
                            player, new Location(Bukkit.getWorld("world"), -144.0, -58.0, 5.0), new Location(Bukkit.getWorld("world"), -155.0, -53.0, -10.0)
                    )) {
                        if (!rollborder.isInside(player.getLocation())) {
                            player.damage(1.0);
                        }

                        player.setWorldBorder(rollborder);
                    } else if (!Objects.equals(player.getWorldBorder(), rollborder)) {
                        player.setWorldBorder(null);
                    }
                }
            }

            bossbar.setTitle("Lunch");
        }

        if (Bukkit.getWorld("world").getTime() > 13000L && Bukkit.getWorld("world").getTime() < 15000L) {
            this.timer1 = 13000;
            this.timer2 = 15000;
            bossbar.setColor(BarColor.RED);
            bossbar.setTitle("EVENING ROLL CALL");

            for (Player pxxxxxxx : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.roles.get(pxxxxxxx).equals(Role.PRISONER)) {
                    WorldBorder rollborder = Bukkit.createWorldBorder();
                    rollborder.setCenter(new Location(Bukkit.getWorld("world"), -140.0, -57.0, 15.0));
                    rollborder.setSize(3.0);
                    rollborder.setDamageAmount(0.4);
                    rollborder.setDamageBuffer(0.0);
                    if (KingsButBad.isInside(
                            pxxxxxxx, new Location(Bukkit.getWorld("world"), -139.0, -57.0, 16.0), new Location(Bukkit.getWorld("world"), -142.0, -57.0, 13.0)
                    )) {
                        if (!rollborder.isInside(pxxxxxxx.getLocation())) {
                            pxxxxxxx.damage(1.0);
                        }

                        pxxxxxxx.setWorldBorder(rollborder);
                    }
                }
            }
        }

        if (Bukkit.getWorld("world").getTime() > 15000L && Bukkit.getWorld("world").getTime() < 18000L) {
            this.timer1 = 15000;
            this.timer2 = 18000;
            bossbar.setColor(BarColor.PINK);
            bossbar.setTitle("Cell Time");

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.roles.get(player).equals(Role.PRISONER) && player.getWorldBorder() != null) {
                    player.setWorldBorder(null);
                }
            }

            int prisonersnotincell = 0;

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.roles.get(player).equals(Role.PRISONER)
                        && !KingsButBad.isInside(
                        player, new Location(Bukkit.getWorld("world"), -136.0, -53.0, -6.0), new Location(Bukkit.getWorld("world"), -132.0, -57.0, 23.0)
                )) {
                    player.sendTitle("", CreateText.addColors("<red><b>Get to your cell!"), 0, 20, 0);
                    prisonersnotincell = prisonersnotincell + 1;
                }
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (KingsButBad.roles.get(player).equals(Role.PRISON_GUARD) && prisonersnotincell != 0) {
                    player.sendTitle("", CreateText.addColors("<red><b>" + prisonersnotincell + " prisoners are not in their cells!"), 0, 20, 0);
                    prisonersnotincell = prisonersnotincell + 1;
                }
            }
        }

        if (Bukkit.getWorld("world").getTime() > 18000L && Bukkit.getWorld("world").getTime() < 24000L) {
            this.timer1 = 18000;
            this.timer2 = 24000;
            bossbar.setColor(BarColor.RED);
            bossbar.setTitle("LIGHTS OUT");
            Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 1L);
        }

        int currentTime = (int) Bukkit.getWorld("world").getTime();
        int startTime = this.timer1.intValue();
        int endTime = this.timer2.intValue();

        if (endTime > startTime) {
            double progress = (double)(currentTime - startTime) / (endTime - startTime);
            progress = Math.max(0.0, Math.min(1.0, progress));
            bossbar.setProgress(progress);
        } else {
            bossbar.setProgress(0.0);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(!Keys.money.has(player))
                Keys.money.set(player, 0.0);

            if (!KingsButBad.roles.containsKey(player)) {
                KingsButBad.roles.put(player, Role.PEASANT);
                RoleManager.givePlayerRole(player);
            }
        }

        if (Bukkit.getWorld("world").getTime() <= 18000L) {
            Bukkit.getWorld("world").getBlockAt(KingdomsLoader.activeKingdom.getPrisonLightPowerBlock()).setType(Material.REDSTONE_BLOCK);
        } else {
            Bukkit.getWorld("world").getBlockAt(KingdomsLoader.activeKingdom.getPrisonLightPowerBlock()).setType(Material.AIR);
        }
    }
}
