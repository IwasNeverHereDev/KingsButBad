package agmas.kingsbutbad.tasks;

import agmas.kingsbutbad.Kingdom.KingdomsLoader;
import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.Role;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static agmas.kingsbutbad.tasks.MiscTask.bossbar;

public class AreasTask extends BukkitRunnable {
    private static final String PRISON_TITLE = ChatColor.GOLD + "-= The Prison =-";
    private static final String CASTLE_TITLE = ChatColor.GRAY + "-= The Castle =-";
    private static final String OUTSIDE_TITLE = ChatColor.GREEN + "-= The Outside =-";
    private static final String STAY_IN_PRISON_TITLE = ChatColor.RED + "Stay in the prison!";

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayerZone(player);
            handlePrisonGuard(player);
        }
    }

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
        for (Entity passenger : player.getPassengers()) {
            passenger.leaveVehicle();
        }
    }

    private void teleportToPrison(Player player) {
        player.teleport(KingdomsLoader.activeKingdom.getPrisonGuardSpawn());
    }
}
