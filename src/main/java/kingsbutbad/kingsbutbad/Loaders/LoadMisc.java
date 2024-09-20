package kingsbutbad.kingsbutbad.Loaders;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;

import static kingsbutbad.kingsbutbad.tasks.MiscTask.cells;

public class LoadMisc {
    public static void init(){
        cells.clear();
        cells.addAll(KingdomsLoader.activeKingdom.getCells());
    }
}
