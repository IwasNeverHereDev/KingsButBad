package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.Advancements.AdvancementManager;
import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.DiscordUtils;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
   @EventHandler
   public void onPlayerDeathEvent(PlayerDeathEvent event) {
      event.getDrops().clear();
      BotManager.getInGameChatChannel().sendMessage(DiscordUtils.deformat(ChatColor.stripColor(event.getDeathMessage()))).queue();
      event.setDeathMessage(ChatColor.stripColor(event.getDeathMessage()));
      if (event.getPlayer().getKiller() != null && event.getPlayer().getKiller().getInventory().getItemInMainHand().getType().equals(Material.IRON_SHOVEL)) {
         event.setCancelled(true);
         event.getPlayer().getKiller().addPassenger(event.getPlayer());
         event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
         event.getPlayer().sendTitle(CreateText.addColors("<gold>Handcuffed!"),CreateText.addColors("<gray>By: "+event.getPlayer().getKiller().getName()), 0, 3, 0);
      }
      for(Entity entity : event.getPlayer().getPassengers())
         event.getPlayer().getPassengers().remove(entity);
      if(event.getPlayer().equals(KingsButBad.king) && event.getPlayer().getKiller() != null)
         AdvancementManager.giveAdvancement(event.getPlayer().getKiller(), "royalassassin");
      if (event.getPlayer().equals(KingsButBad.king)) {
         if (KingsButBad.king2 != null) {
            KingsButBad.king = KingsButBad.king2;
            KingsButBad.king2 = null;

            for (Player p : Bukkit.getOnlinePlayers()) {
               if (KingsButBad.roles.get(p) != Role.PEASANT) {
                  KingsButBad.roles.put(p, Role.PEASANT);
                  RoleManager.givePlayerRole(p);
               }
            }

            KingsButBad.invitations.clear();
            KingsButBad.roles.put(KingsButBad.king, Role.KING);
            RoleManager.showKingMessages(KingsButBad.king, Role.KING.objective);
            RoleManager.givePlayerRole(KingsButBad.king);
            KingsButBad.kingGender = "King";
            KingsButBad.kingGender2 = "King";

            for (Player pe : Bukkit.getOnlinePlayers()) {
               pe.sendTitle(CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>KING " + KingsButBad.king.getName().toUpperCase()), ChatColor.GREEN + "is your new overlord!");
            }
         }

         KingsButBad.cooldown = 100;
      }
      event.setDeathMessage(ChatColor.GRAY + event.getDeathMessage());
      if (KingsButBad.roles.get(event.getPlayer()).equals(Role.CRIMINAl)) {
         if (!event.getPlayer().getInventory().contains(Material.PAPER)) {
            Bukkit.broadcastMessage(CreateText.addColors("<red>>> <b>Criminal " + event.getPlayer().getName() + "<gold> </b>has been successfully captured!"));
            KingsButBad.prisonTimer.put(event.getPlayer(), 2400);
            KingsButBad.roles.put(event.getPlayer(), Role.PRISONER);
            event.setCancelled(true);
            event.getPlayer().getInventory().clear();
            Keys.inPrison.set(event.getPlayer(), true);
            RoleManager.givePlayerRole(event.getPlayer());
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
