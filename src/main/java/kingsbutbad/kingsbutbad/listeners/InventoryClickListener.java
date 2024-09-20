package kingsbutbad.kingsbutbad.listeners;

import com.destroystokyo.paper.Namespaced;
import kingsbutbad.kingsbutbad.Advancements.AdvancementManager;
import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.Kingdom.Kingdom;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsReader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Key;
import kingsbutbad.kingsbutbad.keys.KeyTypes;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.*;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static kingsbutbad.kingsbutbad.tasks.ScheduleTask.bossbar;
import static kingsbutbad.kingsbutbad.utils.Item.createItem;
import static kingsbutbad.kingsbutbad.utils.RoleManager.isSettable;

@SuppressWarnings("deprecation")
public class InventoryClickListener implements Listener { // TODO: Clean up This File (InventoryClickListener.java)
   @EventHandler
   public void onPlayerQuit(InventoryClickEvent event) {
      if (!(event.getWhoClicked() instanceof Player p)) return;
      if (p.hasCooldown(Material.FISHING_ROD)
         || p.hasCooldown(Material.WOODEN_HOE)
         || p.hasCooldown(Material.BONE)
         || p.hasCooldown(Material.STONE_PICKAXE)) {
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
         p.teleport(targetKingdom.getSpawn());
         p.sendMessage(CreateText.addColors("<gray>You have selected and been teleported to "+targetKingdom.getDisplayName()+" <gray>Kingdom!"));
         BotManager.getBuilderChannel().sendMessage(p.getName() + " has selected and been teleported to "+ChatColor.stripColor(CreateText.addColors(targetKingdom.getDisplayName()))+" Kingdom!").queue();
      }
      if(event.getView().getTitle().equals(CreateText.addColors("<red>Pacts"))){
         event.setCancelled(true);

      }
      if(event.getView().getTitle().equals(CreateText.addColors("<red>Pact GUI"))){
         event.setCancelled(true);

         ItemStack item = event.getCurrentItem();
         Pacts targetPact = null;
         for(Pacts pacts : Pacts.values())
            if(pacts.getItemStack().getType()==item.getType())
               targetPact = pacts;
         if(targetPact == null) return;
         if(targetPact.name().equals(Keys.activePact.get(p, Pacts.NONE.name()))) {
            p.sendMessage(CreateText.addColors("<red>Your current pact is already this!"));
            return;
         }
         if(targetPact != Pacts.NONE && buy(10000, p)){
          Keys.activePact.set(p, targetPact.name());
          p.sendMessage(CreateText.addColors("<green>Your pact is now "+targetPact.getDisplayName()));
          return;
         }
         if(targetPact == Pacts.NONE) {
            Keys.activePact.set(p, targetPact.name());
            p.sendMessage(CreateText.addColors("<red>Your pact is now none!"));
         }
         return;
      }
      if(event.getView().getTitle().equals(CreateText.addColors("<gold>KingsButBad Settings"))){
         Material type = event.getCurrentItem().getType();
         event.setCancelled(true);
         if(type.equals(Material.BLACK_CANDLE))
            changeSettings(p, Keys.isAutoShoutEnabled, "Auto Shout");
         if(type.equals(Material.NAME_TAG))
            changeSettings(p, Keys.SHORTEN_SHOUTMSG, "Shorten Shout Messages");
         if(type.equals(Material.GOLD_INGOT))
            changeSettings(p, Keys.PING_NOISES, "Ping On Mentioned");
         if(type.equals(Material.COAL))
            changeSettings(p, Keys.showMineMessages, "Show Mine Messages");
         if(type.equals(Material.PAPER)){
            String chat;
            if(!Keys.selectedChat.get(p, false))
               chat = "<white>Builder Chat<gray>";
            else
               chat = "<white>Staff Chat<gray>";
            p.sendMessage(CreateText.addColors("<gray>Settings Changed: <white>Your Selected Chat has been set to "+chat+" Shortcut!"));
            Keys.selectedChat.set(p, !Keys.selectedChat.get(p, false));
         }
         if(type.equals(Material.CLOCK))
            changeSettings(p, Keys.displayRoleStats, "Display Roles Playtime");
         if(type.equals(Material.BOOK))
            changeSettings(p, Keys.SHORTEN_ROLES_MSG, "Shorten Roles Tab/Messages");
         if(type.equals(Material.WRITABLE_BOOK))
            changeSettings(p, Keys.SHORTEN_RANKS_MSG, "Shorten Ranks Tab/Messages");
         PlayerJoinListener.updateTab((Player) p);
         Bukkit.dispatchCommand(p, "kbbsettings");
         return;
      }
      if (event.getView().getTitle().equals(CreateText.addColors("<gold>Prisoner Trader"))) {
         event.setCancelled(true);
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
               KingsButBad.prisonTimer.put(p, KingsButBad.prisonTimer.getOrDefault(p, 0F) - 25*20);
               if(KingsButBad.prisonTimer.getOrDefault(p, 0F) < 0) KingsButBad.prisonTimer.remove(p);
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
               AdvancementManager.giveAdvancement(p, "alohomora");
               AdvancementManager.giveAdvancement(p, "undertable");
            } else {
               p.sendMessage(CreateText.addColors("<gray>You need <white>35 Coal<gray> to buy a Make-Shift Key!"));
            }
            return;
         }
         return;
      }
      if(!event.getClickedInventory().getType().equals(InventoryType.CHEST) || p.getGameMode().equals(GameMode.CREATIVE)) return;
      event.setCancelled(true);

               List<String> lore = event.getCurrentItem().getLore();

               String sellString = null;
               String getString = null;
               String buyString = null;

               if(lore != null)
                  for (String line : lore) {
                  if (line.startsWith(ChatColor.GREEN.toString() + "Sell $")) {
                     sellString = line.substring((ChatColor.GREEN.toString() + "Sell $").length()).trim();
                  } else if (line.startsWith(ChatColor.GREEN.toString() + "Get ")) {
                     getString = line.substring((ChatColor.GREEN.toString() + "Get ").length()).trim();
                  } else if (line.startsWith(ChatColor.GREEN.toString() + "Buy $")) {
                     buyString = line.substring((ChatColor.GREEN.toString() + "Buy $").length()).trim();
                  }
                  }

               if (sellString != null) {
                  handleSell(event, event.getCurrentItem(), sellString);
                  p.closeInventory();
               }

               if (getString != null) {
                  handleGet(event);
                  p.closeInventory();
               }
               if (buyString != null)
                  handleBuy(event, buyString);
         if (event.getCurrentItem().getType().equals(Material.DEEPSLATE_COAL_ORE) && !KingsButBad.coalCompactor) {
            if (buy(150, p)) {
               Bukkit.broadcastMessage(
                  CreateText.addColors(
                     "<blue>" + p.getName() + " has bought the <gold>Coal Compactor"
                  )
               );
               KingsButBad.coalCompactor = true;
               p.closeInventory();
            }
         }
         if (event.getCurrentItem().getType().equals(Material.OAK_FENCE) && !KingsButBad.mineUnlocked) {
            if (buy(150, p)) {
               Bukkit.broadcastMessage(
                  CreateText.addColors(
                      "<blue>" + p.getName() + " has bought the <gold>Mines"
                  )
               );
               KingsButBad.mineUnlocked = true;
               p.closeInventory();
            }
         }
         if (event.getCurrentItem().getType().equals(Material.LIGHT_BLUE_WOOL) && !KingsButBad.joesUnlocked) {

            if (buy(200, p)) {
               Bukkit.broadcastMessage(
                  CreateText.addColors(
                      "<blue>" + p.getName() + " has bought <gold>Little Joe's Shack"
                  )
               );
               KingsButBad.joesUnlocked = true;
               p.closeInventory();
            }
         }
         if(getRoyalPatrollers() < 5) {
            if (event.getCurrentItem().getType().equals(Material.IRON_SHOVEL)) {

               if (buy(150, p)) {
                  LivingEntity le = (LivingEntity) Bukkit.getWorld("world")
                          .spawnEntity(KingdomsLoader.activeKingdom.getSpawn(), EntityType.ZOMBIE);
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
         } else {
            event.getWhoClicked().sendMessage(CreateText.addColors("<red>Sorry, there is a cap of 5 Patrollers!"));
         }
         if (event.getCurrentItem().getType().equals(Material.MAP)) {
            p.closeInventory();
            p.sendMessage(ChatColor.GOLD + "PRISON'S STATS");
            int prisonercount = 0;
            int guardcount = 0;

            for (Player player : Bukkit.getOnlinePlayers()) {
               if (KingsButBad.roles.get(player).equals(Role.PRISONER))
                  prisonercount++;
               if (KingsButBad.roles.get(player).equals(Role.PRISON_GUARD))
                  guardcount++;
            }

            p.sendMessage(ChatColor.GOLD + "Prisoners we are current holding: " + prisonercount);
            p.sendMessage(ChatColor.BLUE + "It is currently " + bossbar.getTitle());
            p.sendMessage(ChatColor.LIGHT_PURPLE + "We have " + guardcount + " loyal guards!");
         }
         if (event.getCurrentItem().getType().equals(Material.RED_CONCRETE)) {

            if(KingsButBad.king == p || KingsButBad.king2 == p || KingsButBad.roles.getOrDefault(p, Role.PEASANT).isPowerful){
               p.sendMessage(CreateText.addColors("<red>You can't in prison yourself! <gray>(<white>Criminal,Outlaw,Peasant can turn themself in!<gray>)"));
               event.setCancelled(true);
               return;
            }
            Bukkit.broadcastMessage(CreateText.addColors("<red>>> <b>" + p.getName() + "<gold> </b> turned themselves in, for some reason.."));
            KingsButBad.prisonTimer.put(p, 20*60F);
            KingsButBad.roles.put(p, Role.PRISONER);
            event.setCancelled(true);
            p.getInventory().clear();
            Keys.inPrison.set(p, true);
            RoleManager.givePlayerRole(p);
         }
         if (event.getCurrentItem().getType().equals(Material.GOLDEN_APPLE)) {
            if (buy(150, p)) {
               p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
               if(!event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.GRAY + "A strong tool to keep yourself safe."))
                  AdvancementManager.giveAdvancement(p, "undertable");
            }
         }
         if (event.getCurrentItem().getType().equals(Material.IRON_NUGGET)) {
            if(RoleManager.isKingAtAll(p)){
               p.sendMessage(CreateText.addColors("<red>You can't be a servant!"));
               return;
            }
            if(Keys.activePact.get(p,"") == Pacts.CRIMINAL.name()) {
               p.sendMessage(CreateText.addColors("<red>Your pact doesn't allow to use this!"));
               return;
            }
            KingsButBad.roles.put(p, Role.SERVANT);
            RoleManager.givePlayerRole(p);
         }
         if (event.getCurrentItem().getType().equals(Material.IRON_SWORD))
            if (buy(300, p))
               p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
      if(event.getCurrentItem().getType().equals(Material.ORANGE_DYE)) {
         event.setCancelled(true);
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
            mafiaSword.setItemMeta(mafiaSwordMeta);
            ItemStack mafiaHelmet = createItem(Material.IRON_HELMET, "<gold>Outlaw Helmet", new ArrayList<>(), null);
            ItemStack mafiaChestplate = createItem(Material.LEATHER_CHESTPLATE, "<gold>Outlaw Chestplate", new ArrayList<>(), null);
            ItemStack mafiaLeggings = createItem(Material.IRON_LEGGINGS, "<gold>Outlaw Leggings", new ArrayList<>(), null);
            ItemStack mafiaBoots = createItem(Material.LEATHER_BOOTS, "<gold>Outlaw Boots", new ArrayList<>(), null);
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

         if (event.getCurrentItem().getType().equals(Material.CHAINMAIL_HELMET)) {

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
         if (event.getCurrentItem().getType().equals(Material.ARROW))
            if (buy(50.0, p))
               p.getInventory().addItem(new ItemStack(Material.ARROW, 32));

         if (event.getCurrentItem().getType().equals(Material.BOW))
            if (buy(100, p))
               p.getInventory().addItem(new ItemStack(Material.BOW));

         if (event.getCurrentItem().getType().equals(Material.YELLOW_CONCRETE)) {
            ArrayList<String> dialogues = getDialougesStrings();
            p.sendMessage(CreateText.addColors("<green>archer johnm <gray><b>>></gray><white> " + dialogues.get(new Random().nextInt(0, dialogues.size()))));
         }

         if (event.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK)) {

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
            p.teleport(KingdomsLoader.activeKingdom.getMineExitLoc2());
            p.playSound(p, Sound.ENTITY_MINECART_RIDING, 1.0F, 1.0F);
         }

         if (event.getCurrentItem().getType().equals(Material.STONE_PICKAXE)
                     && !p.getInventory().contains(Material.STONE_PICKAXE)) {

                 ItemStack stonePickaxe = new ItemStack(Material.STONE_PICKAXE);
                 ItemMeta pickaxeMeta = stonePickaxe.getItemMeta();

                 Collection<Namespaced> destroyableKeys = new HashSet<>();
                 destroyableKeys.add(NamespacedKey.minecraft("coal_ore"));
                 destroyableKeys.add(NamespacedKey.minecraft("iron_ore"));
                 destroyableKeys.add(NamespacedKey.minecraft("gold_ore"));

                 pickaxeMeta.setDestroyableKeys(destroyableKeys);
                 stonePickaxe.setItemMeta(pickaxeMeta);

                 p.getInventory().addItem(stonePickaxe);
         }
         if (event.getCurrentItem().getType().equals(Material.COAL_ORE)) {
            int iii = 0;
            if (!p.hasCooldown(Material.STONE_PICKAXE)) {
               for (ItemStack ixxxxx : p.getInventory()) {
                  if (ixxxxx != null) {
                     Material itemType = ixxxxx.getType();
                     int reward;

                     if (itemType.equals(Material.COAL_ORE)) {
                        reward = 50;
                     } else if (itemType.equals(Material.IRON_ORE)) {
                        reward = 100;
                     } else if (itemType.equals(Material.GOLD_ORE)) {
                        reward = 250;
                     } else {
                         reward = 0;
                     }

                      if (reward > 0) {
                        int originalAmount = ixxxxx.getAmount();
                        Bukkit.getScheduler().runTaskLater(
                                KingsButBad.getPlugin(KingsButBad.class),
                                () -> {
                                   for (int i = 1; i <= ixxxxx.getAmount(); i++) {
                                      Bukkit.getScheduler().runTaskLater(
                                              KingsButBad.getPlugin(KingsButBad.class),
                                              () -> {
                                                 ixxxxx.setAmount(ixxxxx.getAmount() - 1);
                                                 p.setCooldown(Material.STONE_PICKAXE, 20);
                                                 p.playSound(p, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                                                 Keys.money.addDouble(p, (double) reward);
                                              },
                                              1
                                      );
                                   }
                                },
                                1
                        );
                        iii = iii + originalAmount;
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
   private void handleSell(InventoryClickEvent event, ItemStack itemStack, String sellString) {
      Player p = (Player) event.getWhoClicked();
      double sellPrice = 0.0;
      try {
         sellPrice = Double.parseDouble(sellString);
      } catch (NumberFormatException e) {
         Bukkit.getLogger().warning("Invalid sell price format: " + sellString);
         return;
      }

      if (sellPrice > 0) {
         ItemStack[] inventoryItems = p.getInventory().getContents();
         int itemCount = 0;
         for (ItemStack invItem : inventoryItems) {
            if (invItem != null && invItem.getType() == itemStack.getType()) {
               itemCount += invItem.getAmount();
               invItem.setAmount(0);
            }
         }

         double totalEarned = itemCount * sellPrice;
         Keys.money.addDouble(p, totalEarned);

         p.closeInventory();
         p.sendMessage(ChatColor.GREEN + "Sold " + itemCount + " items for " + FormatUtils.formatMoney(totalEarned));
      }
   }
   private void handleGet(InventoryClickEvent event) {
      Player p = (Player) event.getWhoClicked();
      ItemStack item = event.getCurrentItem();
      ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
      itemMeta.setLore(new ArrayList<>());
      item.setItemMeta(itemMeta);
      if (item != null) {
         p.getInventory().addItem(item);
         p.sendMessage(CreateText.addColors("<red>You received a <white>" + item.getType().toString().replaceAll("_", " ").toLowerCase() + "<gray>."));
      }
   }
   private void handleBuy(InventoryClickEvent event, String buyString) {
      Player p = (Player) event.getWhoClicked();
      double buyPrice;
      try {
         buyPrice = Double.parseDouble(buyString);
      } catch (NumberFormatException e) {
         Bukkit.getLogger().warning("Invalid buy price format: " + buyString);
         return;
      }

      if (Keys.money.get(p, 0.0) >= buyPrice) {
         Keys.money.subtractDouble(p, buyPrice);
         ItemStack item = event.getCurrentItem();
         ItemMeta meta = item.getItemMeta();
         List<String> lores = item.getItemMeta().getLore();
         for(String lore : lores)
            lore.replaceAll(CreateText.addColors("<green>Buy $"+buyPrice), "");
         meta.setLore(lores);
         item.setItemMeta(meta);
         p.getInventory().addItem(item);
      } else {
         p.sendMessage(CreateText.addColors("<red>You don't have enough money! <gray>(<white>Cost: " + FormatUtils.formatMoney(buyPrice) + "<gray>)"));
      }
   }
   private int getRoyalPatrollers(){
      int result = 0;
      for(Entity entity : Bukkit.getWorlds().get(0).getEntities())
         if(entity.getType().equals(EntityType.ZOMBIE)) result++;
      return result;
   }
   private boolean buy(double cost, Player p){
      if(Keys.money.get(p, 0.0) >= cost) {
         Keys.money.subtractDouble(p, cost);
         return true;
      }
      return false;
   }
}
