package kingsbutbad.kingsbutbad.listeners;

import com.comphenix.protocol.PacketType;
import kingsbutbad.kingsbutbad.Advancements.AdvancementManager;
import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.commands.Clans.ClansDB;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.DiscordUtils;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class PlayerDeathListener implements Listener { // TODO: Clean up This File (PlayerDeathListener.java)
   @EventHandler
   public void onPlayerDeathEvent(PlayerDeathEvent event) {
      event.getPlayer().eject();
      event.getPlayer().getPassengers().clear();
      event.getDrops().clear();
      if(!Keys.vanish.get(event.getPlayer(), false))
         BotManager.getInGameChatChannel().sendMessage(DiscordUtils.deformat(ChatColor.stripColor(event.getDeathMessage()))).queue();
      else {
         RoleManager.givePlayerRole(event.getPlayer());
         event.setCancelled(true);
         event.getPlayer().sendMessage(CreateText.addColors("<red>(Staff) You were given your role back due to vanish!"));
         return;
      }
      Player killer = event.getPlayer().getKiller();
      if (killer != null) {
         ClansDB.loadData(new File(ClansDB.getDataFolder(), "gangs.gz").getPath()).addExpToUsersGang(killer, 5);
         ItemStack weapon = killer.getInventory().getItemInMainHand();
         if (weapon.getType().equals(Material.STONE_AXE)) {
            KingsButBad.listOfKilledRoles
                    .computeIfAbsent(killer.getUniqueId(), k -> new ArrayList<>())
                    .add(KingsButBad.roles.getOrDefault(event.getPlayer(), Role.PEASANT));
            if (KingsButBad.listOfKilledRoles.get(killer.getUniqueId()).containsAll(List.of(Role.values())))
               AdvancementManager.giveAdvancement(killer, "deathtoallroles");
         }
      }
      if(KingsButBad.roles.getOrDefault(event.getPlayer(), Role.PEASANT).isPowerful)
         event.setDeathMessage(ChatColor.GOLD+ChatColor.stripColor(event.getDeathMessage()));
      else
         event.setDeathMessage(ChatColor.stripColor(event.getDeathMessage()));
      if (event.getPlayer().getKiller() != null && event.getPlayer().getKiller().getInventory().getItemInMainHand().getType().equals(Material.IRON_SHOVEL)) {
         if(event.getPlayer().getKiller().getPassengers().isEmpty() && !event.getPlayer().hasPotionEffect(PotionEffectType.SLOW))  {
            Bukkit.getScheduler().scheduleSyncDelayedTask(KingsButBad.pl, () -> {
               if(event.getPlayer().getVehicle() instanceof Player && event.getPlayer().getKiller().getPassengers().contains(event.getPlayer())) {
                  event.getPlayer().getVehicle().removePassenger(event.getPlayer());
                  killer.sendMessage(CreateText.addColors("<red>Handcuffs rusted away!"));
                  event.getPlayer().sendMessage(CreateText.addColors("<green>Handcuffs rusted away! <gray>(<white>FREEDOM!!!<gray>)"));
                  event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*5,0));
               }
            }, 20*15);
            event.setCancelled(true);
            event.getPlayer().getKiller().addPassenger(event.getPlayer());
            event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
            event.getPlayer().sendTitle(CreateText.addColors("<gold>Handcuffed!"), CreateText.addColors("<gray>By: " + event.getPlayer().getKiller().getName()), 0, 3, 0);
            return;
         }else{
            event.getPlayer().getKiller().sendMessage(CreateText.addColors("<red>You can only handcuff one person at a time!"));
         }
      }
      if(event.getPlayer().equals(KingsButBad.king) && event.getPlayer().getKiller() != null)
         AdvancementManager.giveAdvancement(event.getPlayer().getKiller(), "royalassassin");
      if (event.getPlayer().equals(KingsButBad.king)) {
         if (KingsButBad.king2 != null) {
            KingsButBad.king = KingsButBad.king2;
            KingsButBad.king2 = null;

            for (Player p : Bukkit.getOnlinePlayers()) {
               if(p.isInsideVehicle() && p.getVehicle() == null)
                  p.getVehicle().removePassenger(p);
               Role role = KingsButBad.roles.getOrDefault(p, Role.PEASANT);
               if(role.isPowerful){
                  KingsButBad.roles.put(p, Role.PEASANT);
                  RoleManager.givePlayerRole(p);
               }
            }

            KingsButBad.invitations.clear();
            KingsButBad.roles.put(KingsButBad.king, Role.KING);
            RoleManager.showKingMessages(KingsButBad.king, Role.KING.objective);
            RoleManager.givePlayerRole(KingsButBad.king);
            KingsButBad.taxesPerctage = 0;
            KingsButBad.kingGender = "King";
            KingsButBad.kingGender2 = "King";

            for (Player pe : Bukkit.getOnlinePlayers()) {
               pe.sendTitle(CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>KING " + KingsButBad.king.getName().toUpperCase()), ChatColor.GREEN + "is your new overlord!");
            }
         }

         KingsButBad.cooldown = 100;
      }
      event.setDeathMessage(ChatColor.GRAY + event.getDeathMessage());
      if (KingsButBad.roles.get(event.getPlayer()).equals(Role.CRIMINAL)) {
         if (!event.getPlayer().getInventory().contains(Material.PAPER)) {
            Bukkit.broadcastMessage(CreateText.addColors("<red>>> <b>Criminal " + event.getPlayer().getName() + "<gold> </b>has been successfully captured!"));
            KingsButBad.prisonTimer.put(event.getPlayer(), 2400);
            KingsButBad.roles.put(event.getPlayer(), Role.PRISONER);
            event.setCancelled(true);
            event.getPlayer().getInventory().clear();
            Keys.inPrison.set(event.getPlayer(), true);
            RoleManager.givePlayerRole(event.getPlayer());
            return;
         } else {
            KingsButBad.roles.put(event.getPlayer(), Role.PEASANT);
            Bukkit.broadcastMessage(
               CreateText.addColors("<red>>> <b>Criminal " + event.getPlayer().getName() + "<gold> </b>used their get-out-of-jail-free card.")
            );
         }
      }
      if(KingsButBad.roles.get(event.getPlayer()).equals(Role.OUTLAW)){
         if (!event.getPlayer().getInventory().contains(Material.PAPER)) {
            Bukkit.broadcastMessage(CreateText.addColors("<gold>>> <b>Outlaw " + event.getPlayer().getName() + "<gold> </b>has been successfully captured!"));
            KingsButBad.prisonTimer.put(event.getPlayer(), 2400);
            KingsButBad.roles.put(event.getPlayer(), Role.PRISONER);
            event.setCancelled(true);
            event.getPlayer().getInventory().clear();
            Keys.inPrison.set(event.getPlayer(), true);
            RoleManager.givePlayerRole(event.getPlayer());
         } else {
            KingsButBad.roles.put(event.getPlayer(), Role.PEASANT);
            Bukkit.broadcastMessage(
                    CreateText.addColors("<gold>>> <b>Outlaw " + event.getPlayer().getName() + "<gold> </b>used their get-out-of-jail-free card.")
            );
         }
      }
   }
}
