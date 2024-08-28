package kingsbutbad.kingsbutbad.Kingdom;

import org.bukkit.Location;

import java.util.List;

public class Kingdom {
   private final String material;
   private final boolean isPublic;
   private final String displayName;
   private final String name;
   private final Location prisonersSpawn;
   private final Location mafiaSpawn;
   private final Location kingSpawn;
   private final Location knightsSpawn;
   private final Location jailSpawn;
   private final Location farmerJoeLocation;
   private final Location minerGuardLocation;
   private final Location bertrudeLocation;
   private final Location lunchLadyLocation;
   private final Location royalTraderLocation;
   private final Location royalServantLocation;
   private final Location mopVillagerLocation;
   private final Location archerJohnLocation;
   private final Location prisonGuardLocation;
   private final Location littleJoesLocation;
   private final Location servantLocation;
   private final Location minerLocation;
   private final Location selfDefenseLocation;
   private final Location sewerVillagerLocation;
   private final Location royalVillagerLocation;
   private final Location mafiaRecruiter;
   private final Location mineEntranceLoc1;
   private final Location mineEntranceLoc2;
   private final Location mineExitLoc1;
   private final Location mineExitLoc2;
   private final Location castle1;
   private final Location castle2;
   private final Location outside1;
   private final Location outside2;
   private final Location prison1;
   private final Location prison2;
   private final Location bm1;
   private final Location bm2;
   private final Location prisonLightPowerBlock;
   private final Location blackMarketInsidePrisoner;
   private final Location blackMarketExitPrisoner;
   private final List<Location> cells;
   private final  Location bmSafe;
   private final Location prisonGuardSpawn;
   private final Location bmPrison1;
   private final Location bmPrison2;
   private final Location bmPrisonTrader;
   private final Location raidSpawn;

   public Kingdom(
           String material,
           boolean isPublic,
           String displayName,
           String name,
           Location prisonersSpawn,
           Location mafiaSpawn,
           Location kingSpawn,
           Location knightsSpawn,
           Location jailSpawn,
           Location farmerJoeLocation,
           Location minerGuardLocation,
           Location bertrudeLocation,
           Location lunchLadyLocation,
           Location royalTraderLocation,
           Location royalServantLocation,
           Location mopVillagerLocation,
           Location archerJohnLocation,
           Location prisonGuardLocation,
           Location littleJoesLocation,
           Location servantLocation,
           Location minerLocation,
           Location selfDefenseLocation,
           Location sewerVillagerLocation,
           Location royalVillagerLocation, Location mafiaRecruiter,
           Location mineEntranceLoc1,
           Location mineEntranceLoc2,
           Location mineExitLoc1,
           Location mineExitLoc2,
           Location castle1,
           Location castle2,
           Location outside1,
           Location outside2,
           Location prison1,
           Location prison2,
           Location bm1,
           Location bm2,
           Location prisonLightPowerBlock, Location blackMarketInsidePrisoner, Location blackMarketExitPrisoner, List<Location> cells, Location bmSafe, Location prisonGuardSpawn, Location bmPrison1, Location bmPrison2, Location bmPrisonTrader, Location raidSpawn
   ) {
      this.material = material;
      this.isPublic = isPublic;
      this.displayName = displayName;
      this.name = name;
      this.prisonersSpawn = prisonersSpawn;
      this.mafiaSpawn = mafiaSpawn;
      this.kingSpawn = kingSpawn;
      this.knightsSpawn = knightsSpawn;
      this.jailSpawn = jailSpawn;
      this.farmerJoeLocation = farmerJoeLocation;
      this.minerGuardLocation = minerGuardLocation;
      this.bertrudeLocation = bertrudeLocation;
      this.lunchLadyLocation = lunchLadyLocation;
      this.royalTraderLocation = royalTraderLocation;
      this.royalServantLocation = royalServantLocation;
      this.mopVillagerLocation = mopVillagerLocation;
      this.archerJohnLocation = archerJohnLocation;
      this.prisonGuardLocation = prisonGuardLocation;
      this.littleJoesLocation = littleJoesLocation;
      this.servantLocation = servantLocation;
      this.minerLocation = minerLocation;
      this.selfDefenseLocation = selfDefenseLocation;
      this.sewerVillagerLocation = sewerVillagerLocation;
      this.royalVillagerLocation = royalVillagerLocation;
       this.mafiaRecruiter = mafiaRecruiter;
       this.mineEntranceLoc1 = mineEntranceLoc1;
      this.mineEntranceLoc2 = mineEntranceLoc2;
      this.mineExitLoc1 = mineExitLoc1;
      this.mineExitLoc2 = mineExitLoc2;
      this.castle1 = castle1;
      this.castle2 = castle2;
      this.outside1 = outside1;
      this.outside2 = outside2;
      this.prison1 = prison1;
      this.prison2 = prison2;
      this.bm1 = bm1;
      this.bm2 = bm2;
      this.prisonLightPowerBlock = prisonLightPowerBlock;
       this.blackMarketInsidePrisoner = blackMarketInsidePrisoner;
       this.blackMarketExitPrisoner = blackMarketExitPrisoner;
       this.cells = cells;
       this.bmSafe = bmSafe;
       this.prisonGuardSpawn = prisonGuardSpawn;
       this.bmPrison1 = bmPrison1;
       this.bmPrison2 = bmPrison2;
       this.bmPrisonTrader = bmPrisonTrader;
       this.raidSpawn = raidSpawn;
   }

