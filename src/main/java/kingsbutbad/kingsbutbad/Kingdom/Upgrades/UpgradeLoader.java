package kingsbutbad.kingsbutbad.Kingdom.Upgrades;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kingsbutbad.kingsbutbad.Kingdom.Kingdom;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsReader;
import kingsbutbad.kingsbutbad.KingsButBad;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UpgradeLoader {
    public static List<Upgrade> UpgradeList = new ArrayList<>();
    public void read(Path areaPath) {
        try (FileReader reader = new FileReader(areaPath.toFile())) {
            JsonElement rootElement = JsonParser.parseReader(reader);
            JsonObject rootObject = rootElement.getAsJsonObject();
            JsonObject kingdomsObject = rootObject.getAsJsonObject("Upgrade");
            UpgradeList = new ArrayList<>();

            for (Map.Entry<String, JsonElement> entry : kingdomsObject.entrySet()) {
                JsonObject areaObj = entry.getValue().getAsJsonObject();
                UpgradeTypes areaType = UpgradeTypes.valueOf(areaObj.get("Type").getAsString().toUpperCase());
                ItemStack item = new ItemStack(Material.valueOf(areaObj.get("Itemstack").getAsString()));
                String kingdomName = areaObj.get("KingdomName").getAsString();
                Kingdom targetKingdom = null;
                for(Kingdom kingdom : KingdomsReader.kingdomsList)
                    if(kingdom.getName().equalsIgnoreCase(kingdomName)) targetKingdom = kingdom;
                String displayName = areaObj.get("DisplayName").getAsString();
                double cost = areaObj.get("Cost").getAsDouble();
                Path path = new File(KingsButBad.pl.getDataFolder(), "Upgrades/"+areaObj.get("Path").getAsString()).toPath();
                Upgrade upgrade = new Upgrade(areaType, item, cost, displayName, path);
                UpgradeList.add(upgrade);
            }
        } catch (IOException var50) {
            Bukkit.getLogger().severe(var50.getMessage());
        }
    }
}
