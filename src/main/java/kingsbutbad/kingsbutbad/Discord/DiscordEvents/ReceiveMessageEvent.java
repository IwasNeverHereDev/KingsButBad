package kingsbutbad.kingsbutbad.Discord.DiscordEvents;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.utils.CreateText;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ReceiveMessageEvent extends ListenerAdapter {
   @Override
   public void onMessageReceived(@NotNull MessageReceivedEvent event) {
      if(event.getAuthor().isBot()) return;
      if(!event.getGuild().equals(BotManager.getGuild())) return;
      if (event.getChannel().equals(BotManager.getInGameChatChannel())) {
               String msg = event.getMessage().getContentDisplay();
               msg = msg.replaceAll("\\bhttps?://\\S+\\b", "(link)");
               msg = msg.replaceAll("\\b\\S+\\.(jpg|jpeg|png|gif|bmp|webp)\\b", "(image)");
               Color mainRoleColor = event.getMember().getRoles().get(0).getColor();
               String mainRolecolorFormatted = "<#"+Integer.toHexString(mainRoleColor.getRGB()).substring(2)+">";
               String mainRoleName = event.getMember().getRoles().isEmpty() ? "<#4ba5ff>(Member)" : "<#4ba5ff>(" +mainRolecolorFormatted+ event.getMember().getRoles().get(0).getName() + "<#4ba5ff>) ";
               Bukkit.broadcastMessage(CreateText.addColors("<#4ba5ff>" + mainRoleName + event.getAuthor().getEffectiveName() + ": " + msg));
      }
      if(event.getChannel().equals(BotManager.getStafflogChannel())){
         String msg = event.getMessage().getContentDisplay();
         msg = msg.replaceAll("\\bhttps?://\\S+\\b", "(link)");
         msg = msg.replaceAll("\\b\\S+\\.(jpg|jpeg|png|gif|bmp|webp)\\b", "(image)");
         Color mainRoleColor = event.getMember().getRoles().get(0).getColor();
         String mainRolecolorFormatted = "<#"+Integer.toHexString(mainRoleColor.getRGB()).substring(2)+">";
         String mainRoleName = event.getMember().getRoles().isEmpty() ? " <gray>(Default) " : "<gray>(" +mainRolecolorFormatted+ event.getMember().getRoles().get(0).getName() + "<gray>) ";
         sendStaffChat(event.getAuthor().getEffectiveName(), mainRoleName, msg);
      }
      if(event.getChannel().equals(BotManager.getBuilderChannel())){
         String msg = event.getMessage().getContentDisplay();
         msg = msg.replaceAll("\\bhttps?://\\S+\\b", "(link)");
         msg = msg.replaceAll("\\b\\S+\\.(jpg|jpeg|png|gif|bmp|webp)\\b", "(image)");
         Color mainRoleColor = event.getMember().getRoles().get(0).getColor();
         String mainRolecolorFormatted = "<#"+Integer.toHexString(mainRoleColor.getRGB()).substring(2)+">";
         String mainRoleName = event.getMember().getRoles().isEmpty() ? " <gray>(Default) " : "<gray>(" +mainRolecolorFormatted+ event.getMember().getRoles().get(0).getName() + "<gray>) ";
         sendBuilderChat(event.getAuthor().getEffectiveName(), mainRoleName, msg);
      }
   }
   private void sendStaffChat(String name, String prefix, String msg){
      String messageFormated = CreateText.addColors("<gray>[<red>Staff Chat<gray>] "+ChatColor.stripColor(prefix)+"<red>"+name+"<gray>: <white>"+msg);
      for (Player player : Bukkit.getOnlinePlayers())
         if (player.hasPermission("kbb.staff"))
            player.sendMessage(messageFormated);
   }
   private void sendBuilderChat(String name, String prefix, String msg){
      String messageFormated = CreateText.addColors("<gray>[<yellow>Builder Chat<gray>] "+ChatColor.stripColor(prefix)+"<red>"+name+"<gray>: <white>"+msg);
      for (Player player : Bukkit.getOnlinePlayers())
         if (player.hasPermission("kbb.builder"))
            player.sendMessage(messageFormated);
   }
}
