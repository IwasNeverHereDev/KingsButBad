package kingsbutbad.kingsbutbad.Kingdom;

import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class KingdomsReader {
   private final Path kingdomsFilePath;
   private final Gson gson;
   public static List<Kingdom> kingdomsList = new ArrayList<>();

   public KingdomsReader(Path kingdomsFilePath) {
      this.kingdomsFilePath = kingdomsFilePath;
      this.gson = new Gson();
   }

   public Map<String, Kingdom> read() {
      Map<String, Kingdom> kingdoms = new HashMap<>();

      try (FileReader reader = new FileReader(this.kingdomsFilePath.toFile())) {
         JsonElement rootElement = JsonParser.parseReader(reader);
         JsonObject rootObject = rootElement.getAsJsonObject();
         JsonObject kingdomsObject = rootObject.getAsJsonObject("kingdoms");
         kingdomsList = new ArrayList<>();

         for (Entry<String, JsonElement> entry : kingdomsObject.entrySet()) {
            String key = entry.getKey();
            JsonObject kingdomObject = entry.getValue().getAsJsonObject();
            String material = kingdomObject.get("material").getAsString();
            boolean isPublic = kingdomObject.get("isPublic").getAsBoolean();
            String displayName = kingdomObject.get("displayName").getAsString();
            String name = kingdomObject.get("name").getAsString();
            Location prisonersSpawn = this.readLocation(kingdomObject.getAsJsonObject("prisonersSpawn"));
            Location mafiaSpawn = this.readLocation(kingdomObject.getAsJsonObject("mafiaSpawn"));
            Location kingSpawn = this.readLocation(kingdomObject.getAsJsonObject("kingSpawn"));
            Location knightsSpawn = this.readLocation(kingdomObject.getAsJsonObject("knightsSpawn"));
            Location jailSpawn = this.readLocation(kingdomObject.getAsJsonObject("jailSpawn"));
            Location farmerJoeLocation = this.readLocation(kingdomObject.getAsJsonObject("farmerJoeLocation"));
            Location minerGuardLocation = this.readLocation(kingdomObject.getAsJsonObject("minerGuardLocation"));
            Location bertrudeLocation = this.readLocation(kingdomObject.getAsJsonObject("bertrudeLocation"));
            Location lunchLadyLocation = this.readLocation(kingdomObject.getAsJsonObject("lunchLadyLocation"));
            Location royalTraderLocation = this.readLocation(kingdomObject.getAsJsonObject("royalTraderLocation"));
            Location royalServantLocation = this.readLocation(kingdomObject.getAsJsonObject("royalServantLocation"));
            Location mopVillagerLocation = this.readLocation(kingdomObject.getAsJsonObject("mopVillagerLocation"));
            Location archerJohnLocation = this.readLocation(kingdomObject.getAsJsonObject("archerJohnLocation"));
            Location prisonGuardLocation = this.readLocation(kingdomObject.getAsJsonObject("prisonGuardLocation"));
            Location littleJoesLocation = this.readLocation(kingdomObject.getAsJsonObject("littleJoesLocation"));
            Location servantLocation = this.readLocation(kingdomObject.getAsJsonObject("servantLocation"));
            Location minerLocation = this.readLocation(kingdomObject.getAsJsonObject("minerLocation"));
            Location selfDefenseLocation = this.readLocation(kingdomObject.getAsJsonObject("selfDefenseLocation"));
            Location sewerVillagerLocation = this.readLocation(kingdomObject.getAsJsonObject("sewerVillagerLocation"));
            Location royalVillagerLocation = this.readLocation(kingdomObject.getAsJsonObject("royalVillagerLocation"));
            Location mineEntranceLoc1 = this.readLocation(kingdomObject.getAsJsonObject("mineEntranceLoc1"));
            Location mineEntranceLoc2 = this.readLocation(kingdomObject.getAsJsonObject("mineEntranceLoc2"));
            Location mineExitLoc1 = this.readLocation(kingdomObject.getAsJsonObject("mineExitLoc1"));
            Location mineExitLoc2 = this.readLocation(kingdomObject.getAsJsonObject("mineExitLoc2"));
            Location castle1 = this.readLocation(kingdomObject.getAsJsonObject("castle1"));
            Location castle2 = this.readLocation(kingdomObject.getAsJsonObject("castle2"));
            Location outside1 = this.readLocation(kingdomObject.getAsJsonObject("outside1"));
            Location outside2 = this.readLocation(kingdomObject.getAsJsonObject("outside2"));
            Location prison1 = this.readLocation(kingdomObject.getAsJsonObject("prison1"));
            Location prison2 = this.readLocation(kingdomObject.getAsJsonObject("prison2"));
            Location bm1 = this.readLocation(kingdomObject.getAsJsonObject("bm1"));
            Location bm2 = this.readLocation(kingdomObject.getAsJsonObject("bm2"));
            Location prisonLightPowerBlock = this.readLocation(kingdomObject.getAsJsonObject("prisonLightPowerBlock"));
            Location mafiaVillager = this.readLocation(kingdomObject.getAsJsonObject("MafiaRecruiter"));
            Location blackMarketInsidePrisoner = this.readLocation(kingdomObject.getAsJsonObject("blackMarketInsidePrisoner"));
            Location blackMarketExitPrisoner = this.readLocation(kingdomObject.getAsJsonObject("blackMarketExitPrisoner"));
            Location bmSafe = this.readLocation(kingdomObject.getAsJsonObject("bmSafe"));
            Location prisonGuardSpawn = this.readLocation(kingdomObject.getAsJsonObject("prisonGuardSpawn"));
            Location bmPrison1 = this.readLocation(kingdomObject.getAsJsonObject("bmPrison1"));
            Location bmPrison2 = this.readLocation(kingdomObject.getAsJsonObject("bmPrison2"));
            Location bmPrisonTrader = this.readLocation(kingdomObject.getAsJsonObject("bmPrisonTrader"));
            List<Location> cells = new ArrayList<>();
            JsonArray cellsArray = kingdomObject.getAsJsonArray("cells");
            if (cellsArray != null) {
               for (JsonElement cellElement : cellsArray) {
                  JsonObject cellObject = cellElement.getAsJsonObject();
                  Location cellLocation = this.readLocation(cellObject);
                  cells.add(cellLocation);
               }
            }
            Kingdom kingdom = new Kingdom(
               material,
               isPublic,
               displayName,
               name,
               prisonersSpawn,
               mafiaSpawn,
               kingSpawn,
               knightsSpawn,
               jailSpawn,
               farmerJoeLocation,
               minerGuardLocation,
               bertrudeLocation,
               lunchLadyLocation,
               royalTraderLocation,
               royalServantLocation,
               mopVillagerLocation,
               archerJohnLocation,
               prisonGuardLocation,
               littleJoesLocation,
               servantLocation,
               minerLocation,
               selfDefenseLocation,
               sewerVillagerLocation,
               royalVillagerLocation,
               mafiaVillager,
               mineEntranceLoc1,
               mineEntranceLoc2,
               mineExitLoc1,
               mineExitLoc2,
               castle1,
               castle2,
               outside1,
               outside2,
               prison1,
               prison2,
               bm1,
               bm2,
               prisonLightPowerBlock,
                    blackMarketInsidePrisoner,
                    blackMarketExitPrisoner,
                    cells,
                    bmSafe,
                    prisonGuardSpawn,
                    bmPrison1,
                    bmPrison2,
                    bmPrisonTrader
            );
            kingdomsList.add(kingdom);
            kingdoms.put(key, kingdom);
         }
      } catch (IOException var50) {
         var50.printStackTrace();
      }

      return kingdoms;
   }

   private Location readLocation(JsonObject locationObject) {
      if (locationObject == null) {
         // Handle the null case, maybe throw an exception or return a default Location
         Bukkit.getLogger().warning("Attempted to read a null location object.");
         return new Location(Bukkit.getWorlds().get(0), 0, 0, 0); // Default Location
      }

      String worldName = locationObject.has("world") ? locationObject.get("world").getAsString() : "world"; // Default world
      double x = locationObject.has("x") ? locationObject.get("x").getAsDouble() : 0.0; // Default x
      double y = locationObject.has("y") ? locationObject.get("y").getAsDouble() : 0.0; // Default y
      double z = locationObject.has("z") ? locationObject.get("z").getAsDouble() : 0.0; // Default z
      float yaw = locationObject.has("yaw") ? locationObject.get("yaw").getAsFloat() : 0.0f; // Default yaw
      float pitch = locationObject.has("pitch") ? locationObject.get("pitch").getAsFloat() : 0.0f; // Default pitch

      // Validate the world
      World world = Bukkit.getWorld(worldName);
      if (world == null) {
         world = Bukkit.getWorlds().get(0); // Use the first loaded world if the specified world is not found
      }

      return new Location(world, x, y, z, yaw, pitch);
   }
}
