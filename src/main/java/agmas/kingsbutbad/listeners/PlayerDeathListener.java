package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.Discord.BotManager;
import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.keys.Keys;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.DiscordUtils;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerDeathListener implements Listener {
   @EventHandler
   public void onPlayerDeathEvent(PlayerDeathEvent event) {
      BotManager.getInGameChatChannel().sendMessage(DiscordUtils.deformat(event.getDeathMessage())).queue();
      if (event.getPlayer().equals(KingsButBad.king)) {
         if (event.getPlayer().getKiller() != null && !RoleManager.isKingAtAll(event.getPlayer())) {
            KingsButBad.king = null;
            KingsButBad.king2 = null;

            for (Player p : Bukkit.getOnlinePlayers()) {
               if (KingsButBad.roles.get(p) != Role.PEASANT) {
                  KingsButBad.roles.put(p, Role.PEASANT);
                  RoleManager.givePlayerRole(p);
               }
            }

            Player px = event.getPlayer().getKiller();
            KingsButBad.invitations.clear();
            KingsButBad.king = px;
            KingsButBad.roles.put(px, Role.KING);
            RoleManager.showKingMessages(px, Role.KING.objective);
            RoleManager.givePlayerRole(px);
            KingsButBad.kingGender = "King";

            for (Player pe : Bukkit.getOnlinePlayers()) {
               pe.sendTitle(CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>KING " + px.getName().toUpperCase()), ChatColor.GREEN + "is your new overlord!");
            }
         }

         KingsButBad.cooldown = 100;
      }

      event.getDrops().clear();
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
