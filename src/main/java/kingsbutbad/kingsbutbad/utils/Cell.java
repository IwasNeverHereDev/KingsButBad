package kingsbutbad.kingsbutbad.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

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
        p.sendMessage(CreateText.addColors("<gray>You have been put in a <white>Cell "+(randomIndex+1)+"<gray>!"));
    }
}
