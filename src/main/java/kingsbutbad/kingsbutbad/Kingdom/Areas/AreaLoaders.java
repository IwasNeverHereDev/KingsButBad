package kingsbutbad.kingsbutbad.Kingdom.Areas;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kingsbutbad.kingsbutbad.Kingdom.Kingdom;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AreaLoaders {
    public static List<Area> AreasList = new ArrayList<>();
    public void read(Path areaPath) {
        try (FileReader reader = new FileReader(areaPath.toFile())) {
            JsonElement rootElement = JsonParser.parseReader(reader);
            JsonObject rootObject = rootElement.getAsJsonObject();
            JsonObject kingdomsObject = rootObject.getAsJsonObject("Areas");
            AreasList = new ArrayList<>();

            for (Map.Entry<String, JsonElement> entry : kingdomsObject.entrySet()) {
                JsonObject areaObj = entry.getValue().getAsJsonObject();
                AreaTypes areaType = AreaTypes.valueOf(areaObj.get("Type").getAsString().toUpperCase());
                String kingdomName = areaObj.get("KingdomName").getAsString();
                String displayName = entry.getKey();
                Kingdom targetKingdom = null;
                for(Kingdom kingdom : KingdomsReader.kingdomsList)
                    if(kingdom.getName().equalsIgnoreCase(kingdomName)) targetKingdom = kingdom;
                Location location1 = this.readLocation(areaObj.get("Location1").getAsJsonObject());
                Location location2 = this.readLocation(areaObj.get("Location2").getAsJsonObject());
                JsonArray locations = areaObj.getAsJsonArray("Locations");
                List<Location> locationList = new ArrayList<>();
                if (locations != null)
                    for (JsonElement cellElement : locations) {
                        JsonObject cellObject = cellElement.getAsJsonObject();
                        Location cellLocation = this.readLocation(cellObject);
                        locationList.add(cellLocation);
                    }
                Area area = new Area(location1, location2, areaType, locationList, targetKingdom, displayName);
                AreasList.add(area);
            }
        } catch (IOException var50) {
            Bukkit.getLogger().severe(var50.getMessage());
        }
    }

    private Location readLocation(JsonObject locationObject) {
        if (locationObject == null) {
            Bukkit.getLogger().warning("Attempted to read a null location object.");
            return new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
        }

        String worldName = locationObject.has("world") ? locationObject.get("world").getAsString() : "world";
        double x = locationObject.has("x") ? locationObject.get("x").getAsDouble() : 0.0;
        double y = locationObject.has("y") ? locationObject.get("y").getAsDouble() : 0.0;
        double z = locationObject.has("z") ? locationObject.get("z").getAsDouble() : 0.0;
        float yaw = locationObject.has("yaw") ? locationObject.get("yaw").getAsFloat() : 0.0f;
        float pitch = locationObject.has("pitch") ? locationObject.get("pitch").getAsFloat() : 0.0f;

        World world = Bukkit.getWorld(worldName);

        if (world == null)
            world = Bukkit.getWorlds().get(0);

        return new Location(world, x, y, z, yaw, pitch);
    }
}
