package agmas.kingsbutbad.listeners;

import java.util.ArrayList;

import agmas.kingsbutbad.Kingdom.KingdomsLoader;
import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.keys.Keys;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Role;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteractListener implements Listener {
   @EventHandler
   public void onPlayerInteractEvent(PlayerInteractEvent event) {
      if(event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.CAULDRON)){
         Player p = event.getPlayer();
         if(KingsButBad.roles.getOrDefault(p, Role.PEASANT) == Role.PRISONER || KingsButBad.roles.getOrDefault(p, Role.PEASANT) == Role.PEASANT || KingsButBad.roles.getOrDefault(p, Role.PEASANT) == Role.CRIMINAl || KingsButBad.roles.getOrDefault(p, Role.PEASANT) == Role.OUTLAW){
            p.teleport(KingdomsLoader.activeKingdom.getBlackMarketInsidePrisoner());
         }
         event.setCancelled(true);
         return;
      }
      if (event.getItem() != null) {
         if (event.getItem().getType().equals(Material.CLAY_BALL) && !event.getPlayer().isInsideVehicle() && !event.getPlayer().hasCooldown(Material.CLAY_BALL)
            )
          {
            event.getPlayer().setCooldown(Material.CLAY_BALL, 80);
            Horse horse = (Horse)event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.HORSE);
            horse.setCustomName(event.getPlayer().getName() + "'s horse");
            horse.addPassenger(event.getPlayer());
            horse.setTamed(true);
            horse.getInventory().setArmor(new ItemStack(Material.IRON_HORSE_ARMOR));
            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
         }

         if (event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Adrenaline Shot")) {
            event.getItem().setAmount(event.getItem().getAmount() - 1);
            event.getPlayer().addPotionEffect(PotionEffectType.LUCK.createEffect(400, 0));
         }
      }

      if (event.getClickedBlock() != null) {
         ArrayList<Material> untouchables = new ArrayList<Material>() {
         };
         untouchables.add(Material.SWEET_BERRY_BUSH);
         untouchables.add(Material.SPRUCE_TRAPDOOR);
         untouchables.add(Material.DARK_OAK_DOOR);
         untouchables.add(Material.SPRUCE_DOOR);
         untouchables.add(Material.SPRUCE_FENCE_GATE);
         untouchables.add(Material.DECORATED_POT);
         untouchables.add(Material.FLOWER_POT);
         if (event.getPlayer().getGameMode().equals(GameMode.ADVENTURE) && untouchables.contains(event.getClickedBlock().getType())) {
            event.setCancelled(true);
         }

         if (event.getClickedBlock().getType().equals(Material.BARREL) && event.getPlayer().getGameMode().equals(GameMode.ADVENTURE)) {
            event.setCancelled(true);
         }

         if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.FARMLAND) {
            event.setCancelled(true);
         }

         if (event.getClickedBlock().getType().equals(Material.IRON_DOOR)) {
            BlockState state = event.getClickedBlock().getState();
            Door openable = (Door)state.getBlockData();
            if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE) && event.getPlayer().isSneaking()) {
               event.setCancelled(true);
               if (!openable.isOpen()) {
                  event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0F, 1.0F);
                  openable.setOpen(true);
               } else {
                  openable.setOpen(false);
                  event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_IRON_DOOR_CLOSE, 1.0F, 1.0F);
               }

               state.setBlockData(openable);
               state.update();
            }

            if (event.getItem() != null
               && event.getItem().getItemMeta() != null
               && event.getItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Keycard")) {
               event.setCancelled(true);
               if (!openable.isOpen()) {
                  event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_IRON_DOOR_OPEN, 1.0F, 1.0F);
                  openable.setOpen(true);
               } else {
                  openable.setOpen(false);
                  event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_IRON_DOOR_CLOSE, 1.0F, 1.0F);
               }

               state.setBlockData(openable);
               state.update();
            }
         }
      }
   }
}
