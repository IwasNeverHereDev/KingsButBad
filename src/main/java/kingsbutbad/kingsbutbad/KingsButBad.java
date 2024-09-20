package kingsbutbad.kingsbutbad;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.Loaders.*;
import kingsbutbad.kingsbutbad.WebServer.WebServerManager;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.Seasons;
import me.coralise.spigot.CustomBansPlus;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Server;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public final class KingsButBad extends JavaPlugin { // TODO: Clean up This File (KingsButBad.java)
   public static KingsButBad pl;
   public static Player king = null;
   public static int raidCooldown = -1;
   public static boolean isRaidActive = false;
   public static int taxesPerctage = 0;
   public static List<Entity> raidEnemies = new ArrayList<>();
   public static BossBar raidBossbar = null;
   public static List<Player> inRaid = new ArrayList<>();
   public static int raidStartedEnmeiesCount = 0;
   public static Player king2 = null;
   public static HashMap<Player, Role> roles = new HashMap<>();
   public static Boolean joesUnlocked = false;
   public static Boolean coalCompactor = false;
   public static HashMap<UUID, List<Role>> listOfKilledRoles = new HashMap<>();
   public static HashMap<Player, Float> prisonTimer = new HashMap<>();
   public static HashMap<Player, Integer> prisonQuota = new HashMap<>();
   public static HashMap<Player, Role> invitations = new HashMap<>();
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
   public static HashMap<Player, String> princePrefix = new HashMap<>();
   public static Villager servant;
   public static Villager miner;
   public static String kingPrefix = "King";
   public static String kingPrefix2 = "King";
   public static int cooldown = 100;
   public static UUID lastKing;
   public static UUID lastKing2;
   public static HashMap<Player, Integer> currentZone = new HashMap<>();
   public static HashMap<Player, Float> thirst = new HashMap<>();
   public static boolean isIntercomEnabled = false;
   public static CustomBansPlus cbp;
   public static Server server;
   public static Instant startTime;
   public static Seasons currentSeason;
   public static int year = 0;

   public void onEnable() {
      currentSeason = Seasons.SUMMER;
      startTime = Instant.now();
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
      LoadMisc.init();
      NoNoWords.reload();
      WebServerManager.startWebServer();
      BotManager.getInGameChatChannel().sendMessage(BotManager.getStartRole().getAsMention() + " | Server is Starting...").queue();
      sendStartUpTime();
      startTime = Instant.now();
   }

   public void onDisable() {
      BotManager.getBot().shutdown();
      WebServerManager.stopWebServer();
      BotManager.getInGameChatChannel().sendMessage(BotManager.getStopRole().getAsMention() + " | Server is Stopping...").queue();
   }
   private void sendStartUpTime(){
      Duration res = Duration.between(startTime, Instant.now());

      long nanos = res.toNanos();
      long millis = res.toMillis();
      long seconds = res.getSeconds();

      Bukkit.getLogger().info(String.format(
              "\n\n      Startup Time: %d nanoseconds (%d milliseconds or %d seconds)\n\n",
              nanos, millis, seconds
      ));
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
