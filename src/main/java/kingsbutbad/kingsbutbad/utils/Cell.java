package kingsbutbad.kingsbutbad.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

import static kingsbutbad.kingsbutbad.tasks.MiscTask.cells;

public class Cell {
    public static void tpToRandomCell(Player p) {
        if (cells.isEmpty()) {
            p.sendMessage(CreateText.addColors("<red>Contact _Aquaotter_ there are no cells! >:("));
            return;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(cells.size());

        Location randomLocation = cells.get(randomIndex);
        p.teleport(randomLocation);
        p.sendMessage(CreateText.addColors("<gray>You have been put in <white>Cell "+(randomIndex+1)+"<gray>!"));
    }
    public static Location findClosestLocation(List<Location> locations, Location input) {
        if (locations == null || locations.isEmpty() || input == null) {
            return null;
        }

        Location closest = null;
        double closestDistanceSquared = Double.MAX_VALUE;

        for (Location loc : locations) {
            double distanceSquared = loc.distanceSquared(input);
            if (distanceSquared < closestDistanceSquared) {
                closestDistanceSquared = distanceSquared;
                closest = loc;
            }
        }

        return closest;
    }
}
