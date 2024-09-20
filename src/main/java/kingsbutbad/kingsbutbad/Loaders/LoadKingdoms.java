package kingsbutbad.kingsbutbad.Loaders;

import kingsbutbad.kingsbutbad.Kingdom.Areas.AreaLoaders;
import kingsbutbad.kingsbutbad.Kingdom.KingdomManager;
import kingsbutbad.kingsbutbad.Kingdom.Upgrades.UpgradeLoader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsReader;
import java.io.File;
import java.nio.file.Path;

public class LoadKingdoms {
   public static void reload() {
      new KingdomsReader(Path.of(new File(KingsButBad.pl.getDataFolder(), "kingdoms.yml").getPath())).read();
      new UpgradeLoader().read(new File(KingsButBad.pl.getDataFolder(), "Upgrades.json").toPath());
      new AreaLoaders().read(new File(KingsButBad.pl.getDataFolder(), "Areas.json").toPath());
   }

   public static void init() {
      reload();
      KingdomsLoader.setActiveKingdomByName("default");
   }
}
