package kingsbutbad.kingsbutbad.Loaders;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsReader;
import java.io.File;
import java.nio.file.Path;

public class LoadKingdoms {
   public static void reload() {
      new KingdomsReader(Path.of(new File(KingsButBad.pl.getDataFolder(), "kingdoms.yml").getPath())).read();
   }

   public static void init() {
      reload();
      KingdomsLoader.setActiveKingdomByName("default");
   }
}
