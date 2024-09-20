package kingsbutbad.kingsbutbad.Kingdom.Areas;

import kingsbutbad.kingsbutbad.Kingdom.Kingdom;
import org.bukkit.Location;

import java.util.List;

public class Area {
    private final Location loc1;
    private final Location loc2;
    private final AreaTypes areaTypes;
    private final List<Location> listLoc;
    private final Kingdom kingdom;
    private final String displayName;
    public Area(Location loc1, Location loc2, AreaTypes areaTypes, List<Location> listLoc, Kingdom kingdom, String displayName) {
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.areaTypes = areaTypes;
        this.listLoc = listLoc;
        this.kingdom = kingdom;
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
    public List<Location> getListLoc() {
        return listLoc;
    }
    public Location getLoc2() {
        return loc2;
    }
    public Location getLoc1() {
        return loc1;
    }
    public AreaTypes getAreaTypes() {
        return areaTypes;
    }
    public Kingdom getKingdom() {
        return kingdom;
    }
}