   public String getMaterial() {
      return this.material;
   }

   public boolean isPublic() {
      return this.isPublic;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public String getName() {
      return this.name;
   }

   public Location getSpawn() {
      return this.prisonersSpawn;
   }

   public Location getMafiaSpawn() {
      return this.mafiaSpawn;
   }

   public Location getKingSpawn() {
      return this.kingSpawn;
   }

   public Location getKnightsSpawn() {
      return this.knightsSpawn;
   }

   public Location getJailSpawn() {
      return this.jailSpawn;
   }

   public Location getFarmerJoeLocation() {
      return this.farmerJoeLocation;
   }

   public Location getMinerGuardLocation() {
      return this.minerGuardLocation;
   }

   public Location getBertrudeLocation() {
      return this.bertrudeLocation;
   }

   public Location getLunchLadyLocation() {
      return this.lunchLadyLocation;
   }

   public Location getRoyalTraderLocation() {
      return this.royalTraderLocation;
   }

   public Location getRoyalServantLocation() {
      return this.royalServantLocation;
   }

   public Location getMopVillagerLocation() {
      return this.mopVillagerLocation;
   }

   public Location getArcherJohnLocation() {
      return this.archerJohnLocation;
   }

   public Location getPrisonGuardLocation() {
      return this.prisonGuardLocation;
   }

   public Location getLittleJoesLocation() {
      return this.littleJoesLocation;
   }

   public Location getServantLocation() {
      return this.servantLocation;
   }

   public Location getMinerLocation() {
      return this.minerLocation;
   }

   public Location getSelfDefenseLocation() {
      return this.selfDefenseLocation;
   }

   public Location getSewerVillagerLocation() {
      return this.sewerVillagerLocation;
   }

   public Location getRoyalVillagerLocation() {
      return this.royalVillagerLocation;
   }

   public Location getMineEntranceLoc2() {
      return this.mineEntranceLoc2;
   }

   public Location getMineEntranceLoc1() {
      return this.mineEntranceLoc1;
   }

   public Location getMineExitLoc2() {
      return this.mineExitLoc2;
   }

   public Location getMineExitLoc1() {
      return this.mineExitLoc1;
   }

   public Location getCastle1() {
      return this.castle1;
   }

   public Location getCastle2() {
      return this.castle2;
   }

   public Location getOutside1() {
      return this.outside1;
   }

   public Location getOutside2() {
      return this.outside2;
   }

   public Location getPrison1() {
      return this.prison1;
   }

   public Location getPrison2() {
      return this.prison2;
   }

   public Location getBm1() {
      return this.bm1;
   }

   public Location getBm2() {
      return this.bm2;
   }

   public Location getPrisonLightPowerBlock() {
      return this.prisonLightPowerBlock;
   }

   public Location getMafiaRecruiter() {
      return mafiaRecruiter;
   }

   public Location getBlackMarketExitPrisoner() {
      return blackMarketExitPrisoner;
   }

   public Location getBlackMarketInsidePrisoner() {
      return blackMarketInsidePrisoner;
   }

   public List<Location> getCells() {
      return cells;
   }

   public Location getBmSafe() {
      return bmSafe;
   }

   public Location getPrisonGuardSpawn() {
      return prisonGuardSpawn;
   }

   public Location getBmPrison2() {
      return bmPrison2;
   }

   public Location getBmPrison1() {
      return bmPrison1;
   }

   public Location getBmPrisonTrader() {
      return bmPrisonTrader;
   }
   public Location getRaidSpawn() {
      return raidSpawn;
   }
}
