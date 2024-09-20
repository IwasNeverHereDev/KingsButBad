package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Item;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

import static kingsbutbad.kingsbutbad.tasks.ScheduleTask.bossbar;

@SuppressWarnings("deprecation")
public class PlayerInteractAtEntityListener implements Listener { // TODO: Clean up This File (PlayerInteractAtEntityListener.java)
   @EventHandler
   public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
      if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND))
         event.setCancelled(true);
      if(event.getPlayer().getGameMode() == GameMode.SPECTATOR){
         event.getPlayer().sendMessage(CreateText.addColors("<red>You can't open GUIS while respawning!"));
         event.setCancelled(true);
         return;
      }
      if(event.getRightClicked() instanceof Player p){
         if(event.getPlayer().getItemInHand().getType().equals(Material.POTION)){
            Player giver = event.getPlayer();
            giver.getInventory().setItemInMainHand(new ItemStack(Material.GLASS_BOTTLE));
            giver.sendMessage(CreateText.addColors("<gray>You gave "+p.getName()+" water!"));
           p.sendMessage(CreateText.addColors("<gray>You were given water by "+giver.getName()));
            KingsButBad.thirst.put(p, KingsButBad.thirst.getOrDefault(p, 0F) + 100);
         }
      }

      if (event.getRightClicked().getType().equals(EntityType.ITEM_FRAME))
         event.setCancelled(true);

      if (event.getRightClicked().getType().equals(EntityType.VILLAGER)) {
         if(Keys.vanish.get(event.getPlayer(), false)) {
            event.getPlayer().sendMessage(CreateText.addColors("<gray>The NPC can't see vanished players!"));
            event.setCancelled(true);
            return;
         }
         Bukkit.getScheduler()
            .runTaskLater(
               KingsButBad.getPlugin(KingsButBad.class),
               () -> {
                  event.getPlayer().closeInventory();
                  event.setCancelled(true);
                  event.setCancelled(true);
                  if (event.getRightClicked().getType().equals(EntityType.VILLAGER)) {
                     Player player = event.getPlayer();
                     Villager villager = (Villager) event.getRightClicked();
                     Block block = villager.getLocation().add(0, -2, 0).getBlock();

                     if (block.getType() == Material.CHEST) {
                        Chest chest = (Chest) block.getState();
                        Inventory chestInventory = chest.getInventory();

                        Inventory guiInventory = Bukkit.createInventory(null, 27, chest.getCustomName() + " GUI");
                        guiInventory.setContents(chestInventory.getContents());

                        player.openInventory(guiInventory);
                        return;
                     }
                  }
                  if (event.getRightClicked().equals(KingsButBad.servant)) {
                     Inventory inv = Bukkit.createInventory(null, 9);
                     ItemStack cod = new ItemStack(Material.IRON_NUGGET);
                     cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     ItemMeta codmeta = cod.getItemMeta();
                     codmeta.setDisplayName(ChatColor.GOLD + "Become a " + KingsButBad.kingPrefix.toUpperCase() + ChatColor.GOLD + "'s servant");
                     ArrayList<String> codlore = new ArrayList<>();
                     codlore.add(ChatColor.GRAY + "Makes you a servant.");
                     codlore.add(CreateText.addColors("<red>Listen to the king's orders. This <red>isn't <gray> a role of power."));
                     codmeta.setLore(codlore);
                     cod.setItemMeta(codmeta);
                     inv.setItem(4, cod);
                     event.getPlayer().openInventory(inv);
                  }
                  if(event.getRightClicked().equals(KingsButBad.bmPrisonTrader)){
                     if(!KingsButBad.roles.get(event.getPlayer()).equals(Role.PRISONER)) {event.getPlayer().sendMessage(CreateText.addColors("<gray>You need to be Prisoner to talk to me."));return;}
                     Inventory inv = Bukkit.createInventory(null, 9*3, CreateText.addColors("<gold>Prisoner Trader"));
                     List<String> lore = new ArrayList<>();
                     lore.add("");
                     lore.add("<gray>Removes -25 seconds.");
                     lore.add("");
                     lore.add("<green>Cost<gray>: <green>5 Coal");
                     lore.add("");
                     List<String> lore1 = new ArrayList<>();
                     lore1.add("");
                     lore1.add("<gray>Able to Escape using a key!");
                     lore1.add("");
                     lore1.add("<green>Cost<gray>: <green>35 Coal");
                     lore1.add("");
                     ItemStack time = Item.createItem(Material.COAL, "<gray>Remove some time", lore, null);
                     ItemStack soon = Item.createItem(Material.BARRIER, "<red>#suggestions a idea for this slot!", new ArrayList<>(), null);
                     ItemStack key = Item.createItem(Material.PAPER, "<gray>Make-Shift Key", lore1, null);
                     inv.setItem(10, time);
                     inv.setItem(13, soon);
                     inv.setItem(16, key);
                     event.getPlayer().openInventory(inv);
                  }
                  if(event.getRightClicked().equals(KingsButBad.mafiaRecruiter)){
                     if(KingsButBad.roles.get(event.getPlayer()).isPowerful) return;
                     Inventory inv = Bukkit.createInventory(null, 9*3, CreateText.addColors("<gold>Outlaw Recruiter"));
                     ItemStack dye = new ItemStack(Material.ORANGE_DYE);
                     ItemMeta dyem = dye.getItemMeta();
                     dyem.setDisplayName(CreateText.addColors("<gold>Join the Outlaw(s)!"));
                     dye.setItemMeta(dyem);
                     inv.setItem(13, dye);
                     event.getPlayer().openInventory(inv);
                  }
                  if (event.getRightClicked().equals(KingsButBad.royalServant) && RoleManager.isKingAtAll(event.getPlayer())) {
                     int zombiecount = 0;

                     for (LivingEntity le : Bukkit.getWorld("world").getLivingEntities()) {
                        if (le.getType().equals(EntityType.ZOMBIE)) {
                           zombiecount++;
                        }
                     }

                     Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Royal Servant");
                     ItemStack cod = new ItemStack(Material.IRON_SHOVEL);
                     cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     ItemMeta codmeta = cod.getItemMeta();
                     codmeta.setDisplayName(ChatColor.GOLD + "Hire Royal Patroler");
                     ArrayList<String> codlore = new ArrayList<>();
                     codlore.add(ChatColor.GRAY + "Hire a Royal Patroler.");
                     codlore.add(ChatColor.GRAY + "Patrolers find and kill criminals.");
                     codlore.add(ChatColor.GRAY + "Royal Patrollers Active: " + zombiecount);
                     codlore.add(ChatColor.GREEN + "$150");
                     codmeta.setLore(codlore);
                     cod.setItemMeta(codmeta);
                     if (zombiecount < 5) {
                        inv.setItem(10, cod);
                     } else {
                        cod = new ItemStack(Material.RED_STAINED_GLASS);
                        cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        codmeta = cod.getItemMeta();
                        codmeta.setDisplayName(ChatColor.GOLD + "Royal Patroller");
                        codlore = new ArrayList<>();
                        codlore.add(ChatColor.GRAY + "Capped at 5 Patrollers!");
                        codmeta.setLore(codlore);
                        cod.setItemMeta(codmeta);
                        inv.setItem(10, cod);
                     }

                     if (!KingsButBad.coalCompactor) {
                        cod = new ItemStack(Material.DEEPSLATE_COAL_ORE);
                        cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        codmeta = cod.getItemMeta();
                        codmeta.setDisplayName(ChatColor.GOLD + "Coal Compactor");
                        codlore = new ArrayList<>();
                        codlore.add(ChatColor.GRAY + "Gain 5$ every time a prisoner mines coal.");
                        codlore.add(ChatColor.GREEN + "$150");
                     } else {
                        cod = new ItemStack(Material.RED_STAINED_GLASS);
                        cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        codmeta = cod.getItemMeta();
                        codmeta.setDisplayName(ChatColor.GOLD + "Coal Compactor");
                        codlore = new ArrayList<>();
                        codlore.add(ChatColor.GRAY + "Already Bought!");
                     }
                      codmeta.setLore(codlore);
                      cod.setItemMeta(codmeta);
                      inv.setItem(13, cod);

                      if (!KingsButBad.mineUnlocked) {
                        cod = new ItemStack(Material.OAK_FENCE);
                        cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        codmeta = cod.getItemMeta();
                        codmeta.setDisplayName(ChatColor.GOLD + "Mines");
                        codlore = new ArrayList<>();
                        codlore.add(ChatColor.GRAY + "Unlocks the mining job.");
                        codlore.add(ChatColor.GREEN + "$150");
                      } else {
                        cod = new ItemStack(Material.RED_STAINED_GLASS);
                        cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        codmeta = cod.getItemMeta();
                        codmeta.setDisplayName(ChatColor.GOLD + "Mines");
                        codlore = new ArrayList<>();
                        codlore.add(ChatColor.GRAY + "Already Bought!");
                      }
                      codmeta.setLore(codlore);
                      cod.setItemMeta(codmeta);
                      inv.setItem(14, cod);

                      if (!KingsButBad.joesUnlocked) {
                        cod = new ItemStack(Material.LIGHT_BLUE_WOOL);
                        cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        codmeta = cod.getItemMeta();
                        codmeta.setDisplayName(ChatColor.GOLD + "Buy Little Joe's Shack");
                        codlore = new ArrayList<>();
                        codlore.add(ChatColor.GRAY + "Allows people to shop from Little Joe's to upgrade");
                        codlore.add(ChatColor.GRAY + "Tools.");
                        codlore.add(ChatColor.GREEN + "$200");
                      } else {
                        cod = new ItemStack(Material.RED_STAINED_GLASS);
                        cod.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        codmeta = cod.getItemMeta();
                        codmeta.setDisplayName(ChatColor.GOLD + "Little Joe's Shack");
                        codlore = new ArrayList<>();
                        codlore.add(ChatColor.GRAY + "Already Bought!");
                      }
                      codmeta.setLore(codlore);
                      cod.setItemMeta(codmeta);
                      inv.setItem(15, cod);

                      event.getPlayer().openInventory(inv);
                  }

                  if (event.getRightClicked().equals(KingsButBad.littleJoes)) {
                     Inventory invx = Bukkit.createInventory(null, 9);
                     ItemStack codx = new ItemStack(Material.GOLDEN_CARROT, 16);
                     codx.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     ItemMeta codmetax = codx.getItemMeta();
                     codmetax.setDisplayName(ChatColor.GOLD + "Joe's Snax");
                     ArrayList<String> codlorex = new ArrayList<>();
                     codlorex.add(ChatColor.GRAY + "small man, small bites");
                     codlorex.add(ChatColor.GREEN + "$32");
                     codmetax.setLore(codlorex);
                     codx.setItemMeta(codmetax);
                     invx.setItem(1, codx);
                     codx = new ItemStack(Material.ENCHANTED_BOOK);
                     codx.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     codmetax = codx.getItemMeta();
                     codmetax.setDisplayName(ChatColor.GOLD + "Fortune 1 On all Hoes");
                     codlorex = new ArrayList<>();
                     codlorex.add(ChatColor.GREEN + "Applies to every Hoe in your inventory");
                     codlorex.add(ChatColor.GREEN + "$400");
                     codmetax.setLore(codlorex);
                     codx.setItemMeta(codmetax);
                     invx.setItem(3, codx);
                     codx = new ItemStack(Material.ENCHANTED_BOOK);
                     codx.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     codmetax = codx.getItemMeta();
                     codmetax.setDisplayName(ChatColor.GOLD + "Lure 2 On all Fishing Rods");
                     codlorex = new ArrayList<>();
                     codlorex.add(ChatColor.GREEN + "Applies to every Fishing Rod in your inventory");
                     codlorex.add(ChatColor.GREEN + "$400");
                     codmetax.setLore(codlorex);
                     codx.setItemMeta(codmetax);
                     invx.setItem(5, codx);
                     codx = new ItemStack(Material.ENCHANTED_BOOK);
                     codx.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     codmetax = codx.getItemMeta();
                     codmetax.setDisplayName(ChatColor.GOLD + "Efficiency 2 On all Pickaxes");
                     codlorex = new ArrayList<>();
                     codlorex.add(ChatColor.GREEN + "Applies to every Pickaxe in your inventory");
                     codlorex.add(ChatColor.GREEN + "$400");
                     codmetax.setLore(codlorex);
                     codx.setItemMeta(codmetax);
                     invx.setItem(7, codx);
                     event.getPlayer().openInventory(invx);
                  }

                  if (event.getRightClicked().equals(KingsButBad.royalTrader)) {
                     Inventory invx = Bukkit.createInventory(null, 9);
                     ItemStack codx = new ItemStack(Material.PAPER);
                     codx.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     ItemMeta codmetax = codx.getItemMeta();
                     codmetax.setDisplayName(ChatColor.GOLD + "Get-Out-Of-Jail-Free Card");
                     ArrayList<String> codlorex = new ArrayList<>();
                     codlorex.add(ChatColor.GRAY + "Stops you from going to jail as a criminal");
                     codlorex.add(ChatColor.GRAY + "if you die.");
                     codlorex.add(
                        CreateText.addColors(
                           "<gray>(note; this will still <obfuscated>LOGFEB31-BRTRUD.. 1: Prisoner Emily, We need you. 1: Don't worry. You'll be let out actually. 1: The warden knows about this. 1: Apparently, they want you to go to a... Storage facility? Strange. 1: They want you to go... on the 31st of feburary? 1: Yeah.. That's not even a day.. 1: Shit. He's calling, I better stop before we get caught talking.</obfuscated>"
                        )
                     );
                     codlorex.add(ChatColor.GREEN + "$1500");
                     codmetax.setLore(codlorex);
                     codx.setItemMeta(codmetax);
                     invx.setItem(1, codx);
                     codx = new ItemStack(Material.PLAYER_HEAD);
                     codx.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     codmetax = codx.getItemMeta();
                     SkullMeta sm = (SkullMeta)codmetax;
                     sm.setOwningPlayer(Bukkit.getOfflinePlayer("Piggopet"));
                     sm.setDisplayName(ChatColor.GOLD + "Piggopet's Head");
                     codlorex = new ArrayList<>();
                     codlorex.add(ChatColor.GRAY + "piggopet reference");
                     codlorex.add(ChatColor.GREEN + "$priceless");
                     sm.setLore(codlorex);
                     codx.setItemMeta(sm);
                     invx.setItem(3, codx);
                     codx = new ItemStack(Material.CHAINMAIL_HELMET);
                     codx.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     codmetax = codx.getItemMeta();
                     codmetax.setDisplayName(ChatColor.GOLD + "Stronger Gear");
                     codlorex = new ArrayList<>();
                     codlorex.add(ChatColor.GREEN + "Cost: $2.5k");
                     codmetax.setLore(codlorex);
                     codx.setItemMeta(codmetax);
                     invx.setItem(5, codx);
                     codx = new ItemStack(Material.IRON_SWORD);
                     codx.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     codmetax = codx.getItemMeta();
                     codmetax.setDisplayName(ChatColor.GOLD + "Iron Sword");
                     codlorex = new ArrayList<>();
                     codlorex.add(ChatColor.GRAY + "A simple sword for simple soldiers");
                     codlorex.add(ChatColor.GREEN + "$300");
                     codmetax.setLore(codlorex);
                     codx.setItemMeta(codmetax);
                     invx.setItem(7, codx);
                     event.getPlayer().openInventory(invx);
                  }



                  if (event.getRightClicked().equals(KingsButBad.archerJohn)) {
                     Inventory invx = Bukkit.createInventory(null, 9);
                     ItemStack codx = new ItemStack(Material.ARROW);
                     codx.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     codx.setAmount(32);
                     ItemMeta codmetax = codx.getItemMeta();
                     codmetax.setDisplayName(ChatColor.GOLD + "Bunch o' arrows");
                     ArrayList<String> codlorex = new ArrayList<>();
                     codlorex.add(ChatColor.GRAY + "silly little arrows");
                     codlorex.add(ChatColor.GREEN + "$50");
                     codmetax.setLore(codlorex);
                     codx.setItemMeta(codmetax);
                     invx.setItem(1, codx);
                     codx = new ItemStack(Material.BOW);
                     codx.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     codmetax = codx.getItemMeta();
                     codmetax.setDisplayName(ChatColor.GOLD + "Bow");
                     codlorex = new ArrayList<>();
                     codlorex.add(ChatColor.GRAY + "A bow. You hold it, pull it back, rip the space-time");
                     codlorex.add(ChatColor.GRAY + "continum, and shoot! Atleast, that's what i do.");
                     codlorex.add(ChatColor.GREEN + "$100");
                     codmetax.setLore(codlorex);
                     codx.setItemMeta(codmetax);
                     invx.setItem(3, codx);
                     codx = new ItemStack(Material.ENCHANTED_BOOK);
                     codx.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     codmetax = codx.getItemMeta();
                     codmetax.setDisplayName(ChatColor.GOLD + "+1 Power Level");
                     codlorex = new ArrayList<>();
                     codlorex.add(ChatColor.GREEN + "Applies to every bow in your inventory");
                     codlorex.add(ChatColor.GREEN + "$400");
                     codmetax.setLore(codlorex);
                     codx.setItemMeta(codmetax);
                     invx.setItem(5, codx);
                     codx = new ItemStack(Material.YELLOW_CONCRETE);
                     codx.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     codmetax = codx.getItemMeta();
                     codmetax.setDisplayName(ChatColor.GOLD + "Talk");
                     codlorex = new ArrayList<>();
                     codlorex.add(ChatColor.GRAY + "Talk to archer john. See how he's doing.");
                     codmetax.setLore(codlorex);
                     codx.setItemMeta(codmetax);
                     invx.setItem(7, codx);
                     event.getPlayer().openInventory(invx);
                  }


                  if (event.getRightClicked().equals(KingsButBad.lunchLady)
                     && (bossbar.getTitle().equals("Lunch") || bossbar.getTitle().equals("Breakfast"))) {
                     event.getPlayer().sendMessage(CreateText.addColors("<gold>Lunch Lady <white><b>>> Here's your lunch."));
                     event.getPlayer().getInventory().addItem(new ItemStack(Material.BEETROOT_SOUP));
                  }

                  if (event.getRightClicked().equals(KingsButBad.miner)) {
                     Inventory invx = Bukkit.createInventory(null, 9);
                     ItemStack hoe = new ItemStack(Material.STONE_PICKAXE);
                     hoe.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     ItemMeta hoemeta = hoe.getItemMeta();
                     hoemeta.setDisplayName(ChatColor.BLUE + "Get Pickaxe");
                     hoe.setItemMeta(hoemeta);
                     invx.setItem(2, hoe);
                     ItemStack wheat = new ItemStack(Material.COAL_ORE);
                     wheat.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     ItemMeta wheatmeta = wheat.getItemMeta();
                     wheatmeta.setDisplayName(ChatColor.GOLD + "Sell Ores");
                     wheat.setItemMeta(wheatmeta);
                     invx.setItem(3, wheat);
                     event.getPlayer().openInventory(invx);
                     hoe = new ItemStack(Material.MINECART);
                     hoe.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     hoemeta = hoe.getItemMeta();
                     hoemeta.setDisplayName(ChatColor.BLUE + "Back to Surface");
                     hoe.setItemMeta(hoemeta);
                     invx.setItem(6, hoe);
                     event.getPlayer().openInventory(invx);
                  }

                  if (event.getRightClicked().equals(KingsButBad.prisonGuard)) {
                     Inventory invx = Bukkit.createInventory(null, 9);
                     ItemStack hoe = new ItemStack(Material.RED_CONCRETE);
                     hoe.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     ItemMeta hoemeta = hoe.getItemMeta();
                     hoemeta.setDisplayName(ChatColor.BLUE + "Turn Yourself In" + ChatColor.GRAY + " (for some reason)");
                     hoe.setItemMeta(hoemeta);
                     invx.setItem(2, hoe);
                     event.getPlayer().openInventory(invx);
                     hoe = new ItemStack(Material.MAP);
                     hoe.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                     hoemeta = hoe.getItemMeta();
                     hoemeta.setDisplayName(ChatColor.BLUE + "Prison Stats");
                     hoe.setItemMeta(hoemeta);
                     invx.setItem(6, hoe);
                     event.getPlayer().openInventory(invx);
                  }

                  if (event.getRightClicked().equals(KingsButBad.royalVillager))
                     event.getPlayer().performCommand("king help");
               },
               1L
            );
      }
   }
}
