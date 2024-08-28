package kingsbutbad.kingsbutbad;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.Discord.DiscordEvents.ReactMessageEvent;
import kingsbutbad.kingsbutbad.Loaders.*;
import kingsbutbad.kingsbutbad.utils.Role;
import me.coralise.spigot.CustomBansPlus;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class KingsButBad extends JavaPlugin {
   public static KingsButBad pl;
   public static Player king = null;
   public static int raidCooldown = -1;
   public static boolean isRaidActive = false;
   public static List<Entity> raidEnemies = new ArrayList<>();
   public static BossBar raidBossbar = null;
   public static int raidStartedEnmeiesCount = 0;
   public static Player king2 = null;
   public static HashMap<Player, Role> roles = new HashMap<>();
   public static Boolean joesUnlocked = false;
   public static Boolean coalCompactor = false;
   public static HashMap<Player, Integer> prisonTimer = new HashMap<>();
   public static HashMap<Player, Integer> prisonQuota = new HashMap<>();
   public static HashMap<Player, Role> invitations = new HashMap<>();
   public static HashMap<Player, Location> datedLocations = new HashMap<>();
   public static LuckPerms api;
   public static Villager royalVillager;
   public static Villager sewerVillager;
   public static HashMap<Player, Player> bodyLink = new HashMap<>();
   public static Villager selfDefense;
   public static Boolean mineUnlocked = false;
   public static Villager farmerJoe;
   public static Villager minerGuard;
   public static Villager bertrude;
   public static Villager lunchLady;
   public static Villager royalTrader;
   public static Villager royalServant;
   public static Villager mopVillager;
   public static Villager archerJohn;
   public static Villager prisonGuard;
   public static Villager littleJoes;
   public static Villager mafiaRecruiter;
   public static Villager bmPrisonTrader;
   public static HashMap<Player, String> princeGender = new HashMap<>();
   public static Villager servant;
   public static Villager miner;
   public static String kingGender = "King";
   public static String kingGender2 = "King";
   public static int cooldown = 100;
   public static Player lastKing;
   public static Player lastKing2;
   public static HashMap<Player, Integer> currentZone = new HashMap<>();
   public static HashMap<Player, Integer> thirst = new HashMap<>();
   public static HashMap<Player, Boolean> soundWaves = new HashMap<>();
   public static boolean isInterocmEnabled = false;
   public static CustomBansPlus cbp;

   public void onEnable() {
      pl = this;
      api = LuckPermsProvider.get();
      cbp = me.coralise.spigot.CustomBansPlus.getInstance();
      LoadCraftingRecipes.init();
      LoadEvents.init();
      LoadCommands.init();
      LoadTabCompleters.init();
      BotManager.init();
      LoadDiscordCommands.init();
      LoadKingdoms.init();
      LoadTasks.init();
      NoNoWords.reload();
      RemoveAdvancements.init();
   }

   public void onDisable() {
      for (LivingEntity le : Bukkit.getWorld("world").getLivingEntities()) {
         if (le.getType().equals(EntityType.VILLAGER)) {
            le.remove();
         }
      }

      BotManager.getBot().shutdown();
   }

   public static boolean isInside(Player player, Location loc1, Location loc2) {
      double[] dim = new double[]{loc1.getX(), loc2.getX()};
      Arrays.sort(dim);
      if (!(player.getLocation().getX() > dim[1]) && !(player.getLocation().getX() < dim[0])) {
         dim[0] = loc1.getZ();
         dim[1] = loc2.getZ();
         Arrays.sort(dim);
         return !(player.getLocation().getZ() > dim[1]) && !(player.getLocation().getZ() < dim[0]);
      } else {
         return false;
      }
   }
}
