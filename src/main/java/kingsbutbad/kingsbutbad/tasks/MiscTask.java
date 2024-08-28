package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static kingsbutbad.kingsbutbad.utils.FormatUtils.formatMoney;

public class MiscTask extends BukkitRunnable {
   public static HashMap<Player, Float> stamina = new HashMap<>();
   HashMap<Player, Boolean> regenstamina = new HashMap<>();
   public static ArrayList<Location> cells = new ArrayList<>();
   public static BossBar bossbar = Bukkit.createBossBar("??? TIME", BarColor.WHITE, BarStyle.SOLID, new BarFlag[0]);
   public static BossBar bar = Bukkit.createBossBar("KingHealth", BarColor.WHITE, BarStyle.SOLID, new BarFlag[0]);
   Integer timer1 = 0;
   Integer timer2 = 1000;

   public void run() {
      KingsButBad.cooldown--;
      if (KingsButBad.littleJoes != null) {
         if (KingsButBad.joesUnlocked) {
            KingsButBad.littleJoes.teleport(KingdomsLoader.activeKingdom.getLittleJoesLocation());
         } else {
            KingsButBad.littleJoes.teleport(new Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0, 0.0F, 0.0F));
         }
      }

      if (Bukkit.getWorld("world").getTime() > 0L && Bukkit.getWorld("world").getTime() < 2000L) {
         this.timer1 = 0;
         this.timer2 = 2500;
         bossbar.setColor(BarColor.RED);
         bossbar.setTitle("ROLL CALL");

         for (Player p : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.roles.get(p).equals(Role.PRISONER)) {
               WorldBorder rollborder = Bukkit.createWorldBorder();
               rollborder.setCenter(new Location(Bukkit.getWorld("world"), -140.0, -57.0, 15.0));
               rollborder.setSize(3.0);
               rollborder.setDamageAmount(0.4);
               rollborder.setDamageBuffer(0.0);
               if (KingsButBad.isInside(
                  p, new Location(Bukkit.getWorld("world"), -139.0, -57.0, 16.0), new Location(Bukkit.getWorld("world"), -142.0, -57.0, 13.0)
               )) {
                  if (!rollborder.isInside(p.getLocation())) {
                     p.damage(1.0);
                  }
                  p.setWorldBorder(rollborder);
               } else if (!Objects.equals(p.getWorldBorder(), rollborder)) {
                  p.setWorldBorder(null);
               }
            }
         }
      }

      if (Bukkit.getWorld("world").getTime() > 2000L && Bukkit.getWorld("world").getTime() < 4000L) {
         this.timer1 = 2000;
         this.timer2 = 4000;
         bossbar.setColor(BarColor.WHITE);

         for (Player px : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.roles.get(px).equals(Role.PRISONER)) {
               WorldBorder rollborder = Bukkit.createWorldBorder();
               rollborder.setCenter(new Location(Bukkit.getWorld("world"), -153.0, -58.0, 3.0));
               rollborder.setSize(18.0);
               rollborder.setDamageAmount(0.4);
               rollborder.setDamageBuffer(0.0);
               if (KingsButBad.isInside(
                  px, new Location(Bukkit.getWorld("world"), -144.0, -58.0, 5.0), new Location(Bukkit.getWorld("world"), -155.0, -53.0, -10.0)
               )) {
                  if (!rollborder.isInside(px.getLocation())) {
                     px.damage(1.0);
                  }

                  px.setWorldBorder(rollborder);
               } else if (!Objects.equals(px.getWorldBorder(), rollborder)) {
                  px.setWorldBorder(null);
               }
            }
         }

         bossbar.setTitle("Breakfast");
      }

      if (Bukkit.getWorld("world").getTime() > 4000L && Bukkit.getWorld("world").getTime() < 7000L) {
         this.timer1 = 4000;
         this.timer2 = 7000;
         bossbar.setColor(BarColor.WHITE);
         bossbar.setTitle("Free Time");

         for (Player pxx : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.roles.get(pxx).equals(Role.PRISONER) && pxx.getWorldBorder() != null) {
               pxx.setWorldBorder(null);
            }
         }
      }

      if (Bukkit.getWorld("world").getTime() >= 7000L && Bukkit.getWorld("world").getTime() <= 7005L) {
         for (Player pxxx : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.roles.get(pxxx).equals(Role.PRISONER)) {
               KingsButBad.prisonQuota.put(pxxx, 30);
            }
         }
      }

      if (Bukkit.getWorld("world").getTime() == 10000L) {
         for (Player pxxxx : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.prisonQuota.getOrDefault(pxxxx, 0) > 0 && KingsButBad.roles.get(pxxxx).equals(Role.PRISONER)) {
               pxxxx.sendTitle(ChatColor.RED + "MISSED QUOTA.", ChatColor.DARK_RED + "+80s to prison time.");
               KingsButBad.prisonTimer.put(pxxxx, KingsButBad.prisonTimer.get(pxxxx) + 80);
            }
         }
      }

      if (Bukkit.getWorld("world").getTime() > 7000L && Bukkit.getWorld("world").getTime() < 10000L) {
         this.timer1 = 7000;
         this.timer2 = 10000;
         bossbar.setColor(BarColor.WHITE);
         bossbar.setTitle("Job Time");

         for (Player player : Bukkit.getOnlinePlayers()) {
            WorldBorder rollborder = Bukkit.createWorldBorder();
            rollborder.setCenter(new Location(Bukkit.getWorld("world"), -150.0, -49.0, 13.0));
            rollborder.setSize(15.0);
            rollborder.setDamageAmount(0.4);
            rollborder.setDamageBuffer(0.0);
            if (KingsButBad.roles.get(player).equals(Role.PRISONER)) {
               if (KingsButBad.isInside(
                  player, new Location(Bukkit.getWorld("world"), -142.0, -50.0, 6.0), new Location(Bukkit.getWorld("world"), -157.0, -58.0, 20.0)
               )) {
                  if (!rollborder.isInside(player.getLocation())) {
                     player.damage(1.0);
                  }

                  player.setWorldBorder(rollborder);
               } else if (!Objects.equals(player.getWorldBorder(), rollborder)) {
                  player.setWorldBorder(null);
               }
            }
         }
      }

      if (Bukkit.getWorld("world").getTime() > 10000L && Bukkit.getWorld("world").getTime() < 13000L) {
         this.timer1 = 10000;
         this.timer2 = 13000;
         bossbar.setColor(BarColor.WHITE);

         for (Player player : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.roles.get(player).equals(Role.PRISONER)) {
               WorldBorder rollborder = Bukkit.createWorldBorder();
               rollborder.setCenter(new Location(Bukkit.getWorld("world"), -153.0, -58.0, 3.0));
               rollborder.setSize(18.0);
               rollborder.setDamageAmount(0.4);
               rollborder.setDamageBuffer(0.0);
               if (KingsButBad.isInside(
                  player, new Location(Bukkit.getWorld("world"), -144.0, -58.0, 5.0), new Location(Bukkit.getWorld("world"), -155.0, -53.0, -10.0)
               )) {
                  if (!rollborder.isInside(player.getLocation())) {
                     player.damage(1.0);
                  }

                  player.setWorldBorder(rollborder);
               } else if (!Objects.equals(player.getWorldBorder(), rollborder)) {
                  player.setWorldBorder(null);
               }
            }
         }

         bossbar.setTitle("Lunch");
      }

      if (Bukkit.getWorld("world").getTime() > 13000L && Bukkit.getWorld("world").getTime() < 15000L) {
         this.timer1 = 13000;
         this.timer2 = 15000;
         bossbar.setColor(BarColor.RED);
         bossbar.setTitle("EVENING ROLL CALL");

         for (Player pxxxxxxx : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.roles.get(pxxxxxxx).equals(Role.PRISONER)) {
               WorldBorder rollborder = Bukkit.createWorldBorder();
               rollborder.setCenter(new Location(Bukkit.getWorld("world"), -140.0, -57.0, 15.0));
               rollborder.setSize(3.0);
               rollborder.setDamageAmount(0.4);
               rollborder.setDamageBuffer(0.0);
               if (KingsButBad.isInside(
                  pxxxxxxx, new Location(Bukkit.getWorld("world"), -139.0, -57.0, 16.0), new Location(Bukkit.getWorld("world"), -142.0, -57.0, 13.0)
               )) {
                  if (!rollborder.isInside(pxxxxxxx.getLocation())) {
                     pxxxxxxx.damage(1.0);
                  }

                  pxxxxxxx.setWorldBorder(rollborder);
               }
            }
         }
      }

      if (Bukkit.getWorld("world").getTime() > 15000L && Bukkit.getWorld("world").getTime() < 18000L) {
         this.timer1 = 15000;
         this.timer2 = 18000;
         bossbar.setColor(BarColor.PINK);
         bossbar.setTitle("Cell Time");

         for (Player player : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.roles.get(player).equals(Role.PRISONER) && player.getWorldBorder() != null) {
               player.setWorldBorder(null);
            }
         }

         Integer prisonersnotincell = 0;

         for (Player player : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.roles.get(player).equals(Role.PRISONER)
               && !KingsButBad.isInside(
                  player, new Location(Bukkit.getWorld("world"), -136.0, -53.0, -6.0), new Location(Bukkit.getWorld("world"), -132.0, -57.0, 23.0)
               )) {
               player.sendTitle("", CreateText.addColors("<red><b>GET IN YOUR CELL, FILTH!"), 0, 20, 0);
               prisonersnotincell = prisonersnotincell + 1;
            }
         }

         for (Player player : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.roles.get(player).equals(Role.PRISON_GUARD) && prisonersnotincell != 0) {
               player.sendTitle("", CreateText.addColors("<red><b>" + prisonersnotincell + " PRISONERS ARE NOT IN THEIR CELLS!"), 0, 20, 0);
               prisonersnotincell = prisonersnotincell + 1;
            }
         }
      }

      if (Bukkit.getWorld("world").getTime() > 18000L && Bukkit.getWorld("world").getTime() < 24000L) {
         this.timer1 = 18000;
         this.timer2 = 24000;
         bossbar.setColor(BarColor.RED);
         bossbar.setTitle("LIGHTS OUT");
         Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 1L);
      }

      int currentTime = (int) Bukkit.getWorld("world").getTime();
      int startTime = this.timer1.intValue();
      int endTime = this.timer2.intValue();

      if (endTime > startTime) {
         double progress = (double)(currentTime - startTime) / (endTime - startTime);
         progress = Math.max(0.0, Math.min(1.0, progress));
         bossbar.setProgress(progress);
      } else {
         bossbar.setProgress(0.0);
      }

      for (Player player : Bukkit.getOnlinePlayers()) {
         if(!Keys.money.has(player))
            Keys.money.set(player, 0.0);

         if (!KingsButBad.roles.containsKey(player)) {
            KingsButBad.roles.put(player, Role.PEASANT);
            RoleManager.givePlayerRole(player);
         }
      }

      this.cells.clear();
      this.cells.addAll(KingdomsLoader.activeKingdom.getCells());
      if (Bukkit.getWorld("world").getTime() <= 18000L) {
         Bukkit.getWorld("world").getBlockAt(KingdomsLoader.activeKingdom.getPrisonLightPowerBlock()).setType(Material.REDSTONE_BLOCK);
      } else {
         Bukkit.getWorld("world").getBlockAt(KingdomsLoader.activeKingdom.getPrisonLightPowerBlock()).setType(Material.AIR);
      }

      if (KingsButBad.king == null || !KingsButBad.king.isOnline() || KingsButBad.king.isDead()) {
         KingsButBad.king = null;
         KingsButBad.king2 = null;
      }

      if (KingsButBad.king != null && KingsButBad.king.getInventory().getHelmet() == null) {
         KingsButBad.king.setItemOnCursor(new ItemStack(Material.AIR));
         KingsButBad.king = null;
         KingsButBad.cooldown = 100;
         Bukkit.broadcastMessage(
            CreateText.addColors(
               "<red><b>>><b> THE <gradient:#FFFF52:#FFBA52><b>"
                  + KingsButBad.kingGender.toUpperCase()
                  + "<b></gradient><b><red> HAS RESIGNED! <#A52727>Use /king to become the king.."
            )
         );

         for (Player player : Bukkit.getOnlinePlayers()) {
            if (KingsButBad.roles.get(player) != Role.PEASANT) {
               KingsButBad.roles.put(player, Role.PEASANT);
               RoleManager.givePlayerRole(player);
            }
         }
      }

      for (Player player : Bukkit.getOnlinePlayers()) {
         KingsButBad.thirst.putIfAbsent(player, 300);
         if (KingsButBad.thirst.get(player) <= 0) {
            if(!Keys.vanish.get(player, false) || player.getGameMode().equals(GameMode.ADVENTURE)) {
               KingsButBad.thirst.put(player, 0);
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "damage " + player.getName() + " 3 minecraft:dry_out");
            }
         }

         if (!KingsButBad.roles.containsKey(player)
            && (KingsButBad.invitations.get(player).equals(Role.PRISONER) || KingsButBad.invitations.get(player).equals(Role.PRISON_GUARD))) {
            KingsButBad.thirst.put(player, 300);
         }

         if (KingsButBad.thirst.get(player) > 300) {
            KingsButBad.thirst.put(player, 300);
         }

         player.setRemainingAir(KingsButBad.thirst.get(player));
         if (new Random().nextInt(0, 16) == 0) {
            KingsButBad.thirst.put(player, KingsButBad.thirst.get(player) - 1);
         }

         for (Entity e : player.getPassengers()) {
            LivingEntity le = (LivingEntity)e;
            le.setNoDamageTicks(3);
         }

         if(KingsButBad.isInside(player, KingdomsLoader.activeKingdom.getBmPrison1(), KingdomsLoader.activeKingdom.getBmPrison2())){
            for(Entity p : player.getPassengers()) {
               player.getPassengers().remove(p);
               p.teleport(KingdomsLoader.activeKingdom.getBlackMarketExitPrisoner());
            }
            player.teleport(KingdomsLoader.activeKingdom.getBlackMarketExitPrisoner());
         }

         if (player.isInsideVehicle() && player.getVehicle().isSneaking()) {
            player.leaveVehicle();
         }

         if (KingsButBad.roles.get(player).equals(Role.BODYGUARD)) {
            WorldBorder kingborder = Bukkit.createWorldBorder();
            kingborder.setCenter(KingsButBad.bodyLink.get(player).getLocation());
            kingborder.setSize(8.0);
            kingborder.setDamageAmount(0.4);
            kingborder.setDamageBuffer(0.0);
            if (!kingborder.isInside(player.getLocation())) {
               player.damage(1.0);
            }

            player.setWorldBorder(kingborder);
         }

         if (!KingsButBad.roles.get(player).equals(Role.BODYGUARD)
            && !KingsButBad.roles.get(player).equals(Role.PRISONER)
            && player.getWorldBorder() != null) {
            player.setWorldBorder(null);
         }

         if (KingsButBad.king != null) {
            bar.setTitle(CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingGender.toUpperCase() + " " + KingsButBad.king.getName().toUpperCase() + " <b></gradient> <aqua>[<dark_blue>"+KingsButBad.thirst.get(KingsButBad.king)+"<aqua>/<dark_blue>300<aqua>]"));
            bar.setStyle(BarStyle.SEGMENTED_20);
            bar.setProgress(KingsButBad.king.getHealth() / KingsButBad.king.getHealthScale());
            bar.setColor(BarColor.YELLOW);
            bar.addPlayer(player);
         } else {
            bar.removeAll();
         }

         player.setLevel(0);
         if (player.hasPotionEffect(PotionEffectType.LUCK)) {
            stamina.put(player, 0.99F);
         }

         if (!stamina.containsKey(player)) {
            stamina.put(player, 0.99F);
         }

         if (!this.regenstamina.containsKey(player)) {
            this.regenstamina.put(player, false);
         }

         if (stamina.get(player) <= 0.0F) {
            stamina.put(player, 0.0F);
         }

         player.setExp(stamina.get(player));
         player.setMaxHealth(20.0);
         if ((double)stamina.get(player).floatValue() >= 0.99) {
            this.regenstamina.put(player, false);
         }

         if (this.regenstamina.get(player)) {
            player.setFoodLevel(6);
            player.setWalkSpeed(0.09F);
            player.setFreezeTicks(30);
         } else {
            player.setFreezeTicks(0);
            player.setFoodLevel(19);
            if (!KingsButBad.roles.get(player).equals(Role.PRISONER)) {
               if (KingsButBad.king != null) {
                  if (!RoleManager.isKingAtAll(player)) {
                     if (KingsButBad.roles.get(player) != Role.BODYGUARD) {
                        player.setWalkSpeed(0.16F);
                     } else {
                        player.setWalkSpeed(0.2F);
                     }
                  } else {
                     player.setWalkSpeed(0.2F);
                  }
               } else {
                  player.setWalkSpeed(0.16F);
               }
            }
         }

         if (player.isSprinting() && player.getGameMode().equals(GameMode.ADVENTURE)) {
            if (stamina.get(player) <= 0.0F) {
               player.setFoodLevel(6);
               this.regenstamina.put(player, true);
               player.sendTitle(ChatColor.RED + "", ChatColor.DARK_RED + "regenerating stamina..", 20, 20, 20);
            } else {
               stamina.put(player, stamina.get(player) - 0.01F);
            }
         } else if (stamina.get(player) < 0.99F) {
            stamina.put(player, stamina.get(player) + 0.01F);
         }

         if (KingsButBad.isInside(
            player, KingdomsLoader.activeKingdom.getMineEntranceLoc1(), KingdomsLoader.activeKingdom.getMineEntranceLoc2())) {
            if (KingsButBad.mineUnlocked) {
               player.teleport(KingdomsLoader.activeKingdom.getMineExitLoc1());
            } else {
               player.teleport(KingdomsLoader.activeKingdom.getMineExitLoc2());
               player.sendMessage(ChatColor.RED + "That's not unlocked!");
            }
         }

         if (KingsButBad.roles.get(player).equals(Role.KNIGHT)) {
            Boolean hasHorseSpawned = false;

            for (Entity e : Bukkit.getWorld("world").getEntities()) {
               if (e.getCustomName() != null && e.getCustomName().equals(player.getName() + "'s horse")) {
                  hasHorseSpawned = true;
               }
            }

            if (!hasHorseSpawned) {
               if (!player.getInventory().contains(Material.CLAY_BALL)) {
                  ItemStack diamondchest = new ItemStack(Material.CLAY_BALL);
                  ItemMeta diamondchestmeta = diamondchest.getItemMeta();
                  diamondchestmeta.setDisplayName(CreateText.addColors("<gray>Spawn <gold>Horse"));
                  diamondchest.setItemMeta(diamondchestmeta);
                  player.getInventory().addItem(new ItemStack[]{diamondchest});
               }
            } else {
               player.getInventory().remove(Material.CLAY_BALL);
            }
         }

         String actiobarextras = "";
         if (KingsButBad.roles.get(player).equals(Role.CRIMINAl)) {
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(player);
            player.addPotionEffect(PotionEffectType.GLOWING.createEffect(40, 0));
         }

         if (KingsButBad.roles.get(player).equals(Role.KING)) {
            player.addPotionEffect(PotionEffectType.GLOWING.createEffect(40, 0));
         }

         if (KingsButBad.roles.get(player).equals(Role.PRISON_GUARD)
            && bossbar.getPlayers().contains(player)
            && bossbar.getTitle().equals("LIGHTS OUT")) {
            actiobarextras = actiobarextras + ChatColor.GRAY + " | " + CreateText.addColors("<red>It's lights out! <blue>You can leave the <gold>prison.");
         }

         if (KingsButBad.king != null) {
            KingsButBad.lastKing = KingsButBad.king;
         }

         if (KingsButBad.king2 != null) {
            KingsButBad.lastKing2 = KingsButBad.king2;
         }

         Role.KING.tag = CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingGender.toUpperCase() + "<b></gradient>");
         Role.KING.uncompressedColors = "<gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingGender.toUpperCase() + "<b></gradient>";
         DecimalFormat df = new DecimalFormat("#0.0");
         if (RoleManager.isKingAtAll(player)
            && player.getLocation().getBlock().getType().equals(Material.YELLOW_CARPET)
            ) {
            actiobarextras = actiobarextras + ChatColor.GRAY + " | " + CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>+0.25$ for sitting on the throne.");
            Keys.money.addDouble(player,0.25);
         }

         actiobarextras = actiobarextras
            + ChatColor.GRAY
            + " | "
            + CreateText.addColors(
               "<color:#26ff00><b>$</b><gradient:#26ff00:#61ffc0>"
                  + formatMoney(Keys.money.get(player, 0.0))
                     .replaceAll("\\$", "")
                  + " <dark_gray>(<gray>"
                  + df.format(Keys.money.get(player, 0.0))
                  + "<dark_gray>) "
            );
         if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            player.setGameMode(GameMode.ADVENTURE);
         }

         if (KingsButBad.roles.get(player).equals(Role.PRISONER)) {
            if (!player.hasCooldown(Material.TERRACOTTA)
               && !KingsButBad.isInside(
                  player, KingdomsLoader.activeKingdom.getPrison1(), KingdomsLoader.activeKingdom.getPrison2()
               )) {
               KingsButBad.roles.put(player, Role.PEASANT);
               player.removePotionEffect(PotionEffectType.WEAKNESS);
               Keys.inPrison.remove(player);
               Bukkit.broadcastMessage(CreateText.addColors("<red><b>>> " + player.getName() + " has escaped the prison!"));
               player.sendTitle(ChatColor.RED + "!!! You're now a criminal !!!", ChatColor.GRAY + "You escaped");
               KingsButBad.roles.put(player, Role.CRIMINAl);
               player.playSound(player, Sound.ENTITY_SILVERFISH_DEATH, 1.0F, 0.5F);
            }

            if (!DisguiseAPI.isDisguised(player)) {
               PlayerDisguise prisoner = new PlayerDisguise("leonrobiclone");
               prisoner.setName("Prisoner " + new Random().nextInt(1000, 9999));
               DisguiseAPI.disguiseEntity(player, prisoner);
               DisguiseAPI.setActionBarShown(player, false);
            }

            player.setFoodLevel(6);
            stamina.put(player, 0.99F);
            KingsButBad.prisonQuota.putIfAbsent(player, 0);
            KingsButBad.prisonTimer.put(player, KingsButBad.prisonTimer.getOrDefault(player, 0) - 1);
            String tooltip = "";
            if (KingsButBad.prisonQuota.get(player) > 0) {
               tooltip = tooltip
                  + CreateText.addColors("<gray> | <gold>MINE <red><b>" + KingsButBad.prisonQuota.get(player) + "<gold></b> BLOCKS! <gray>or +80s");
            }

            if (player.getLocation().getPitch() < 30.0F) {
               tooltip = tooltip + CreateText.addColors("<gray> | <red>Tip: Look down to go faster.");
               player.setWalkSpeed(0.02F);
            } else {
               player.setWalkSpeed(0.1F);
            }

            player.sendActionBar(
               CreateText.addColors("<gray>Sentence Left: <red><b>" + parseTicksToTime(KingsButBad.prisonTimer.get(player))) + tooltip
            );
            if (KingsButBad.prisonTimer.get(player) <= 0) {
               KingsButBad.roles.put(player, Role.PEASANT);
               RoleManager.givePlayerRole(player);
               player.removePotionEffect(PotionEffectType.WEAKNESS);
               Keys.inPrison.remove(player);
               Bukkit.broadcastMessage(CreateText.addColors("<gold>>> " + player.getName() + " served their prison sentence."));
            }
         } else {
            if (DisguiseAPI.isDisguised(player)) {
               DisguiseAPI.undisguiseToAll(player);
            }

            if (KingsButBad.king != null) {
               if (KingsButBad.king2 == null) {
                  player.sendActionBar(
                     CreateText.addColors(
                           "<gray>Current king<gray>: <gradient:#FFFF52:#FFBA52><b>"
                              + KingsButBad.kingGender.toUpperCase()
                              + " "
                              + KingsButBad.king.getName().toUpperCase()
                        )
                        + actiobarextras
                  );
               } else {
                  player.sendActionBar(
                     CreateText.addColors(
                           "<gray>Current king<gray>: <gradient:#FFFF52:#FFBA52><b>"
                              + KingsButBad.kingGender.toUpperCase()
                              + " "
                              + KingsButBad.king.getName().toUpperCase()
                              + "<dark_gray></b> &</gray><gradient:#FFFF52:#FFBA52><b> "
                              + KingsButBad.kingGender2.toUpperCase()
                              + " "
                              + KingsButBad.king2.getName().toUpperCase()
                        )
                        + actiobarextras
                  );
               }
            } else {
               String iscool = "<gradient:#ff2f00:#fcff3d><b>NO KING! Use /king to claim!";
               if (KingsButBad.cooldown > 0) {
                  iscool = "<gradient:#ff2f00:#fcff3d><b>On Cooldown... <gray>[" + parseTicksToTime(KingsButBad.prisonTimer.getOrDefault(player, 0)) + "]";
               }

               player.sendActionBar(CreateText.addColors("<gray>Current king<gray>: " + iscool) + actiobarextras);
            }
         }
      }
   }
   public static String parseTicksToTime(int ticks) {
      int totalSeconds = ticks / 20;
      int minutes = totalSeconds / 60;
      int seconds = totalSeconds % 60;

      // Formatting the time string as MM:SS
      return String.format("%02d:%02d", minutes, seconds);
   }

}
