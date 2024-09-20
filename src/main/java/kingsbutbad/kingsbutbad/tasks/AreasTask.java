package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.Kingdom.Areas.Area;
import kingsbutbad.kingsbutbad.Kingdom.Areas.AreaLoaders;
import kingsbutbad.kingsbutbad.Kingdom.Areas.AreaTypes;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.Cell;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Pacts;
import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

import static kingsbutbad.kingsbutbad.tasks.ScheduleTask.bossbar;

public class AreasTask extends BukkitRunnable {
    private static final String PRISON_TITLE = CreateText.addColors("<gray>-= <gold>The Prison <gray>=-");
    private static final String CASTLE_TITLE = CreateText.addColors("<gray>-= <white>The Castle <gray>=-");
    private static final String OUTSIDE_TITLE = CreateText.addColors("<gray>-= <green>The Outside <gray>=-");

    public void run() {
        updateAreaSystem();
        Bukkit.getOnlinePlayers().forEach(this::updatePlayerZone);
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
    private void updateAreaSystem(){
        for(Area area : AreaLoaders.AreasList){
            if(area.getKingdom() != KingdomsLoader.activeKingdom) return;
            for(Player p : Bukkit.getOnlinePlayers()){
                if(!isInside(area, p)) continue;
                if(p.getGameMode() != GameMode.ADVENTURE) return;
                AreaTypes type = area.getAreaTypes();
                if(type.equals(AreaTypes.NON_KINGDOM) && KingsButBad.roles.getOrDefault(p, Role.PEASANT).isPowerful) {
                    p.teleport(Cell.findClosestLocation(area.getListLoc(), p.getLocation()));
                    p.sendMessage(CreateText.addColors("<red>Sorry, Kingdom roles can't be here!"));
                }
                if(type.equals(AreaTypes.NON_KINGDOM)){
                    if(Keys.activePact.get(p, "") == Pacts.SERVANT.name() || Keys.activePact.get(p, "") == Pacts.KNIGHT.name()){
                        p.teleport(Cell.findClosestLocation(area.getListLoc(), p.getLocation()));
                        p.sendMessage(CreateText.addColors("<red>Sorry, Your pact says can't be here!"));
                    }
                }
                if(type.equals(AreaTypes.TELEPORT))
                    p.teleport(Cell.findClosestLocation(area.getListLoc(), p.getLocation()));
            }
        }
    }
    public boolean isInside(Area area, Player player) {
        double[] dim = new double[2];

        dim[0] = area.getLoc1().getX();
        dim[1] = area.getLoc2().getX();
        Arrays.sort(dim);
        if(player.getLocation().getX() > dim[1] || player.getLocation().getX() < dim[0])
            return false;

        dim[0] = area.getLoc1().getY();
        dim[1] = area.getLoc2().getY();
        Arrays.sort(dim);
        if(player.getLocation().getY() > dim[1] || player.getLocation().getY() < dim[0])
            return false;

        dim[0] = area.getLoc1().getZ();
        dim[1] = area.getLoc2().getZ();
        Arrays.sort(dim);
        return !(player.getLocation().getZ() > dim[1]) && !(player.getLocation().getZ() < dim[0]);
    }
}
