package kingsbutbad.kingsbutbad.Kingdom;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.tasks.MiscTask;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

public class KingdomsLoader {
   public static Kingdom activeKingdom;

   public KingdomsLoader(Kingdom kingdom, boolean isReload) {
      activeKingdom = kingdom;
      if(!isReload) {
         for (Player p : Bukkit.getOnlinePlayers()) {
            if (Keys.isBuilderMode.get(p, false)) {
               p.sendMessage(CreateText.addColors("<gray>That kingdom has been swapped to " + kingdom.getDisplayName() + "<gray> Kingdom! (You haven't been teleported for you are in Builder mode!)"));
               continue;
            }
            p.sendMessage(CreateText.addColors("\n<gray>After the long voyage you have arrived at <white>" + kingdom.getDisplayName() + "<gray> Kingdom!\n"));
            switch (KingsButBad.roles.getOrDefault(p, Role.PEASANT)) {
               case KING:
               case PRINCE:
               case BODYGUARD:
                  this.tp(p, kingdom.getKingSpawn());
                  break;
               case KNIGHT:
                  this.tp(p, kingdom.getKnightsSpawn());
                  break;
               case PEASANT:
               case SERVANT:
                  this.tp(p, kingdom.getSpawn());
                  break;
               case CRIMINAL:
                  this.tp(p, kingdom.getMafiaSpawn());
                  break;
               case PRISONER:
                  this.tp(p, MiscTask.cells.get(0));
               case PRISON_GUARD:
                  this.tp(p, kingdom.getPrisonGuardSpawn());
               case OUTLAW:
                  this.tp(p, kingdom.getMafiaSpawn());
            }
         }
      }

      setupVillagers(kingdom);
   }

   public static void setActiveKingdomByName(String kingdomName) {
      for (Kingdom kingdom : KingdomsReader.kingdomsList) {
         if (kingdom.getName().equalsIgnoreCase(kingdomName)) {
            new KingdomsLoader(kingdom, false);
            return;
         }
      }

      throw new IllegalArgumentException("Kingdom with name " + kingdomName + " not found.");
   }

   private void tp(Player p, Location loc) {
      if (loc.getWorld() == null) {
         p.sendMessage(CreateText.addColors("<red>Sorry, I couldn't tp you! (Null World) Pls Contact _Aquaotter_."));
      } else {
         p.teleport(loc);
      }
   }

   @SuppressWarnings("deprecation")
   private static Villager createVillager(Location location, String name, Profession profession) {
      Villager villager = (Villager)getWorld().spawnEntity(location, EntityType.VILLAGER);
      villager.setCustomName(CreateText.addColors(name));
      villager.setCustomNameVisible(true);
      villager.setInvulnerable(true);
      villager.setPersistent(true);
      villager.setAI(false);
      return villager;
   }

   private static Villager createVillager(Location location, String name) {
      return createVillager(location, name, Profession.NONE);
   }
   private static World getWorld(){
      World result = Bukkit.getWorld("world");
      if(result == null) return Bukkit.getWorlds().get(0);
      return result;
   }

   public static void setupVillagers(Kingdom kingdom) {
      World world = Bukkit.getWorld("world");
      if(world == null) return;
      for (Entity entity : world.getEntities()) {
         if (entity instanceof Villager) {
            entity.remove();
         }
      }

      KingsButBad.royalVillager = createVillager(kingdom.getRoyalVillagerLocation(), "<gradient:#FFFF52:#FFBA52>Royal Villager");
      KingsButBad.farmerJoe = createVillager(kingdom.getFarmerJoeLocation(), "<blue>Farmer Joe");
      KingsButBad.bertrude = createVillager(kingdom.getBertrudeLocation(), "bertrude");
      KingsButBad.sewerVillager = createVillager(kingdom.getSewerVillagerLocation(), "<gray>Shady Slim", Profession.TOOLSMITH);
      KingsButBad.selfDefense = createVillager(kingdom.getSelfDefenseLocation(), "<red>Defender Jim", Profession.LEATHERWORKER);
      KingsButBad.minerGuard = createVillager(kingdom.getMinerGuardLocation(), "<gray>Miner");
      KingsButBad.lunchLady = createVillager(kingdom.getLunchLadyLocation(), "<gold>Lunch Lady");
      KingsButBad.royalTrader = createVillager(kingdom.getRoyalTraderLocation(), "<gold>Outlaw Trader");
      KingsButBad.mopVillager = createVillager(kingdom.getMopVillagerLocation(), "<yellow>Janitor");
      KingsButBad.archerJohn = createVillager(kingdom.getArcherJohnLocation(), "<green>archer johnm");
      KingsButBad.prisonGuard = createVillager(kingdom.getPrisonGuardLocation(), "<blue>Prison Guard");
      KingsButBad.royalServant = createVillager(kingdom.getRoyalServantLocation(), "<gold>Royal Servant");
      KingsButBad.littleJoes = createVillager(kingdom.getLittleJoesLocation(), "<blue>Little Joes");
      KingsButBad.miner = createVillager(kingdom.getMinerLocation(), "<gold>Miner Joseph");
      KingsButBad.servant = createVillager(kingdom.getServantLocation(), "<gray>Servant Application");
      KingsButBad.mafiaRecruiter = createVillager(kingdom.getMafiaRecruiter(), "<gold>Outlaw Recruiter");
      KingsButBad.bmPrisonTrader = createVillager(kingdom.getBmPrisonTrader(), "<gold>Prison Trader");
   }
}
