package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.Advancements.AdvancementManager;
import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.Kingdom.Kingdom;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsReader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Key;
import kingsbutbad.kingsbutbad.keys.KeyTypes;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.tasks.MiscTask;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Item;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static kingsbutbad.kingsbutbad.utils.Item.createItem;
import static kingsbutbad.kingsbutbad.utils.RoleManager.isSettable;

@SuppressWarnings("deprecation")
public class InventoryClickListener implements Listener { // TODO: Clean up This File (InventoryClickListener.java)
   @EventHandler
   public void onPlayerQuit(InventoryClickEvent event) {
      if (event.getWhoClicked().hasCooldown(Material.FISHING_ROD)
         || event.getWhoClicked().hasCooldown(Material.WOODEN_HOE)
         || event.getWhoClicked().hasCooldown(Material.BONE)
         || event.getWhoClicked().hasCooldown(Material.STONE_PICKAXE)) {
         event.setCancelled(true);
      }
      if(event.getCurrentItem() == null || event.getCurrentItem().isEmpty()) return;

      if(event.getView().getTitle().equals(CreateText.addColors("<yellow>Builder Menu"))){
         event.setCancelled(true);
         String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
         Kingdom targetKingdom = null;
         for(Kingdom kingdom : KingdomsReader.kingdomsList)
            if(CreateText.addColors(kingdom.getDisplayName()).equals(itemName)) targetKingdom=kingdom;
         if(targetKingdom == null) return;
         event.getWhoClicked().teleport(targetKingdom.getSpawn());
         event.getWhoClicked().sendMessage(CreateText.addColors("<gray>You have selected and been teleported to "+targetKingdom.getDisplayName()+" <gray>Kingdom!"));
         BotManager.getBuilderChannel().sendMessage(event.getWhoClicked().getName() + " has selected and been teleported to "+ChatColor.stripColor(CreateText.addColors(targetKingdom.getDisplayName()))+" Kingdom!").queue();
      }
      if(event.getView().getTitle().equals(CreateText.addColors("<gold>KingsButBad Settings"))){
         Player p = (Player) event.getWhoClicked();
         Material type = event.getCurrentItem().getType();
         event.setCancelled(true);
         if(type.equals(Material.BLACK_CANDLE))
            changeSettings(p, Keys.isAutoShoutEnabled, "Auto Shout");
         if(type.equals(Material.NAME_TAG))
            changeSettings(p, Keys.SHORTEN_SHOUTMSG, "Shorten Shout Message");
         if(type.equals(Material.GOLD_INGOT))
            changeSettings(p, Keys.PING_NOISES, "Ping On Mentioned");
         if(type.equals(Material.COAL))
            changeSettings(p, Keys.showMineMessages, "Show Mine Messages");
         if(type.equals(Material.PAPER)){
            String chat;
            if(!Keys.selectedChat.get(event.getWhoClicked(), false))
               chat = "<white>Builder Chat<gray>";
            else
               chat = "<white>Staff Chat<gray>";
            event.getWhoClicked().sendMessage(CreateText.addColors("<gray>Settings Changed: <white>Your Selected Chat has been set to "+chat+" Shortcut!"));
            Keys.selectedChat.set(event.getWhoClicked(), !Keys.selectedChat.get(event.getWhoClicked(), false));
         }
         if(type.equals(Material.CLOCK))
            changeSettings(p, Keys.displayRoleStats, "Display Roles Playtime");
         if(type.equals(Material.BOOK))
            changeSettings(p, Keys.SHORTEN_ROLES_MSG, "Shorten Roles Messages");
         if(type.equals(Material.WRITABLE_BOOK))
            changeSettings(p, Keys.SHORTEN_RANKS_MSG, "Shorten Ranks Messages");
         PlayerJoinListener.updateTab((Player) event.getWhoClicked());
         Bukkit.dispatchCommand(event.getWhoClicked(), "kbbsettings");
         return;
      }
      if (event.getView().getTitle().equals(CreateText.addColors("<gold>Prisoner Trader"))) {
         event.setCancelled(true);
         if (!(event.getWhoClicked() instanceof Player p)) return;
         if (KingsButBad.roles.getOrDefault(p, Role.PEASANT) != Role.PRISONER) return;
         if (event.getCurrentItem() == null) return;

         int requiredCoalKey = 35;
         int requiredCoalTime = 5;
         int coalCount = 0;

         if(event.getCurrentItem().getType().equals(Material.COAL)) {
            for (ItemStack itemStack : p.getInventory().getContents()) {
               if (itemStack != null && itemStack.getType().equals(Material.COAL)) {
                  coalCount += itemStack.getAmount();
               }
            }

            if (coalCount >= requiredCoalTime) {
               int remainingCoal = requiredCoalTime;

               // Remove the required amount of coal from the player's inventory
               for (ItemStack itemStack : p.getInventory().getContents()) {
                  if (itemStack != null && itemStack.getType().equals(Material.COAL)) {
                     int stackAmount = itemStack.getAmount();

                     if (stackAmount > remainingCoal) {
                        itemStack.setAmount(stackAmount - remainingCoal);
                        remainingCoal = 0;
                     } else {
                        remainingCoal -= stackAmount;
                        p.getInventory().remove(itemStack);
                     }

                     if (remainingCoal <= 0) break;
                  }
               }
               KingsButBad.prisonTimer.put(p, KingsButBad.prisonTimer.getOrDefault(p, 0) - 25*20);
               if(KingsButBad.prisonTimer.getOrDefault(p, 0) < 0) KingsButBad.prisonTimer.remove(p);
               p.sendMessage(CreateText.addColors("<gray>You have purchased less time!"));
               AdvancementManager.giveAdvancement(p, "undertable");
               return;

            } else {
               p.sendMessage(CreateText.addColors("<gray>You need <white>5 Coal<gray> to remove some of your time!"));
            }
            return;
         }

         if(event.getCurrentItem().getType().equals(Material.PAPER)) {
            for (ItemStack itemStack : p.getInventory().getContents()) {
               if (itemStack != null && itemStack.getType().equals(Material.COAL)) {
                  coalCount += itemStack.getAmount();
               }
            }

            if (coalCount >= requiredCoalKey) {
               int remainingCoal = requiredCoalKey;

               // Remove the required amount of coal from the player's inventory
               for (ItemStack itemStack : p.getInventory().getContents()) {
                  if (itemStack != null && itemStack.getType().equals(Material.COAL)) {
                     int stackAmount = itemStack.getAmount();

                     if (stackAmount > remainingCoal) {
                        itemStack.setAmount(stackAmount - remainingCoal);
                        remainingCoal = 0;
                     } else {
                        remainingCoal -= stackAmount;
                        p.getInventory().remove(itemStack);
                     }

                     if (remainingCoal <= 0) break;
                  }
               }
               ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
               ItemMeta cardm = card.getItemMeta();
               cardm.setDisplayName(ChatColor.BLUE + "Keycard");
               card.setItemMeta(cardm);
               p.getInventory().addItem(card);
               p.sendMessage(CreateText.addColors("<gray>You have purchased a Make-Shift Key!"));
               AdvancementManager.giveAdvancement(p, "undertable");
            } else {
               p.sendMessage(CreateText.addColors("<gray>You need <white>35 Coal<gray> to buy a Make-Shift Key!"));
            }
            return;
         }
         return;
      }
      if(event.getCurrentItem().getType().equals(Material.ORANGE_DYE)) {
         event.setCancelled(true);
         Player p = (Player) event.getWhoClicked();
         if (KingsButBad.roles.get(p) == Role.PEASANT) {
            Bukkit.broadcastMessage(CreateText.addColors("<gold>" + p.getName() + " has became a Outlaw!"));
            p.sendMessage(CreateText.addColors("<gray>Click Red Dye again to refresh kit!"));
            AdvancementManager.giveAdvancement(p, "outlaw");
         }
         KingsButBad.roles.put(p, Role.OUTLAW);
         Bukkit.getScheduler().scheduleSyncDelayedTask(KingsButBad.pl, () -> {
            ItemStack mafiaSword = new ItemStack(Material.STONE_SWORD);
            ItemMeta mafiaSwordMeta = mafiaSword.getItemMeta();
            mafiaSwordMeta.setDisplayName(CreateText.addColors("<gold>Outlaw Dagger"));
            mafiaSwordMeta.setUnbreakable(true);
            List<String> lore = new ArrayList<>();
            mafiaSword.setItemMeta(mafiaSwordMeta);
            ItemStack mafiaHelmet = createItem(Material.IRON_HELMET, "<gold>Outlaw Helmet", lore, null);
            ItemStack mafiaChestplate = createItem(Material.LEATHER_CHESTPLATE, "<gold>Outlaw Chestplate", lore, null);
            ItemStack mafiaLeggings = createItem(Material.IRON_LEGGINGS, "<gold>Outlaw Leggings", lore, null);
            ItemStack mafiaBoots = createItem(Material.LEATHER_BOOTS, "<gold>Outlaw Boots", lore, null);
            Item.applyDye(mafiaChestplate, Color.ORANGE);
            Item.applyDye(mafiaBoots, Color.ORANGE);
            PlayerInventory inv = p.getInventory();
            inv.addItem(mafiaSword);
            if (isSettable(mafiaBoots))
               inv.setBoots(mafiaBoots);
            if (isSettable(mafiaLeggings))
               inv.setLeggings(mafiaLeggings);
            if (isSettable(mafiaHelmet))
               inv.setHelmet(mafiaHelmet);
            if (isSettable(mafiaChestplate))
               inv.setChestplate(mafiaChestplate);
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Mafias").addPlayer(p);
            p.closeInventory();
            RoleManager.givePlayerRole(p);
         }, 3);
         return;
      }
         if (event.getCurrentItem() != null && event.getCurrentItem().hasItemFlag(ItemFlag.HIDE_PLACED_ON)) {
         event.setCancelled(true);
         if (event.getCurrentItem().getType().equals(Material.DEEPSLATE_COAL_ORE)) {
            Player p = (Player) event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 150.0) {
               Keys.money.subtractDouble(p, 150.0);
               Bukkit.broadcastMessage(
                  CreateText.addColors(
                     "<gradient:#FFFF52:#FFBA52><b><b>" + RoleManager.getKingGender(p) + " " + p.getName() + "<blue> has bought the <gold>Coal Compactor"
                  )
               );
               KingsButBad.coalCompactor = true;
               p.closeInventory();
            }
         }

         if (event.getCurrentItem().getType().equals(Material.OAK_FENCE)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p,0.0) >= 150.0) {
               Keys.money.subtractDouble(p, 150.0);
               Bukkit.broadcastMessage(
                  CreateText.addColors(
                     "<gradient:#FFFF52:#FFBA52><b><b>" + RoleManager.getKingGender(p) + " " + p.getName() + "<blue> has bought the <gold>Mines"
                  )
               );
               KingsButBad.mineUnlocked = true;
               p.closeInventory();
            }
         }

         if (event.getCurrentItem().getType().equals(Material.LIGHT_BLUE_WOOL)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 200.0) {
               Keys.money.subtractDouble(p,200.0);
               Bukkit.broadcastMessage(
                  CreateText.addColors(
                     "<gradient:#FFFF52:#FFBA52><b><b>" + RoleManager.getKingGender(p) + " " + p.getName() + "<blue> has bought <gold>Little Joe's Shack"
                  )
               );
               KingsButBad.joesUnlocked = true;
               p.closeInventory();
            }
         }

         if (event.getCurrentItem().getType().equals(Material.IRON_SHOVEL)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 150.0) {
               Keys.money.subtractDouble(p, 150.0);
               LivingEntity le = (LivingEntity)Bukkit.getWorld("world")
                  .spawnEntity(KingdomsLoader.activeKingdom.getSpawn(),EntityType.ZOMBIE);
               le.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
               le.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
               le.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
               le.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
               le.setCustomName(ChatColor.BLUE + "Royal Patroller");
               p.getOpenInventory().close();
               le.setCustomNameVisible(true);
               le.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
               le.addPotionEffect(PotionEffectType.SPEED.createEffect(9999999, 0));
               le.addPotionEffect(PotionEffectType.REGENERATION.createEffect(9999999, 0));
            }
         }

         if (event.getCurrentItem().getType().equals(Material.MAP)) {
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().sendMessage(ChatColor.GOLD + "PRISON'S STATS");
            int prisonercount = 0;
            int guardcount = 0;

            for (Player p : Bukkit.getOnlinePlayers()) {
               if (KingsButBad.roles.get(p).equals(Role.PRISONER)) {
                  prisonercount = prisonercount + 1;
               }

               if (KingsButBad.roles.get(p).equals(Role.PRISON_GUARD)) {
                  guardcount = guardcount + 1;
               }
            }

            event.getWhoClicked().sendMessage(ChatColor.GOLD + "Prisoners we are current holding: " + prisonercount);
            event.getWhoClicked().sendMessage(ChatColor.BLUE + "It is currently " + MiscTask.bossbar.getTitle());
            event.getWhoClicked().sendMessage(ChatColor.LIGHT_PURPLE + "We have " + guardcount + " loyal guards!");
         }

         if (event.getCurrentItem().getType().equals(Material.RED_CONCRETE)) {
            Player p = (Player)event.getWhoClicked();
            if(KingsButBad.king == p || KingsButBad.king2 == p || KingsButBad.roles.getOrDefault(p, Role.PEASANT).isPowerful){
               p.sendMessage(CreateText.addColors("<red>You can't in prison yourself! <gray>(<white>Criminal,Outlaw,Peasant can turn themself in!<gray>)"));
               event.setCancelled(true);
               return;
            }
            Bukkit.broadcastMessage(CreateText.addColors("<red>>> <b>" + p.getName() + "<gold> </b> turned themselves in, for some reason.."));
            KingsButBad.prisonTimer.put(p, 2400);
            KingsButBad.roles.put(p, Role.PRISONER);
            event.setCancelled(true);
            event.getWhoClicked().getInventory().clear();
            Keys.inPrison.set(event.getWhoClicked(), true);
            RoleManager.givePlayerRole(p);
         }

         if (event.getCurrentItem().getType().equals(Material.GOLDEN_APPLE)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 150.0) {
               Keys.money.subtractDouble(p, 150.0);
               p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
               AdvancementManager.giveAdvancement(p, "undertable");
            }
         }

         if (event.getCurrentItem().getType().equals(Material.IRON_NUGGET)) {
            Player p = (Player)event.getWhoClicked();
            if(RoleManager.isKingAtAll(p)){
               p.sendMessage(CreateText.addColors("<red>You can't be a servant!"));
               return;
            }
            KingsButBad.roles.put(p, Role.SERVANT);
            RoleManager.givePlayerRole(p);
         }

         if (event.getCurrentItem().getType().equals(Material.LEATHER_CHESTPLATE)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 50.0) {
               Keys.money.subtractDouble(p,50.0);
               p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
               p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
               p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
               p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
            }
         }

         if (event.getCurrentItem().getType().equals(Material.POTION)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 15.0) {
               Keys.money.subtractDouble(p, 15.0);
               ItemStack cod = new ItemStack(Material.POTION);
               PotionMeta ptmeta = (PotionMeta)cod.getItemMeta();
               ptmeta.setDisplayName(ChatColor.BLUE + "Water");
               ptmeta.setColor(Color.BLUE);
               cod.setItemMeta(ptmeta);
               p.getInventory().addItem(cod);
            }
         }

         if (event.getCurrentItem().getType().equals(Material.IRON_SWORD)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 300.0) {
               Keys.money.subtractDouble(p, 300.0);
               p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
            }
         }

         if (event.getCurrentItem().getType().equals(Material.CHAINMAIL_HELMET)) {
            Player p = (Player)event.getWhoClicked();
            if(p.getInventory().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE) == 1) {
               p.sendMessage(CreateText.addColors("<red>Your gear is already upgraded!"));
               return;
            }
            if (Keys.money.get(p, 0.0) >= 2500.0) {
               if(p.getInventory().getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE){
                     p.sendMessage(CreateText.addColors("<red>You have already upgraded your Chestplate!"));
                     return;
               }
               Keys.money.subtractDouble(p, 2500.0);
               PlayerInventory inv = p.getInventory();
               p.sendMessage(CreateText.addColors("<gray>Your chestplate and boots now have Protection!"));
               ItemStack chestplate = inv.getChestplate();
               ItemStack boots = inv.getBoots();
               Item.applyEnchmanent(chestplate, Enchantment.PROTECTION_PROJECTILE, 1);
               Item.applyEnchmanent(boots, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            }
         }

         if (event.getCurrentItem().getType().equals(Material.WOODEN_SWORD)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 30.0) {
               Keys.money.subtractDouble(p, 30.0);
               p.getInventory().addItem(new ItemStack(Material.WOODEN_SWORD));
            }
         }

         if (event.getCurrentItem().getType().equals(Material.STICK)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 200.0) >= 200.0) {
               Keys.money.subtractDouble(p, 200.0);
               ItemStack card = new ItemStack(Material.STICK);
               ItemMeta cardm = card.getItemMeta();
               cardm.setDisplayName(ChatColor.BLUE + "Adrenaline Shot");
               card.setItemMeta(cardm);
               p.getInventory().addItem(card);
            }
         }

         if (event.getCurrentItem().getType().equals(Material.STONE_AXE)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 150.0) {
               Keys.money.subtractDouble(p, 150.0);
               p.getInventory().addItem(new ItemStack(Material.STONE_AXE));
               AdvancementManager.giveAdvancement(p, "undertable");
            }
         }

         if (event.getCurrentItem().getType().equals(Material.ARROW)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p,0.0) >= 50.0) {
               Keys.money.subtractDouble(p, 50.0);
               p.getInventory().addItem(new ItemStack(Material.ARROW, 32));
            }
         }

         if (event.getCurrentItem().getType().equals(Material.BOW)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 100.0) {
               Keys.money.subtractDouble(p, 100.0);
               p.getInventory().addItem(new ItemStack(Material.BOW));
            }
         }

         if (event.getCurrentItem().getType().equals(Material.YELLOW_CONCRETE)) {
            Player p = (Player)event.getWhoClicked();
            ArrayList<String> dialogues = getDialougesStrings();
            p.sendMessage(CreateText.addColors("<green>archer johnm <gray><b>>></gray><white> " + dialogues.get(new Random().nextInt(0, dialogues.size()))));
         }

         if (event.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK)) {
            Player p = (Player)event.getWhoClicked();
            if (event.getCurrentItem().getItemMeta() != null) {
               ItemMeta im = event.getCurrentItem().getItemMeta();
               if (im.getDisplayName().equals(ChatColor.GOLD + "+1 Power Level")
                  && Keys.money.get(p, 0.0) >= 400.0) {

                  for (ItemStack i : p.getInventory()) {
                     if (i != null && i.getType().equals(Material.BOW)) {
                        if(i.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) >= 1) continue;
                        if (i.getEnchantments().containsKey(Enchantment.ARROW_DAMAGE)) {
                           i.addEnchantment(Enchantment.ARROW_DAMAGE, i.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) + 1);
                           Keys.money.subtractDouble(p, 400.0);
                        } else {
                           i.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
                           Keys.money.subtractDouble(p, 400.0);
                        }
                     }
                  }
               }

               if (im.getDisplayName().equals(ChatColor.GOLD + "Fortune 1 On all Hoes")
                  && Keys.money.get(p, 0.0) >= 400.0) {

                  for (ItemStack ix : p.getInventory()) {
                     if (ix != null && ix.getType().equals(Material.WOODEN_HOE)) {
                        if(ix.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) >= 1) continue;
                        ix.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
                        Keys.money.subtractDouble(p, 400.0);
                     }
                  }
               }

               if (im.getDisplayName().equals(ChatColor.GOLD + "Lure 2 On all Fishing Rods")
                  && Keys.money.get(p, 0.0) >= 400.0) {

                  for (ItemStack ixx : p.getInventory()) {
                     if (ixx != null && ixx.getType().equals(Material.FISHING_ROD)) {
                        if(ixx.getEnchantmentLevel(Enchantment.LURE) >= 1) continue;
                        ixx.addEnchantment(Enchantment.LURE, 2);
                        Keys.money.subtractDouble(p,400.0);
                     }
                  }
               }

               if (im.getDisplayName().equals(ChatColor.GOLD + "Efficiency 2 On all Pickaxes")
                  && Keys.money.get(p, 0.0) >= 400.0) {
                  Keys.money.subtractDouble(p, 400.0);

                  for (ItemStack ixxx : p.getInventory()) {
                     if (ixxx != null && ixxx.getType().equals(Material.STONE_PICKAXE)) {
                        if(ixxx.getEnchantmentLevel(Enchantment.DIG_SPEED) >= 2) continue;
                        ixxx.addEnchantment(Enchantment.DIG_SPEED, 2);
                     }
                  }
               }
            }
         }

         if (event.getCurrentItem().getType().equals(Material.MINECART)) {
            Player p = (Player)event.getWhoClicked();
            p.teleport(KingdomsLoader.activeKingdom.getMineExitLoc2());
            p.playSound(p, Sound.ENTITY_MINECART_RIDING, 1.0F, 1.0F);
         }
         if (event.getCurrentItem().getType().equals(Material.TRIPWIRE_HOOK)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 1500.0) {
               Keys.money.subtractDouble(p, 1500.0);
               ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
               ItemMeta cardm = card.getItemMeta();
               cardm.setDisplayName(ChatColor.BLUE + "Keycard");
               card.setItemMeta(cardm);
               p.getInventory().addItem(card);
               AdvancementManager.giveAdvancement(p, "undertable");
            }
         }

         if (event.getCurrentItem().getType().equals(Material.PAPER)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 1500.0) {
               Keys.money.subtractDouble(p, 1500.0);
               ItemStack card = new ItemStack(Material.PAPER);
               ItemMeta cardm = card.getItemMeta();
               cardm.setDisplayName(ChatColor.BLUE + "Get-Out-Of-Jail-Free Card");
               card.setItemMeta(cardm);
               p.getInventory().addItem(card);
            }
         }

         if (event.getCurrentItem().getType().equals(Material.COOKED_COD)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 15.0) {
               Keys.money.subtractDouble(p, 15.0);
               p.getInventory().addItem(new ItemStack(Material.COOKED_COD, 16));
            }
         }

         if (event.getCurrentItem().getType().equals(Material.GOLDEN_CARROT)) {
            Player p = (Player)event.getWhoClicked();
            if (Keys.money.get(p, 0.0) >= 32.0) {
               Keys.money.subtractDouble(p, 32.0);
               p.getInventory().addItem(new ItemStack(Material.GOLDEN_CARROT, 16));
            }
         }

         if (event.getCurrentItem().getType().equals(Material.FISHING_ROD) && !event.getWhoClicked().getInventory().contains(Material.FISHING_ROD)) {
            event.getWhoClicked().getInventory().addItem(new ItemStack(Material.FISHING_ROD));
         }

         if (event.getCurrentItem().getType().equals(Material.WOODEN_HOE) && !event.getWhoClicked().getInventory().contains(Material.WOODEN_HOE)) {
            ItemStack woodenhoe = new ItemStack(Material.WOODEN_HOE);
            ItemMeta woodenhoemeta = woodenhoe.getItemMeta();
            woodenhoemeta.setDestroyableKeys(Collections.singleton(NamespacedKey.minecraft("wheat")));
            woodenhoe.setItemMeta(woodenhoemeta);
            event.getWhoClicked().getInventory().addItem(woodenhoe);
         }

         if (event.getCurrentItem().getType().equals(Material.IRON_PICKAXE) && !event.getWhoClicked().getInventory().contains(Material.IRON_PICKAXE)) {
            ItemStack woodenhoe = new ItemStack(Material.IRON_PICKAXE);
            ItemMeta woodenhoemeta = woodenhoe.getItemMeta();
            woodenhoemeta.setDestroyableKeys(Collections.singleton(NamespacedKey.minecraft("deepslate_coal_ore")));
            woodenhoe.setItemMeta(woodenhoemeta);
            event.getWhoClicked().getInventory().addItem(woodenhoe);
         }

         if (event.getCurrentItem().getType().equals(Material.STONE_PICKAXE) && !event.getWhoClicked().getInventory().contains(Material.STONE_PICKAXE)) {
            ItemStack woodenhoe = new ItemStack(Material.STONE_PICKAXE);
            ItemMeta woodenhoemeta = woodenhoe.getItemMeta();
            woodenhoemeta.setDestroyableKeys(Collections.singleton(NamespacedKey.minecraft("coal_ore")));
            woodenhoe.setItemMeta(woodenhoemeta);
            event.getWhoClicked().getInventory().addItem(woodenhoe);
         }

         if (event.getCurrentItem().getType().equals(Material.BONE) && !event.getWhoClicked().getInventory().contains(Material.BONE)) {
            ItemStack woodenhoe = new ItemStack(Material.BONE);
            ItemMeta woodenhoemeta = woodenhoe.getItemMeta();
            woodenhoemeta.setDestroyableKeys(Collections.singleton(NamespacedKey.minecraft("brown_concrete_powder")));
            woodenhoe.setItemMeta(woodenhoemeta);
            event.getWhoClicked().getInventory().addItem(woodenhoe);
         }

         if (event.getCurrentItem().getType().equals(Material.WHEAT)) {
            int iii = 0;
            if (!event.getWhoClicked().hasCooldown(Material.WOODEN_HOE)) {
               int wheat = 0;
               for (ItemStack ixxxx : event.getWhoClicked().getInventory()) {
                  if (ixxxx != null && ixxxx.getType().equals(Material.WHEAT)) {
                     int originalamount = ixxxx.getAmount();
                     wheat += originalamount;
                     if(wheat >= 1000)
                        AdvancementManager.giveAdvancement((Player) event.getWhoClicked(), "farmerjoe");
                     Bukkit.getScheduler()
                        .runTaskLater(
                           KingsButBad.getPlugin(KingsButBad.class),
                           () -> {
                              for (int i = 1; i < ixxxx.getAmount() + 1; i++) {
                                 Bukkit.getScheduler()
                                    .runTaskLater(
                                       KingsButBad.getPlugin(KingsButBad.class),
                                       () -> {
                                          ixxxx.setAmount(ixxxx.getAmount() - 1);
                                          Player p = (Player)event.getWhoClicked();
                                          p.setCooldown(Material.WOODEN_HOE, 20);
                                          p.playSound(p, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                                          Keys.money.addDouble(p, 5.0);
                                       },
                                       5
                                    );
                              }
                           },
                           1
                        );
                     iii = iii + originalamount;
                  }
               }
            }

         }

         if (event.getCurrentItem().getType().equals(Material.COAL_ORE)) {
            int iii = 0;
            if (!event.getWhoClicked().hasCooldown(Material.STONE_PICKAXE)) {
               for (ItemStack ixxxxx : event.getWhoClicked().getInventory()) {
                  if (ixxxxx != null && ixxxxx.getType().equals(Material.COAL_ORE)) {
                     int originalamount = ixxxxx.getAmount();
                     Bukkit.getScheduler()
                        .runTaskLater(
                           KingsButBad.getPlugin(KingsButBad.class),
                           () -> {
                              for (int i = 1; i < ixxxxx.getAmount() + 1; i++) {
                                 Bukkit.getScheduler()
                                    .runTaskLater(
                                       KingsButBad.getPlugin(KingsButBad.class),
                                       () -> {
                                          ixxxxx.setAmount(ixxxxx.getAmount() - 1);
                                          Player p = (Player)event.getWhoClicked();
                                          p.setCooldown(Material.STONE_PICKAXE, 20);
                                          p.playSound(p, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                                          Keys.money.addDouble(p, 50.0);
                                       },
                                       1
                                    );
                              }
                           },
                           1
                        );
                     iii = iii + originalamount;
                  }
               }
            }
         }

         if (event.getCurrentItem().getType().equals(Material.BROWN_CONCRETE)) {
            int iii = 0;
            if (!event.getWhoClicked().hasCooldown(Material.BONE)) {
               for (ItemStack ixxxxxx : event.getWhoClicked().getInventory()) {
                  if (ixxxxxx != null && ixxxxxx.getType().equals(Material.BROWN_DYE)) {
                     int originalamount = ixxxxxx.getAmount();
                     Bukkit.getScheduler()
                        .runTaskLater(
                           KingsButBad.getPlugin(KingsButBad.class),
                           () -> {
                              for (int ii = 1; ii < ixxxxxx.getAmount() + 1; ii++) {
                                 Bukkit.getScheduler()
                                    .runTaskLater(
                                       KingsButBad.getPlugin(KingsButBad.class),
                                       () -> {
                                          ixxxxxx.setAmount(ixxxxxx.getAmount() - 1);
                                          Player p = (Player)event.getWhoClicked();
                                          p.setCooldown(Material.BONE, 20);
                                          p.playSound(p, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                                          Keys.money.addDouble(p, 15.0);
                                       },
                                            ii
                                    );
                              }
                           },
                                iii
                        );
                     iii = iii + originalamount;
                  }
               }
            }
         }

         if (event.getCurrentItem().getType().equals(Material.WATER_BUCKET)) {
            int iii = 0;
            if (!event.getWhoClicked().hasCooldown(Material.FISHING_ROD)) {
               for (ItemStack ixxxxxxx : event.getWhoClicked().getInventory()) {
                  if (ixxxxxxx != null && ixxxxxxx.getType().equals(Material.SALMON)) {
                     int originalamount = ixxxxxxx.getAmount();
                     Bukkit.getScheduler()
                        .runTaskLater(
                           KingsButBad.getPlugin(KingsButBad.class),
                           () -> {
                              for (int ii = 1; ii < ixxxxxxx.getAmount() + 1; ii++) {
                                 Bukkit.getScheduler()
                                    .runTaskLater(
                                       KingsButBad.getPlugin(KingsButBad.class),
                                       () -> {
                                          ixxxxxxx.setAmount(ixxxxxxx.getAmount() - 1);
                                          Player p = (Player)event.getWhoClicked();
                                          p.setCooldown(Material.FISHING_ROD, 20);
                                          p.playSound(p, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                                          Keys.money.addDouble(p, 100.0);
                                       },
                                            ii
                                    );
                              }
                           },
                                iii
                        );
                     iii = iii + originalamount;
                  }
               }
            }
         }
      }
   }

   @NotNull
   private static ArrayList<String> getDialougesStrings() {
      ArrayList<String> dialouges = new ArrayList<>() {
       };
      dialouges.add(
         "Kid, you really make me remember that person who tried to talk to me.. that one time... that they.. talked to me.. darn, i sure am lonely and an outcast (laugh track)"
      );
      dialouges.add("After so many years in the agency, it's finnally fun to see someone who isn't about to be put on a mission to their death! Hi, kid!");
      dialouges.add("Gosh, I sure am old!");
      dialouges.add("the IRS are on our asses");
      dialouges.add("Hello, Aquaotter is a terrible dev fr fr -_Aquaotter_");
      dialouges.add(
         "Earth isn't really, we all live on one big flat world, one big, blocky, green, flat landscape. The walls are obstructing our views. There is nothing beyond us. Wake up. BTU THAT'S JUST A THEORY! A GAME THEORY!!!!!!!!!!!!!!!!!!!!!!1"
      );
      dialouges.add("Hi, I'm arthur Join! (laugh track)");
      dialouges.add("God, I sure am comedical! (Laugh track)");
      return dialouges;
   }

   private void changeSettings(Player p, Key key, String settingName){
         if(key.type() != KeyTypes.BOOLEAN) return;
         key.set(p, !key.getBoolean(p, false));
         p.sendMessage(CreateText.addColors("<gray>Settings Changed: <white>"+settingName+" <gray>has been set to <white>"+key.getBoolean(p, false)+"<gray>!"));

   }
}
