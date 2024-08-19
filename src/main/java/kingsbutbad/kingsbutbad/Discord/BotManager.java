package kingsbutbad.kingsbutbad.Discord;

import kingsbutbad.kingsbutbad.Discord.DiscordEvents.SLashCommandInteractionEvent;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.Discord.DiscordEvents.ReceiveMessageEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class BotManager {
   private static JDA bot;
   private static TextChannel inGameChatChannel;
   private static TextChannel commandsChannel;
   private static TextChannel filterChannel;
   private static TextChannel punishmentChannel;
   private static TextChannel stafflogChannel;
   private static TextChannel builderChannel;
   private static ForumChannel reportChannel;
   private static Guild guild;

   public static void init() {
      String discordToken = getConfigString("DISCORD_BOT_TOKEN", "your-discord-bot-token-here");

      if ("your-discord-bot-token-here".equals(discordToken)) {
         saveConfigWithDefaultToken(discordToken);
      } else {
         try {
            bot = JDABuilder.createDefault(discordToken)
                    .addEventListeners(new ReceiveMessageEvent())
                    .enableIntents(GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                    .enableCache(CacheFlag.VOICE_STATE)
                    .setActivity(Activity.playing("Playing KingsButBad.minehut.gg!"))
                    .addEventListeners(new SLashCommandInteractionEvent())
                    .build();
            bot.awaitReady();

            inGameChatChannel = getChannelById("InGameChatChannelID");
            commandsChannel = getChannelById("CommandsChannelID");
            punishmentChannel = getChannelById("PunishmentChannelID");
            filterChannel = getChannelById("FilterChannelID");
            stafflogChannel = getChannelById("StaffLogChannelID");
            builderChannel = getChannelById("BuilderLogChannelID");
            guild = getGuildById("GuildID");
            reportChannel = getFormChannelById("ReportForumChannelID");

         } catch (Exception e) {
            logError("An error occurred while initializing the Discord bot.", e);
         }
      }
   }

   public static TextChannel getBuilderChannel() {
      return builderChannel;
   }

   public static TextChannel getStafflogChannel() {
      return stafflogChannel;
   }

   private static void saveConfigWithDefaultToken(String discordToken) {
      KingsButBad.pl.getConfig().set("DISCORD_BOT_TOKEN", discordToken);
      KingsButBad.pl.saveConfig();
      logSevere("Discord Bot token is null! [DISCORD_BOT_TOKEN: CONFIG.YML]");
   }

   private static TextChannel getChannelById(String configKey) {
      String channelId = getConfigString(configKey, "0");
      TextChannel channel = bot.getTextChannelById(channelId);
      if (channel == null) {
         logSevere("Channel not found! [" + configKey + ": CONFIG.YML]");
      }
      return channel;
   }
   private static ForumChannel getFormChannelById(String configKey) {
      String channelId = getConfigString(configKey, "0");
      ForumChannel channel = bot.getForumChannelById(channelId);
      if (channel == null) {
         logSevere("Form Channel not found! [" + configKey + ": CONFIG.YML]");
      }
      return channel;
   }

   public static ForumChannel getReportChannel() {
      return reportChannel;
   }

   private static Guild getGuildById(String configKey) {
      String guildId = getConfigString(configKey, "0");
      Guild guild = bot.getGuildById(guildId);
      if (guild == null) {
         logSevere("Guild not found! [" + configKey + ": CONFIG.YML]");
      }
      return guild;
   }

   public static TextChannel getInGameChatChannel() {
      return inGameChatChannel;
   }

   public static TextChannel getCommandsChannel() {
      return commandsChannel;
   }

   public static TextChannel getFilterChannel() {
      return filterChannel;
   }

   public static TextChannel getPunishmentChannel() {
      return punishmentChannel;
   }

   public static JDA getBot() {
      return bot;
   }

   public static Guild getGuild() {
      return guild;
   }

   private static String getConfigString(String path, String defaultValue) {
      FileConfiguration config = KingsButBad.pl.getConfig();
      try {
         if (config.isSet(path)) {
            return config.getString(path);
         }
         logWarning("Config path not found: " + path);
      } catch (Exception e) {
         logError("Error getting config value at path: " + path, e);
      }
      return defaultValue;
   }

   private static void logSevere(String message) {
      Bukkit.getLogger().severe(message);
   }

   private static void logWarning(String message) {
      Bukkit.getLogger().warning(message);
   }

   private static void logError(String message, Exception e) {
      KingsButBad.pl.getLogger().severe(message);
      e.printStackTrace();
   }
}
