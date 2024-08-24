package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.NoNoWords;
import kingsbutbad.kingsbutbad.commands.Builders.BuilderChatCommand;
import kingsbutbad.kingsbutbad.commands.Misc.DiscordCommand;
import kingsbutbad.kingsbutbad.commands.Staff.StaffChatCommand;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.DiscordUtils;
import kingsbutbad.kingsbutbad.utils.Role;
import me.libraryaddict.disguise.DisguiseAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.awt.Color;
import java.util.Objects;
import java.util.function.Predicate;

public class PlayerChatListener implements Listener {
   @EventHandler
   public void onPlayerChatEvent(PlayerChatEvent event) {
      Player player = event.getPlayer();
      String message = event.getMessage();
      Role playerRole = KingsButBad.roles.getOrDefault(player, Role.PEASANT);
      String prefix = "";
      if (KingsButBad.api.getPlayerAdapter(Player.class).getUser(player).getCachedData().getMetaData().getPrefix() != null) {
         prefix = "["
            + ChatColor.stripColor(
               ChatColor.translateAlternateColorCodes(
                  '&', Objects.requireNonNull(KingsButBad.api.getPlayerAdapter(Player.class).getUser(player).getCachedData().getMetaData().getPrefix())
               )
            )
            + "] ";
      }
      message = playerRole.chatColor + message;
      if (KingsButBad.king != null) {
         message = message.replace(
            KingsButBad.king.getName(),
            CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingGender + " " + KingsButBad.king.getName() + "<b></gradient>")
               + playerRole.chatColor
         );
      }

