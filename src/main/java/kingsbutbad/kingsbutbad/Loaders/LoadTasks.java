package kingsbutbad.kingsbutbad.Loaders;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.tasks.*;

public class LoadTasks {
    public static void init(){
        KingsButBad p = KingsButBad.pl;
        new MiscTask().runTaskTimer(p, 0L, 1L);
        new HandcuffTask().runTaskTimer(p, 0L, 1L);
        new VanishTask().runTaskTimer(p, 0L, 1L);
        new KingLastTask().runTaskTimer(p, 0L, 1L);
        new AreasTask().runTaskTimer(p, 0L, 5L);
        new FailsafeTask().runTaskTimer(p, 0L, 1L);
        new RoleTask().runTaskTimer(p, 0L, 1L);
        new AFKTask().runTaskTimer(p, 3000L, 6000L);
        new LocationTask().runTaskTimer(p, 0L, 6000L);
        new VeteranTask().runTaskTimer(p, 0L, 20);
        new RaidTask().runTaskTimer(p, 0L, 1);
    }
}
