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
        new VeteranTask().runTaskTimer(p, 0L, 20*60);
        new RaidTask().runTaskTimer(p, 0L, 1);
        new PrinceTask().runTaskTimer(p, 0L, 1);
        new GangLevelTask().runTaskTimer(p, 0L, 20*5);
        new ReloadVillagersTask().runTaskTimer(p, 0L, 20*60);
        new PingSpoofingTask().runTaskTimer(p, 0L, 20);
    }
}
