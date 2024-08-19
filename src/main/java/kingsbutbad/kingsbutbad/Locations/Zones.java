package kingsbutbad.kingsbutbad.Locations;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import org.bukkit.Location;

public enum Zones {
    CASTLE(KingdomsLoader.activeKingdom.getCastle1(), KingdomsLoader.activeKingdom.getCastle2()),
    PRISON(KingdomsLoader.activeKingdom.getPrison1(), KingdomsLoader.activeKingdom.getPrison2()),
    OUTSIDE(KingdomsLoader.activeKingdom.getOutside1(), KingdomsLoader.activeKingdom.getOutside2());
    private Location loc1;
    private Location loc2;
    Zones(Location loc1, Location loc2){
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    public Location getLoc1() {
        return loc1;
    }

    public Location getLoc2() {
        return loc2;
    }
}
