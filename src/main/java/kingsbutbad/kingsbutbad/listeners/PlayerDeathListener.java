package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.Advancements.AdvancementManager;
import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.commands.Clans.ClansDB;
import kingsbutbad.kingsbutbad.keys.Key;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class PlayerDeathListener implements Listener { // TODO: Clean up This File (PlayerDeathListener.java)
   public static HashMap<UUID, Integer> axeKillCount = new HashMap<>();
   @EventHandler
   public void onPlayerDeathEvent(PlayerDeathEvent event) {
      Player p = event.getPlayer();
      Player killer = p.getKiller();
      checkIfAllGoodThingsComeToEnd(event);
      addRoleValue(p, false);
      p.eject();
      event.getDrops().clear();
       axeKillCount.remove(p.getUniqueId());
      if (killer != null) {
         if(Keys.activePact.get(killer, Pacts.NONE.name()) == Pacts.PRISON_GUARD.name())
            if(KingsButBad.roles.getOrDefault(p, Role.PEASANT) == Role.CRIMINAL || KingsButBad.roles.getOrDefault(p, Role.PEASANT) == Role.PRISONER)
               Keys.money.set(killer, Keys.money.get(p, 0.0) + 500.0);
         if(Keys.activePact.get(killer, Pacts.NONE.name()) == Pacts.OUTLAW.name()){
            Bukkit.getScheduler().runTaskLater(KingsButBad.pl, () -> {
               Bukkit.broadcastMessage(CreateText.addColors("<red>>> <b>Criminal " + p.getName() + "<red> </b>has been successfully captured!"));
               KingsButBad.prisonTimer.put(p, 2400F);
               KingsButBad.roles.put(p, Role.PRISONER);
               p.getInventory().clear();
               Keys.inPrison.set(p, true);
               RoleManager.givePlayerRole(p);
            }, 5);
         }
         p.getPassengers().clear();
         addRoleValue(killer, true);
         ClansDB.loadData(new File(ClansDB.getDataFolder(), "gangs.gz").getPath()).addExpToUsersGang(killer, 5);
         ItemStack weapon = killer.getInventory().getItemInMainHand();
         if(killer.isJumping() && p.getHealth() <= 3)
            if(KingsButBad.roles.getOrDefault(p, Role.PEASANT) == Role.OUTLAW && KingsButBad.roles.getOrDefault(killer, Role.PEASANT) == Role.KING)
               AdvancementManager.giveAdvancement(killer, "executioner");
         if (weapon.getType().equals(Material.STONE_AXE)) {
            axeKillCount.put(killer.getUniqueId(), axeKillCount.getOrDefault(killer.getUniqueId(), 0) + 1);
            if(axeKillCount.getOrDefault(killer.getUniqueId(), 0) >= 5)
               AdvancementManager.giveAdvancement(killer, "onemanarmy");
            KingsButBad.listOfKilledRoles
                    .computeIfAbsent(killer.getUniqueId(), k -> new ArrayList<>())
                    .add(KingsButBad.roles.getOrDefault(p, Role.PEASANT));
            if (KingsButBad.listOfKilledRoles.get(killer.getUniqueId()).containsAll(List.of(Role.values())))
               AdvancementManager.giveAdvancement(killer, "deathtoallroles");
         }
      }
      if(KingsButBad.roles.getOrDefault(p, Role.PEASANT).isPowerful)
         event.setDeathMessage(ChatColor.GOLD+ChatColor.stripColor(event.getDeathMessage()));
      else
         event.setDeathMessage(ChatColor.stripColor(event.getDeathMessage()));
      if (p.getKiller() != null && p.getKiller().getInventory().getItemInMainHand().getType().equals(Material.IRON_SHOVEL)) {
         if(p.getKiller().getPassengers().isEmpty() && !p.hasPotionEffect(PotionEffectType.UNLUCK) && p.isOnline())  {
            p.getKiller().setCooldown(Material.IRON_SHOVEL,20*8);
            event.setCancelled(true);
            p.getKiller().addPassenger(p);
            p.setHealth(p.getMaxHealth());
            p.sendTitle(CreateText.addColors("<gold>Handcuffed!"), CreateText.addColors("<gray>By: " + p.getKiller().getName()), 0, 20*3, 0);
            return;
         }else{
            p.getKiller().sendMessage(CreateText.addColors("<red>You can only handcuff one person at a time!"));
         }
      }
      if(p.equals(KingsButBad.king) && p.getKiller() != null) {
         AdvancementManager.giveAdvancement(p.getKiller(), "royalassassin");
         Role killerRole = KingsButBad.roles.getOrDefault(killer, Role.PEASANT);
         if(killerRole == Role.BODYGUARD)
            AdvancementManager.giveAdvancement(p.getKiller(), "backstaber");
         if(killerRole == Role.PRISONER)
            AdvancementManager.giveAdvancement(p.getKiller(), "losechains");
      }
      if (p.equals(KingsButBad.king)) {
         if (KingsButBad.king2 != null) {
            KingsButBad.king = KingsButBad.king2;
            KingsButBad.king2 = null;

            for (Player player : Bukkit.getOnlinePlayers()) {
               if(player.isInsideVehicle() && player.getVehicle() == null)
                  p.getVehicle().removePassenger(player);
               Role role = KingsButBad.roles.getOrDefault(player, Role.PEASANT);
               if(role.isPowerful){
                  KingsButBad.roles.put(p, Role.PEASANT);
                  RoleManager.givePlayerRole(player);
               }
            }

            KingsButBad.invitations.clear();
            KingsButBad.roles.put(KingsButBad.king, Role.KING);
            RoleManager.showKingMessages(KingsButBad.king, Role.KING.objective);
            RoleManager.givePlayerRole(KingsButBad.king);
            KingsButBad.taxesPerctage = 0;
            KingsButBad.kingPrefix = "King";
            KingsButBad.kingPrefix2 = "King";

            for (Player pe : Bukkit.getOnlinePlayers()) {
               pe.sendTitle(CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>KING " + KingsButBad.king.getName().toUpperCase()), ChatColor.GREEN + "is your new overlord!");
            }
         }

         KingsButBad.cooldown = 100;
      }
      event.setDeathMessage(ChatColor.GRAY + event.getDeathMessage());

         if (KingsButBad.roles.get(p).equals(Role.CRIMINAL)) {
            if(Keys.activePact.get(p, Pacts.NONE.name()) != Pacts.CRIMINAL.name()) {
               if (!p.getInventory().contains(Material.PAPER) || Keys.activePact.get(p, "") == Pacts.OUTLAW.name()) {
                  Bukkit.broadcastMessage(CreateText.addColors("<red>>> <b>Criminal " + p.getName() + "<gold> </b>has been successfully captured!"));
                  KingsButBad.prisonTimer.put(p, 2400F);
                  KingsButBad.roles.put(p, Role.PRISONER);
                  event.setCancelled(true);
                  p.getInventory().clear();
                  Keys.inPrison.set(p, true);
                  RoleManager.givePlayerRole(p);
                  return;
               } else {
                  KingsButBad.roles.put(p, Role.PEASANT);
                  Bukkit.broadcastMessage(
                          CreateText.addColors("<red>>> <b>Criminal " + p.getName() + "<gold> </b>used their get-out-of-jail-free card.")
                  );
               }
            }
            {
               KingsButBad.roles.put(p, Role.PEASANT);
               RoleManager.givePlayerRole(p);
               Bukkit.broadcastMessage(
                       CreateText.addColors("<red>>> <b>Criminal " + p.getName() + "<red> </b>used their pact.")
               );
            }
         }
         if (KingsButBad.roles.get(p).equals(Role.OUTLAW)) {
            if (Keys.activePact.get(p, Pacts.NONE.name()) != Pacts.CRIMINAL.name()) {
               if (!p.getInventory().contains(Material.PAPER) || Keys.activePact.get(p, "") == Pacts.OUTLAW.name()) {
                  Bukkit.broadcastMessage(CreateText.addColors("<gold>>> <b>Outlaw " + p.getName() + "<gold> </b>has been successfully captured!"));
                  KingsButBad.prisonTimer.put(p, 2400F);
                  KingsButBad.roles.put(p, Role.PRISONER);
                  event.setCancelled(true);
                  p.getInventory().clear();
                  Keys.inPrison.set(p, true);
                  RoleManager.givePlayerRole(p);
               } else {
                  KingsButBad.roles.put(p, Role.PEASANT);
                  Bukkit.broadcastMessage(
                          CreateText.addColors("<gold>>> <b>Outlaw " + p.getName() + "<gold> </b>used their get-out-of-jail-free card.")
                  );
               }
            }else{
               KingsButBad.roles.put(p, Role.PEASANT);
               RoleManager.givePlayerRole(p);
               Bukkit.broadcastMessage(
                       CreateText.addColors("<gold>>> <b>Outlaw " + p.getName() + "<gold> </b>used their pact.")
               );
            }
         }
      if(!Keys.vanish.get(p, false))
         BotManager.getInGameChatChannel().sendMessage(DiscordUtils.deformat(ChatColor.stripColor(event.getDeathMessage()))).queue();
      else {
         RoleManager.givePlayerRole(p);
         event.setCancelled(true);
         p.sendMessage(CreateText.addColors("<red>(Staff) You were given your role back due to vanish!"));
      }
   }
   private void addRoleValue(Player p, boolean isKill) {
      Role role = KingsButBad.roles.getOrDefault(p, Role.PEASANT);

      switch (role) {
         case OUTLAW -> addValue(p, Keys.OUTLAWkills, Keys.OUTLAWDeaths, isKill);
         case KING -> addValue(p, Keys.KINGkills, Keys.KINGDeaths, isKill);
         case PEASANT -> addValue(p, Keys.PEASANTkills, Keys.PEASANTDeaths, isKill);
         case CRIMINAL -> addValue(p, Keys.CRIMINALkills, Keys.CRIMINALDeaths, isKill);
         case SERVANT -> addValue(p, Keys.SERVANTkills, Keys.SERVANTDeaths, isKill);
         case BODYGUARD -> addValue(p, Keys.BODYGUARDkills, Keys.BODYGUARDDeaths, isKill);
         case PRINCE -> addValue(p, Keys.PRINCEkills, Keys.PRINCEDeaths, isKill);
         case PRISONER -> addValue(p, Keys.PRISONERkills, Keys.PRISONERDeaths, isKill);
         case PRISON_GUARD -> addValue(p, Keys.PRISON_GUARDkills, Keys.PRISON_GUARDDeaths, isKill);
         case KNIGHT -> addValue(p, Keys.KNIGHTkills, Keys.KNIGHTDeaths, isKill);
         default -> p.sendMessage(CreateText.addColors("<red>Your kills/deaths role stats have not increased! <gray>(<white>Contact Server Administrators<gray>)"));
      }
   }
   private void checkIfAllGoodThingsComeToEnd(PlayerDeathEvent event){
      if(event.getPlayer() == KingsButBad.king)
         if(KingsButBad.mineUnlocked && KingsButBad.joesUnlocked && KingsButBad.coalCompactor)
            AdvancementManager.giveAdvancement(event.getPlayer(), "allgoodthings");
   }

   private void addValue(Player p, Key key1, Key key2, boolean isKill) {
      if (key1 == null || key2 == null) {
         p.sendMessage(CreateText.addColors("<red>Error: Key objects are not properly initialized."));
         return;
      }
      if (isKill)
         key1.addDouble(p, 1.0);
      else
         key2.addDouble(p, 1.0);
   }
}
