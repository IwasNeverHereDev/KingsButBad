package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.FormatUtils;
import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("deprecation")
public class PlayerInteractListener implements Listener {
   @EventHandler
   public void onPlayerInteractEvent(PlayerInteractEvent event) {
      if(event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.CAULDRON)){
         Player p = event.getPlayer();
         if(KingsButBad.roles.getOrDefault(p, Role.PEASANT) == Role.PRISONER) {
            p.teleport(KingdomsLoader.activeKingdom.getBlackMarketInsidePrisoner());
            event.getPlayer().sendTitle("", CreateText.addColors("<gray>=-= PrisonMarket =-="), 0, 3,0);
         }
         event.setCancelled(true);
         return;
      }
      if (event.getItem() != null) {
         if (event.getItem().getType().equals(Material.CLAY_BALL) && !event.getPlayer().isInsideVehicle() && !event.getPlayer().hasCooldown(Material.CLAY_BALL)
            )
          {
             Location loc = event.getPlayer().getLocation();

             for (int x = -1; x <= 1; x++) {
                for (int y = 0; y <= 3; y++) { // Check only the player's level (y=0) and one block above (y=1)
                   for (int z = -1; z <= 1; z++) {
                      Block block = loc.clone().add(x, y, z).getBlock();
                      if (!block.getType().equals(Material.AIR)) {
                         event.getPlayer().sendMessage(CreateText.addColors("<red>Sorry, try this in a more open area"));
                         return;
                      }
                   }
                }
             }
             if(event.getPlayer().getLocation().set(0, -1, 0).getBlock().getType() == Material.AIR){
                event.getPlayer().sendMessage(CreateText.addColors("<red>Sorry, try this in a more open area"));
                return;
             }
             event.getPlayer().setCooldown(Material.CLAY_BALL, 80);
            Horse horse = (Horse)event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.HORSE);
            horse.setCustomName(event.getPlayer().getName() + "'s horse");
            horse.addPassenger(event.getPlayer());
            horse.setTamed(true);
            horse.setJumpStrength(0);
            horse.getInventory().setArmor(new ItemStack(Material.IRON_HORSE_ARMOR));
            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
         }

         if (event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Adrenaline Shot")) {
            event.getItem().setAmount(event.getItem().getAmount() - 1);
            event.getPlayer().setCooldown(event.getItem().getType(), 20);
            int amount = 0;
            if(event.getPlayer().hasPotionEffect(PotionEffectType.LUCK))
               amount = event.getPlayer().getPotionEffect(PotionEffectType.LUCK).getDuration();
            if(amount >= 20 * 60 * 3){
               event.setCancelled(true);
               event.getPlayer().damage(10);
               event.getPlayer().sendTitle("", CreateText.addColors("<red>Overdosed..."), 0, 5, 0);
               event.getPlayer().addPotionEffect(PotionEffectType.SLOW.createEffect(amount/5, 0));
               return;
            }
            event.getPlayer().addPotionEffect(PotionEffectType.LUCK.createEffect(amount+(20 * 20), 0));
         }
      }

      if (event.getClickedBlock() != null) {
         if(event.getClickedBlock().getType().equals(Material.WATER)){
             event.getPlayer().getInventory().getItemInMainHand();
             if(event.getPlayer().getItemInHand().getType().equals(Material.GLASS_BOTTLE)){
               event.setCancelled(true);
               event.getPlayer().getInventory().remove(new ItemStack(Material.POTION, 1));
               ItemStack bottle = new ItemStack(Material.GLASS_BOTTLE, 1);
               PotionMeta potionMeta = (PotionMeta) bottle.getItemMeta();
               potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 3, 1), false);
               bottle.setItemMeta(potionMeta);
               event.getPlayer().getInventory().addItem(bottle);
            }
         }
         if (event.getClickedBlock().getType() == Material.END_PORTAL_FRAME) {
            event.setCancelled(true);
            if(!KingsButBad.roles.getOrDefault(event.getPlayer(), Role.PEASANT).isPowerful){
               event.getPlayer().sendMessage(CreateText.addColors("<red>Only Kingdom roles can use this! <gray>(<white>Knight,King,Prince,etc..<gray>)"));
               return;
            }
            Player player = event.getPlayer();

            if (KingsButBad.raidCooldown > 0) {
               player.sendMessage(CreateText.addColors("<red>You can't start another Raid! <gray>(<white>" + FormatUtils.parseTicksToTime(KingsButBad.raidCooldown) + "<gray>)"));
               return;
            }

            if (KingsButBad.isRaidActive) {
               player.sendMessage(CreateText.addColors("<red>You can't start another Raid! <gray>(<white>Raid is Active<gray>)"));
               return;
            }

            Bukkit.broadcastMessage(CreateText.addColors("<red>Raid is Starting! <gray>(<white>Started by " + player.getName() + "!<gray>)"));
            KingsButBad.isRaidActive = true;

            Random random = new Random();
            int totalRaiders = 10 + random.nextInt(41);
            KingsButBad.raidStartedEnmeiesCount = totalRaiders + 1;

            Bukkit.broadcastMessage(CreateText.addColors("<gray>This raid has a total of <white>" + totalRaiders + " Raiders<gray>! (<white>Spawning...<gray>)"));

            for (int i = 0; i < totalRaiders; i++) {
               Bukkit.getScheduler().scheduleSyncDelayedTask(KingsButBad.pl, () -> {
                  // Spawn the raider entity
                  Entity raider = KingdomsLoader.activeKingdom.getRaidSpawn().getWorld().spawnEntity(KingdomsLoader.activeKingdom.getRaidSpawn(), EntityType.WITHER_SKELETON);
                  raider.setGlowing(true);
                  raider.setCustomName(CreateText.addColors("<gold>Raider " + (KingsButBad.raidEnemies.size() + 1)));
                  KingsButBad.raidEnemies.add(raider);

                  // Target the nearest player
                  if (raider instanceof Monster) {  // Ensure the entity is a type that can target players (e.g., Monster)
                     Monster monster = (Monster) raider;
                     Player nearestPlayer = getNearestPlayer(monster);
                     if (nearestPlayer != null) {
                        monster.setTarget(nearestPlayer);
                     }
                  }
               }, i * 5L);
            }
            return;
         }
         if(event.getClickedBlock().getType().equals(Material.BLACK_CANDLE) && event.getAction().isLeftClick()){
            if(KingsButBad.roles.getOrDefault(event.getPlayer(), Role.PEASANT) == Role.KING || KingsButBad.roles.getOrDefault(event.getPlayer(), Role.PEASANT) == Role.PRINCE){
               KingsButBad.isInterocmEnabled = !KingsButBad.isInterocmEnabled;
               event.getPlayer().sendMessage(CreateText.addColors("<green>The Intercom is now "+ (KingsButBad.isInterocmEnabled ? "Enabled" : "Disabled") + "!"));
               event.setCancelled(true);
               return;
            }
         }
         ArrayList<Material> untouchables = new ArrayList<>() {
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
   private Player getNearestPlayer(Monster monster) {
      Player nearestPlayer = null;
      double nearestDistance = Double.MAX_VALUE;

      for (Player player : Bukkit.getOnlinePlayers()) {
         if(!player.getGameMode().equals(GameMode.ADVENTURE)) continue;
         if(Keys.vanish.get(player, false)) continue;
         double distance = player.getLocation().distance(monster.getLocation());
         if (distance < nearestDistance) {
            nearestDistance = distance;
            nearestPlayer = player;
         }
      }
      return nearestPlayer;
   }

}
