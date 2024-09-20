package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.Advancements.AdvancementManager;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsLoader;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Pacts;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static kingsbutbad.kingsbutbad.tasks.ScheduleTask.bossbar;
import static kingsbutbad.kingsbutbad.utils.FormatUtils.formatMoney;

@SuppressWarnings("deprecation")
public class MiscTask extends BukkitRunnable {
   public static HashMap<Player, Float> stamina = new HashMap<>();
   HashMap<Player, Boolean> regenstamina = new HashMap<>();
   public static ArrayList<Location> cells = new ArrayList<>();
   public static BossBar king1HP = Bukkit.createBossBar("King1Health", BarColor.WHITE, BarStyle.SOLID);
   public static BossBar king2HP = Bukkit.createBossBar("King2Health", BarColor.WHITE, BarStyle.SOLID);

   public void run() {
      KingsButBad.cooldown--;
      if (KingsButBad.littleJoes != null) {
         if (KingsButBad.joesUnlocked) {
            KingsButBad.littleJoes.teleport(KingdomsLoader.activeKingdom.getLittleJoesLocation());
         } else {
            KingsButBad.littleJoes.teleport(new Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0, 0.0F, 0.0F));
         }
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
                  + KingsButBad.kingPrefix.toUpperCase()
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
         KingsButBad.thirst.putIfAbsent(player, 300F);
         if (KingsButBad.thirst.get(player) <= 0) {
            if(!Keys.vanish.get(player, false) && player.getGameMode().equals(GameMode.ADVENTURE)) {
               if(!player.isInvulnerable()) {
                  KingsButBad.thirst.put(player, 0F);
                  if(player.isInsideVehicle())
                     player.getVehicle().removePassenger(player);
                  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "damage " + player.getName() + " 5 minecraft:dry_out");
               }
            }
         }

         if(player.getStatistic(Statistic.BELL_RING) >= 100)
            AdvancementManager.giveAdvancement(player, "bell");
         if(player.isInWater())
            AdvancementManager.giveAdvancement(player, "dewater");

         if (!KingsButBad.roles.containsKey(player)
            && (KingsButBad.invitations.get(player).equals(Role.PRISONER) || KingsButBad.invitations.get(player).equals(Role.PRISON_GUARD))) {
            KingsButBad.thirst.put(player, 300F);
         }

         if (KingsButBad.thirst.get(player) > 300)
            KingsButBad.thirst.put(player, 300F);

         player.setRemainingAir(KingsButBad.thirst.get(player).intValue());
         if (new Random().nextInt(0, 16) == 0) {
            if(player.getNoDamageTicks() <= 0)
               if(Keys.activePact.get(player, "") == Pacts.PEASANT.name())
                  KingsButBad.thirst.put(player, KingsButBad.thirst.get(player) - 0.5F);
               else
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
            if(player.getVehicle() instanceof  Player user)
               user.setCooldown(Material.IRON_SHOVEL, 20*5);
         }

         if (KingsButBad.roles.get(player).equals(Role.BODYGUARD)) {
            WorldBorder kingborder = Bukkit.createWorldBorder();
            kingborder.setCenter(KingsButBad.bodyLink.get(player).getLocation());
            if(Keys.activePact.get(player,"") == Pacts.BODYGUARD.name())
               kingborder.setSize(20.0);
            else
               kingborder.setSize(10.0);
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
            king1HP.setTitle(CreateText.addColors("<gradient:#FFFF52:#FFBA52><b> "+KingsButBad.kingPrefix.toUpperCase() + " <gradient:#FFFF52:#FFBA52><b>" + KingsButBad.king.getName().toUpperCase() + " <b></gradient> <aqua>[<dark_blue>"+KingsButBad.thirst.get(KingsButBad.king)+"<aqua>/<dark_blue>300<aqua>]"));
            king1HP.setStyle(BarStyle.SEGMENTED_20);
            king1HP.setProgress(KingsButBad.king.getHealth() / KingsButBad.king.getHealthScale());
            king1HP.setColor(BarColor.YELLOW);
            king1HP.addPlayer(player);
         } else {
            king1HP.removeAll();
         }

