package kingsbutbad.kingsbutbad.utils;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.NoNoWords;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.Items.RoleItems;
import kingsbutbad.kingsbutbad.utils.Items.Roles.*;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class RoleManager {
   public static Boolean isKingAtAll(Player p) {
      return KingsButBad.king == p || KingsButBad.king2 == p;
   }

   public static String getKingGender(Player p) {
      if (KingsButBad.king == p) {
         return KingsButBad.kingPrefix;
      } else {
         return KingsButBad.king2 == p ? KingsButBad.kingPrefix2 : "ERROR";
      }
   }

   public static void setKingPrefix(Player p, String toset) {
      if (KingsButBad.king == p) {
         if(!NoNoWords.isClean(toset)){
            p.sendMessage(CreateText.addColors("<red>You can't not set your Prefix a Filtered Word!"));
            return;
         }
         if(toset.length() >= 20){
            p.sendMessage(CreateText.addColors("<red>Sorry, Your prefix must be less then 15!"));
            return;
         }
         if(toset.contains("&k")){
            p.sendMessage(CreateText.addColors("<red>Sorry, You can't us Obstructed in your prefix!"));
            return;
         }
         List<String> listOfCommonRanks = new ArrayList<>();
         listOfCommonRanks.add("Builder");
         listOfCommonRanks.add("Owner");
         listOfCommonRanks.add("Admin");
         listOfCommonRanks.add("Mod");
         listOfCommonRanks.add("Helper");
         listOfCommonRanks.add("Booster");
         listOfCommonRanks.add("Support");
         listOfCommonRanks.addAll(Arrays.stream(Role.values())
                 .map(Role::name)
                 .collect(Collectors.toList()));
         for(String noRanks :listOfCommonRanks) {
            if (noRanks.toUpperCase().contains("KING")) continue;
            if (toset.toUpperCase().contains(noRanks.toUpperCase())) {
               p.sendMessage(CreateText.addColors("<red>Sorry, Your prefix can't contain Common Ranks!"));
               return;
            }
         }
         KingsButBad.kingPrefix = CreateText.convertAmpersandToMiniMessage(toset);
      }

      if (KingsButBad.king2 == p) {
         if(!NoNoWords.isClean(toset)){
            p.sendMessage(CreateText.addColors("<red>You can't not set your Prefix a Filtered Word!"));
            return;
         }
         if(toset.length() >= 20){
            p.sendMessage(CreateText.addColors("<red>Sorry, Your prefix must be less then 15!"));
            return;
         }
         if(toset.contains("&k")){
            p.sendMessage(CreateText.addColors("<red>Sorry, You can't us Obstructed in your prefix!"));
            return;
         }
         List<String> listOfCommonRanks = new ArrayList<>();
         listOfCommonRanks.add("Builder");
         listOfCommonRanks.add("Owner");
         listOfCommonRanks.add("Admin");
         listOfCommonRanks.add("Mod");
         listOfCommonRanks.add("Helper");
         listOfCommonRanks.add("Booster");
         listOfCommonRanks.add("Support");
         listOfCommonRanks.addAll(Arrays.stream(Role.values())
                 .map(Role::name)
                 .collect(Collectors.toList()));
         for(String noRanks :listOfCommonRanks) {
            if (noRanks.toUpperCase().contains("KING")) continue;
            if (toset.toUpperCase().contains(noRanks.toUpperCase())) {
               p.sendMessage(CreateText.addColors("<red>Sorry, Your prefix can't contain Common Ranks!"));
               return;
            }
         }
         KingsButBad.kingPrefix2 = CreateText.convertAmpersandToMiniMessage(toset);
      }
   }
   public static void showKingMessages(Player p, String reason) {
      p.sendTitle(
         ChatColor.GREEN
            + "YOU ARE "
            + LegacyComponentSerializer.legacySection()
               .serialize(MiniMessage.miniMessage().deserialize("<gradient:#FFFF52:#FFBA52><b>THE KING!<b></gradient>")),
         reason
      );
      p.sendMessage(
         MiniMessage.miniMessage()
            .deserialize(
               "<green><b>You're </green><gradient:#FFFF52:#FFBA52><b>The king!<b></gradient><#AEAEAE> Read <red><b>/King Help</b></red><#AEAEAE> for a small tutorial!<reset>"
            )
      );
   }

   public static void givePlayerRole(Player p) {
      TabUtils.reload();
      KingsButBad.roles.putIfAbsent(p, Role.PEASANT);
      if (p.getWorldBorder() != null)
         p.setWorldBorder(null);


      if(!KingsButBad.roles.get(p).equals(Role.OUTLAW))
         p.getInventory().clear();
      if (KingsButBad.roles.get(p).equals(Role.PRINCE)) {
         PrinceKit.giveKit(p);
         Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Monarchs").addPlayer(p);
         Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> p.teleport(KingdomsLoader.activeKingdom.getKingSpawn()), 10L);
      } else if (p != KingsButBad.king && p != KingsButBad.king2) {
         if (KingsButBad.roles.get(p) == Role.KNIGHT) {
            KnightKit.giveKit(p);
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> p.teleport(KingdomsLoader.activeKingdom.getKnightsSpawn()), 10L);
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("NRoyals").addPlayer(p);
         }

         if (KingsButBad.roles.get(p) == Role.PRISON_GUARD) {
            PrisonGuardKit.giveKit(p);
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> p.teleport(KingdomsLoader.activeKingdom.getPrisonGuardSpawn()), 10L);
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("NRoyals").addPlayer(p);
         }

         if (KingsButBad.roles.get(p) == Role.BODYGUARD) {
            BodyGuardKit.giveKit(p);
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> p.teleport(KingsButBad.bodyLink.get(p).getLocation()), 10L);
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("NRoyals").addPlayer(p);
         }

         if (KingsButBad.roles.get(p) == Role.PRISONER) {
            p.setCooldown(Material.TERRACOTTA, 80);
            if (!KingsButBad.prisonTimer.containsKey(p) || KingsButBad.prisonTimer.get(p).equals(0))
               KingsButBad.prisonTimer.put(p, 2400F);
            PrisonerKit.giveKit(p);
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> Cell.tpToRandomCell(p), 10L);
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
            if(Keys.activePact.get(p, Pacts.NONE.name()) == Pacts.SERVANT.name()){
               p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
               p.getInventory().addItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
            }
         }

         if (KingsButBad.roles.get(p) == Role.PEASANT || KingsButBad.roles.get(p) == Role.SERVANT) {
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Peasants").addPlayer(p);
            Keys.inPrison.remove(p);
            Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> p.teleport(KingdomsLoader.activeKingdom.getSpawn()), 10L);
            if(Keys.activePact.get(p, Pacts.NONE.name()) == Pacts.SERVANT.name()){
               p.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
               p.getInventory().addItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
            }
         }

         p.sendTitle(KingsButBad.roles.get(p).tag, KingsButBad.roles.get(p).objective);
      } else {
         if (p == KingsButBad.king) {
            KingsButBad.joesUnlocked = false;
            KingsButBad.coalCompactor = false;
            KingsButBad.mineUnlocked = false;

            for (LivingEntity le : Bukkit.getWorld("world").getLivingEntities())
               if (le.getType().equals(EntityType.ZOMBIE))
                  le.remove();
            p.getInventory().setHelmet(KingKit.getHelmet());
         } else {
            p.getInventory().setHelmet(KingKit.getOtherHelmet());
         }
         KingKit.giveKit(p);
         Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Monarchs").addPlayer(p);
         Bukkit.getScheduler().runTaskLater(KingsButBad.getPlugin(KingsButBad.class), () -> p.teleport(KingdomsLoader.activeKingdom.getKingSpawn()), 10L);
      }
   }
   public static boolean isSettable(ItemStack itemStack){
       return itemStack != null && !itemStack.isEmpty() && itemStack.getType() != Material.AIR;
   }
}