      if (KingsButBad.king2 != null) {
         message = message.replace(
            KingsButBad.king2.getName(),
            CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingGender2 + " " + KingsButBad.king2.getName() + "<b></gradient>")
               + playerRole.chatColor
         );
      }

      if (playerRole.isPowerful) {
         message = this.formatPowerfulMessage(message);
         message = this.replaceRoleNamesInMessage(message);
      }

      event.setCancelled(true);
      if(event.getMessage().startsWith("# ") || event.getMessage().startsWith("@ ") || event.getMessage().startsWith("* ") || event.getMessage().startsWith("! ")) {
         if (Keys.selectedChat.get(player, false))
            StaffChatCommand.sendStaffChat(event.getPlayer(), event.getMessage().substring(2));
         else
            BuilderChatCommand.sendBuilderChat(event.getPlayer(), event.getMessage().substring(2));
         event.setCancelled(true);
         return;
      }
      if (NoNoWords.previouslysaid.containsKey(player) && NoNoWords.previouslysaid.get(player).equalsIgnoreCase(message)) {
         player.sendMessage(ChatColor.RED + "No Spamming!");
      } else {
         NoNoWords.previouslysaid.put(player, message);
         event.setFormat("%1$s" + ChatColor.GRAY + ": %2$s");
         message = NoNoWords.filtermsg(player, message);
         if (!NoNoWords.isClean(message)) {
            message = this.getFilteredMessage(player);
            this.handleLocalChat(event,message);
            return;
         }

         if (this.isKingInIntercomZone(player)) {
            this.broadcastIntercomMessage(player, message);
            event.setCancelled(true);
         } else {
            Role role = KingsButBad.roles.getOrDefault(player, Role.PEASANT);
            String rolePrefix = ""+role;
            if(role == Role.KING || role == Role.PRINCE){
               if(KingsButBad.princeGender.containsKey(player) && role == Role.PRINCE)
                  rolePrefix = KingsButBad.princeGender.get(player);
               if(KingsButBad.king == player && role == Role.KING)
                  rolePrefix = KingsButBad.kingGender;
               if(KingsButBad.king2 == player && role == Role.KING)
                  rolePrefix = KingsButBad.kingGender2;
            }
            BotManager.getInGameChatChannel().sendMessage("**" + prefix + "** **[" + rolePrefix.toUpperCase() + "]** `" + player.getName() + "` > `" + ChatColor.stripColor(DiscordUtils.deformat(message)) + "`").queue();
            this.handleLocalChat(event, message);
         }
      }
   }

   private String formatPowerfulMessage(String message) {
      message = message.replace(" i ", " I ");
      if (!message.endsWith(".") && !message.endsWith("!") && !message.endsWith("?")) {
         message = message + ".";
      }

      return message.substring(0, 1).toUpperCase() + message.substring(1);
   }

   private String replaceRoleNamesInMessage(String message) {
      for (Player p : Bukkit.getOnlinePlayers()) {
         switch (KingsButBad.roles.get(p)) {
            case KNIGHT:
               message = message.replace(p.getName(), CreateText.addColors("<gray>Knight " + p.getName()) + KingsButBad.roles.get(p).chatColor);
               break;
            case PRISON_GUARD:
               message = message.replace(p.getName(), CreateText.addColors("<blue>Prison Guard " + p.getName()) + KingsButBad.roles.get(p).chatColor);
               break;
            case PRINCE:
               message = message.replace(
                  p.getName(),
                  CreateText.addColors("<gradient:#FFFF52:#FFBA52>" + KingsButBad.princeGender.get(p).toUpperCase() + p.getName())
                     + KingsButBad.roles.get(p).chatColor
               );
               break;
            case PEASANT:
               message = message.replace(p.getName(), CreateText.addColors("<#59442B>Peasant " + p.getName()) + KingsButBad.roles.get(p).chatColor);
               break;
            case SERVANT:
               message = message.replace(p.getName(), CreateText.addColors("<#867143>Servant " + p.getName()) + KingsButBad.roles.get(p).chatColor);
               break;
            case OUTLAW:
               message = message.replace(p.getName(), CreateText.addColors("<gold>Outlaw " + p.getName()) + KingsButBad.roles.get(p).chatColor);
            case CRIMINAl:
               message = message.replace(p.getName(), CreateText.addColors("<red>Criminal " + p.getName()) + KingsButBad.roles.get(p).chatColor);
               break;
            case PRISONER:
               message = message.replace(p.getName(), CreateText.addColors("<gold>Prisoner " + p.getName()) + KingsButBad.roles.get(p).chatColor);
         }
      }

      return message;
   }

   private String getFilteredMessage(Player player) {
      return NoNoWords.getRandomReplacement().getMsg();
   }

   public boolean isKingInIntercomZone(Player player) {
      if(KingsButBad.king == player || KingsButBad.king2 == player) {
         Location playerLocation = player.getLocation();
         double radius = 2.5;

         // Get the block coordinates around the player within the radius
         int xMin = (int) (playerLocation.getX() - radius);
         int xMax = (int) (playerLocation.getX() + radius);
         int yMin = (int) (playerLocation.getY() - radius);
         int yMax = (int) (playerLocation.getY() + radius);
         int zMin = (int) (playerLocation.getZ() - radius);
         int zMax = (int) (playerLocation.getZ() + radius);

         for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
               for (int z = zMin; z <= zMax; z++) {
                  Block block = player.getWorld().getBlockAt(x, y, z);
                  if (block.getType() == Material.BLACK_CANDLE) {
                     Location candleLocation = block.getLocation();
                     double distance = playerLocation.distance(candleLocation);
                     if (distance <= radius) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
      return false;
   }


   private void broadcastIntercomMessage(Player executor, String message) {
      for (Player p : Bukkit.getOnlinePlayers()) {
         p.playSound(p, Sound.ENTITY_BEE_LOOP_AGGRESSIVE, 1.0F, 0.75F);
         p.sendTitle(ChatColor.BLUE + "INTERCOM " + ChatColor.WHITE + ">>", ChatColor.GOLD + message);
         p.sendMessage(ChatColor.BLUE + "INTERCOM " + ChatColor.WHITE + ">> " + ChatColor.GOLD + message);
         EmbedBuilder embed = new EmbedBuilder();
         embed.setColor(Color.BLUE); // Set the color of the embed
         embed.setTitle("**INTERCOM**"); // Title of the embed
         embed.setDescription("`" + DiscordUtils.deformat(ChatColor.stripColor(message)) + "`"); // Main message content
         embed.setFooter("sent by "+DiscordUtils.deformat(executor.getName()), null); // Optional footer

         BotManager.getInGameChatChannel().sendMessageEmbeds(embed.build()).queue();;

      }
   }

   private void handleLocalChat(PlayerChatEvent event, String message) {
      Player player = event.getPlayer();
      int hearCount = 0;

      for (Player p : Bukkit.getOnlinePlayers()) {
         if (!p.equals(player)) {
            Location originalPlayerLoc = player.getEyeLocation();
            Vector direction = p.getEyeLocation().toVector().subtract(player.getEyeLocation().toVector());
            RayTraceResult rtr = player.getWorld().rayTrace(originalPlayerLoc, direction, 25.0, FluidCollisionMode.NEVER, true, 2.0, Predicate.isEqual(p));
            if (rtr != null && rtr.getHitEntity() != null && rtr.getHitEntity().equals(p)) {
               hearCount++;
               this.sendLocalizedMessage(player, p, message);
            } else if (ChatColor.stripColor(message).contains(p.getName())) {
               player.sendMessage(ChatColor.RED + p.getName() + " isn't in range and can't hear you!");
            }
         }
      }

      if (hearCount == 0) {
         if(Keys.isAutoShoutEnabled.get(player, true))
            Bukkit.getOnlinePlayers().forEach(px -> this.sendShoutMessage(player, px, message));
         else
            player.sendMessage(CreateText.addColors("<red>Nobody heard you... <gray>(<white>Try enabling AutShout in /kbbSettings<gray>)"));
      } else {
         this.sendLocalizedMessage(player, player, message);
      }
   }

   private void sendShoutMessage(Player sender, Player receiver, String msg) {
      String shout = ChatColor.GRAY + "[SHOUT] ";
      if (DisguiseAPI.isDisguised(sender)) {
         receiver.sendMessage(
            shout
               + ChatColor.DARK_GRAY
               + "["
               + ChatColor.GOLD
               + "PRISONER"
               + ChatColor.DARK_GRAY
               + "] "
               + DisguiseAPI.getDisguise(sender).getWatcher().getCustomName()
               + ChatColor.GRAY
               + ": "
               + ChatColor.WHITE
               + msg
         );
      } else {
         receiver.sendMessage(shout + sender.getPlayerListName() + ChatColor.GRAY + ": " + ChatColor.WHITE + msg);
      }
   }

   private void sendLocalizedMessage(Player sender, Player receiver, String message) {
      if (DisguiseAPI.isDisguised(sender)) {
         receiver.sendMessage(
            ChatColor.DARK_GRAY
               + "["
               + ChatColor.GOLD
               + "PRISONER"
               + ChatColor.DARK_GRAY
               + "] "
               + DisguiseAPI.getDisguise(sender).getWatcher().getCustomName()
               + ChatColor.GRAY
               + ": "
               + ChatColor.WHITE
               + message
         );
      } else {
         receiver.sendMessage(sender.getPlayerListName() + ChatColor.GRAY + ": " + ChatColor.WHITE + message);
      }
   }
}