         if (KingsButBad.king2 != null) {
            king2HP.setTitle(CreateText.addColors("<gradient:#FFFF52:#FFBA52><b> "+KingsButBad.kingPrefix2.toUpperCase() + " <gradient:#FFFF52:#FFBA52><b>" + KingsButBad.king2.getName().toUpperCase() + " <b></gradient> <aqua>[<dark_blue>"+KingsButBad.thirst.get(KingsButBad.king2)+"<aqua>/<dark_blue>300<aqua>]"));
            king2HP.setStyle(BarStyle.SEGMENTED_20);
            king2HP.setProgress(KingsButBad.king2.getHealth() / KingsButBad.king2.getHealthScale());
            king2HP.setColor(BarColor.YELLOW);
            king2HP.addPlayer(player);
         } else {
            king2HP.removeAll();
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
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20*3, 10));
            player.setWalkSpeed(0.09F);
         } else {
            player.removePotionEffect(PotionEffectType.HUNGER);
            player.setFoodLevel(19);
            if(Keys.activePact.get(player,"") == Pacts.BODYGUARD.name()) {
               player.setWalkSpeed(0.15F);
            }else {
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
         }

         if (player.isSprinting() && player.getGameMode().equals(GameMode.ADVENTURE) && !player.isInsideVehicle()) {
            if (stamina.get(player) <= 0.0F) {
               player.setFoodLevel(6);
               this.regenstamina.put(player, true);
               player.sendTitle(ChatColor.RED + "", ChatColor.DARK_RED + "regenerating stamina..", 0, 20*3, 0);
            } else {
               stamina.put(player, stamina.get(player) - 0.005F);
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
            boolean hasHorseSpawned = false;

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
         if (KingsButBad.roles.get(player).equals(Role.CRIMINAL)) {
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

         if(Keys.activePact.get(player, "") == Pacts.PRISON_GUARD.name() && !player.hasPotionEffect(PotionEffectType.WEAKNESS))
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20, 0));

         if(Keys.activePact.get(player, "") == Pacts.PRINCE.name()) {
            if(!player.hasPotionEffect(PotionEffectType.SPEED))
               player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0));
            if (player.getMaxHealth() >= 20)
               player.setMaxHealth(16);
         }else{
            player.setMaxHealth(20);
         }

         if(Keys.activePact.get(player, "") == Pacts.KING.name()){
            List<Entity> entityList = player.getNearbyEntities(3,5,3);
            for(Entity entity : entityList)
               if(entity instanceof Player others)
                  if(KingsButBad.roles.getOrDefault(others, Role.PEASANT) == Role.KING || KingsButBad.roles.getOrDefault(others, Role.PEASANT) == Role.PRINCE)
                     Keys.money.addDouble(player, 0.25);
         }

         if (KingsButBad.king != null) {
            KingsButBad.lastKing = KingsButBad.king.getUniqueId();
         }

         if (KingsButBad.king2 != null) {
            KingsButBad.lastKing2 = KingsButBad.king2.getUniqueId();
         }

         Role.KING.tag = CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingPrefix.toUpperCase() + "<b></gradient>");
         Role.KING.uncompressedColors = "<gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingPrefix.toUpperCase() + "<b></gradient>";
         DecimalFormat df = new DecimalFormat("#0.0");
         if (RoleManager.isKingAtAll(player)
            && player.getLocation().getBlock().getType().equals(Material.YELLOW_CARPET)
            ) {
            actiobarextras = actiobarextras + ChatColor.GRAY + " | " + CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>+0.25$ for being on the throne.");
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
         if(KingsButBad.roles.get(player).equals(Role.CRIMINAL)){
            if(KingsButBad.isInside(player, KingdomsLoader.activeKingdom.getPrison1(), KingdomsLoader.activeKingdom.getPrison2())){
               AdvancementManager.giveAdvancement(player, "wrongway");
            }
         }

         if (KingsButBad.roles.get(player).equals(Role.PRISONER)) {
            if (!player.hasCooldown(Material.TERRACOTTA)
               && !KingsButBad.isInside(
                  player, KingdomsLoader.activeKingdom.getPrison1(), KingdomsLoader.activeKingdom.getPrison2()
               ) && player.getGameMode() != GameMode.SPECTATOR) {
               KingsButBad.roles.put(player, Role.PEASANT);
               player.removePotionEffect(PotionEffectType.WEAKNESS);
               Keys.inPrison.remove(player);
               Bukkit.broadcastMessage(CreateText.addColors("<red><b>>> " + player.getName() + " has escaped the prison!"));
               player.sendTitle(ChatColor.RED + "!!! You're now a criminal !!!", ChatColor.GRAY + "You escaped");
               KingsButBad.roles.put(player, Role.CRIMINAL);
               AdvancementManager.giveAdvancement(player,"nopbb");
               player.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 20*30, 0));
               player.playSound(player, Sound.ENTITY_SILVERFISH_DEATH, 1.0F, 0.5F);
            }

            if (!DisguiseAPI.isDisguised(player)) {
               PlayerDisguise prisoner = new PlayerDisguise("YanJos3");
               prisoner.setName("Prisoner " + new Random().nextInt(1000, 9999));
               DisguiseAPI.disguiseEntity(player, prisoner);
               DisguiseAPI.setActionBarShown(player, false);
            }

            player.setFoodLevel(6);
            stamina.put(player, 0.99F);
            KingsButBad.prisonQuota.putIfAbsent(player, 0);
            if(Keys.activePact.get(player, "") == Pacts.PRISONER.name())
               KingsButBad.prisonTimer.put(player, KingsButBad.prisonTimer.getOrDefault(player, 0F) - 0.5F);
            else
               KingsButBad.prisonTimer.put(player, KingsButBad.prisonTimer.getOrDefault(player, 0F) - 1);
            String tooltip = "";
            if (KingsButBad.prisonQuota.get(player) > 0) {
               tooltip = tooltip
                  + CreateText.addColors("<gray> | <gold>MINE <red><b>" + KingsButBad.prisonQuota.get(player) + "<gold></b> BLOCKS! <gray>or +80s");
            }

            player.setWalkSpeed(0.1F);
            if(!Keys.vanish.get(player, false))
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
                  if(!Keys.vanish.get(player, false))
                     player.sendActionBar(
                     CreateText.addColors(
                           "<gray>Current king<gray>: <gradient:#FFFF52:#FFBA52><b>"
                              + KingsButBad.kingPrefix.toUpperCase()
                              + " <gradient:#FFFF52:#FFBA52>"
                              + KingsButBad.king.getName().toUpperCase()
                              + "</gradient>"
                        )
                        + actiobarextras
                  );
               } else {
                  if(!Keys.vanish.get(player, false))
                     player.sendActionBar(
                     CreateText.addColors(
                           "<gray>Current king<gray>: <gradient:#FFFF52:#FFBA52><b>"
                              + KingsButBad.kingPrefix.toUpperCase()
                              + " <gradient:#FFFF52:#FFBA52>"
                              + KingsButBad.king.getName().toUpperCase()
                              + "</gradient><dark_gray></b> &</gray><gradient:#FFFF52:#FFBA52><b> "
                              + KingsButBad.kingPrefix2.toUpperCase()
                              + " <gradient:#FFFF52:#FFBA52>"
                              + KingsButBad.king2.getName().toUpperCase()
                              + "</gradient>"
                        )
                        + actiobarextras
                  );
               }
            } else {
               String iscool = "<gradient:#ff2f00:#fcff3d><b>NO KING! Use /king to claim!";
               if (KingsButBad.cooldown > 0) {
                  iscool = "<gradient:#ff2f00:#fcff3d><b>On Cooldown... <gray>[" + parseTicksToTime(KingsButBad.cooldown) + "]";
               }
               if(!Keys.vanish.get(player, false))
                  player.sendActionBar(CreateText.addColors("<gray>Current king<gray>: " + iscool) + actiobarextras);
            }
         }
      }
   }
   public static String parseTicksToTime(float tick) {
      int ticks = (int) tick;
      int totalSeconds = ticks / 20;
      int minutes = totalSeconds / 60;
      int seconds = totalSeconds % 60;

      return String.format("%02d:%02d", minutes, seconds);
   }

}
