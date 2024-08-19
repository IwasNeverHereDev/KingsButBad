package agmas.kingsbutbad.utils.CBP;

import agmas.kingsbutbad.Discord.BotManager;
import agmas.kingsbutbad.utils.DiscordUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CBPUtils {

    public static EmbedBuilder getMuteEmbed(String player, String staff, String reason, String duration, long muteDate) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED.brighter());
        embedBuilder.setTitle(player + " - MUTE");
        embedBuilder.addField("Staff:", staff, false);
        embedBuilder.addField("Time:", duration, false);

        if (!duration.toLowerCase().contains("perm")) {  // Only add end date if not permanent
            long durationInMillis = convertDurationToMillis(duration);
            long endTimestamp = (muteDate + durationInMillis) / 1000L;
            String discordTimestamp = "<t:" + endTimestamp + ":R>";
            embedBuilder.addField("End Date:", discordTimestamp, false);
        }

        embedBuilder.addField("Reason:", reason, false);
        BotManager.getPunishmentChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        return embedBuilder;
    }

    public static EmbedBuilder getBanEmbed(String player, String staff, String reason, String duration, long banDate) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(player + " - BAN");
        embedBuilder.setColor(Color.RED.brighter());
        embedBuilder.addField("Staff:", staff, false);
        embedBuilder.addField("Time:", duration, false);

        if (!duration.toLowerCase().contains("perm")) {  // Only add lifted date if not permanent
            long durationInMillis = convertDurationToMillis(duration);
            long endTimestamp = (banDate + durationInMillis) / 1000L;
            String discordTimestamp = "<t:" + endTimestamp + ":R>";
            embedBuilder.addField("Lifted Date:", discordTimestamp, false);
        }

        embedBuilder.addField("Reason:", reason, false);
        BotManager.getPunishmentChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        return embedBuilder;
    }
    public static EmbedBuilder getUnBanEmbed(String player, String staff) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(player + " - UNBAN");
        embedBuilder.setColor(Color.GREEN.brighter());
        embedBuilder.addField("Staff:", staff, false);
        BotManager.getPunishmentChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        return embedBuilder;
    }
    public static EmbedBuilder getUnmuteEmbed(String player, String staff) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(player + " - UNMUTE");
        embedBuilder.setColor(Color.GREEN.brighter());
        embedBuilder.addField("Staff:", staff, false);
        BotManager.getPunishmentChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        return embedBuilder;
    }

    public static EmbedBuilder getKickEmbed(String player, String staff, String reason) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED.brighter());
        embedBuilder.setTitle(player + " - KICK");
        embedBuilder.addField("Staff:", staff, false);
        embedBuilder.addField("Reason:", reason, false);
        BotManager.getPunishmentChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        return embedBuilder;
    }
    public static EmbedBuilder getWarnEmbed(String player, String staff, String reason) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED.brighter());
        embedBuilder.setTitle(player + " - WARN");
        embedBuilder.addField("Staff:", staff, false);
        embedBuilder.addField("Reason:", reason, false);
        BotManager.getPunishmentChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        return embedBuilder;
    }

    public static EmbedBuilder getReportEmbed(String user, String reportMessage, String reporter) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED.brighter());
        embedBuilder.setTitle(DiscordUtils.deformat(user) + " - REPORT");
        embedBuilder.setDescription("Reporter: " + DiscordUtils.deformat(reporter));
        embedBuilder.addField("Reason:", DiscordUtils.deformat(reportMessage), false);

        ForumChannel reportChannel = BotManager.getReportChannel();

        if (reportChannel != null) {
            reportChannel.createForumPost("Report [AUTOMATED]", MessageCreateData.fromEmbeds(embedBuilder.build())).queue();
        } else {
            System.out.println("Report channel not found!");
        }

        return embedBuilder;
    }

    // Utility method to convert a duration String to milliseconds
    public static long convertDurationToMillis(String duration) {
        long milliseconds = 0;

        // Check if the duration is empty
        if (duration == null || duration.isEmpty()) {
            throw new IllegalArgumentException("Duration cannot be null or empty.");
        }

        // Pattern to match duration parts (e.g., "1d", "2h", "30m", "5w")
        Pattern pattern = Pattern.compile("(\\d+)([dwhm])");
        Matcher matcher = pattern.matcher(duration);

        while (matcher.find()) {
            long value = Long.parseLong(matcher.group(1));
            char unit = matcher.group(2).charAt(0);

            switch (unit) {
                case 'd': // Days
                    milliseconds += value * 24 * 60 * 60 * 1000;
                    break;
                case 'h': // Hours
                    milliseconds += value * 60 * 60 * 1000;
                    break;
                case 'w': // Weeks
                    milliseconds += value * 7 * 24 * 60 * 60 * 1000;
                    break;
                case 'm': // Minutes
                    milliseconds += value * 60 * 1000;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported duration unit: " + unit);
            }
        }

        return milliseconds;
    }
}
