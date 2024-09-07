package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static kingsbutbad.kingsbutbad.tasks.MiscTask.bossbar;

public class AreasTask extends BukkitRunnable {
    private static final String PRISON_TITLE = CreateText.addColors("<gray>-= <gold>The Prison <gray>=-");
    private static final String CASTLE_TITLE = CreateText.addColors("<gray>-= <white>The Castle <gray>=-");
    private static final String OUTSIDE_TITLE = CreateText.addColors("<gray>-= <green>The Outside <gray>=-");
    private static final String STAY_IN_PRISON_TITLE = CreateText.addColors("<red>Stay in the prison!");

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayerZone(player);
            handlePrisonGuard(player);
        }
    }
    @SuppressWarnings("deprecation")
    private void updatePlayerZone(Player player) {
        int currentZone = KingsButBad.currentZone.getOrDefault(player, 0);

        if (KingsButBad.isInside(player, KingdomsLoader.activeKingdom.getPrison1(), KingdomsLoader.activeKingdom.getPrison2())) {
            if (currentZone != 1) {
                player.sendTitle("", PRISON_TITLE);
                bossbar.addPlayer(player);
                KingsButBad.currentZone.put(player, 1);
            }
        } else if (KingsButBad.isInside(player, KingdomsLoader.activeKingdom.getCastle1(), KingdomsLoader.activeKingdom.getCastle2())) {
            if (currentZone != 2) {
                player.sendTitle("", CASTLE_TITLE);
                bossbar.removePlayer(player);
                KingsButBad.currentZone.put(player, 2);
            }
        } else if (currentZone != 0) {
            player.sendTitle("", OUTSIDE_TITLE);
            bossbar.removePlayer(player);
            KingsButBad.currentZone.put(player, 0);
        }
    }

    @SuppressWarnings("deprecation")
    private void handlePrisonGuard(Player player) {
        Role playerRole = KingsButBad.roles.get(player);

        if (Role.PRISON_GUARD.equals(playerRole)
                && !bossbar.getPlayers().contains(player)
                && !"LIGHTS OUT".equals(bossbar.getTitle())) {

            player.sendTitle("", STAY_IN_PRISON_TITLE);
            removePassengers(player);
            teleportToPrison(player);
        }
    }

    private void removePassengers(Player player) {
        for (Entity passenger : player.getPassengers())
            player.removePassenger(passenger);
    }

    private void teleportToPrison(Player player) {
        player.teleport(KingdomsLoader.activeKingdom.getPrisonGuardSpawn());
    }
}
