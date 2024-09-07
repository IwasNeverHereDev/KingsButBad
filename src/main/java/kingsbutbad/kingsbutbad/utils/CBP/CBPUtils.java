package kingsbutbad.kingsbutbad.utils.CBP;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.utils.DiscordUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CBPUtils {

    public static void getMuteEmbed(String player, String staff, String reason, String duration, long muteDate) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED.brighter());
        embedBuilder.setTitle(player + " - MUTE");
        embedBuilder.addField("Staff:", staff, false);
        embedBuilder.addField("Time:", duration, false);

        if (!duration.toLowerCase().contains("perm")) {
            long durationInMillis = convertDurationToMillis(duration);
            long endTimestamp = (muteDate + durationInMillis) / 1000L;
            String discordTimestamp = "<t:" + endTimestamp + ":R>";
            embedBuilder.addField("End Date:", discordTimestamp, false);
        }

        embedBuilder.addField("Reason:", reason, false);
        BotManager.getPunishmentChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }

    public static void getBanEmbed(String player, String staff, String reason, String duration, long banDate) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(player + " - BAN");
        embedBuilder.setColor(Color.RED.brighter());
        embedBuilder.addField("Staff:", staff, false);
        embedBuilder.addField("Time:", duration, false);

        if (!duration.toLowerCase().contains("perm")) {
            long durationInMillis = convertDurationToMillis(duration);
            long endTimestamp = (banDate + durationInMillis) / 1000L;
            String discordTimestamp = "<t:" + endTimestamp + ":R>";
            embedBuilder.addField("Lifted Date:", discordTimestamp, false);
        }

        embedBuilder.addField("Reason:", reason, false);
        BotManager.getPunishmentChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
    public static void getUnBanEmbed(String player, String staff) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(player + " - UNBAN");
        embedBuilder.setColor(Color.GREEN.brighter());
        embedBuilder.addField("Staff:", staff, false);
        BotManager.getPunishmentChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
    public static void getUnmuteEmbed(String player, String staff) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(player + " - UNMUTE");
        embedBuilder.setColor(Color.GREEN.brighter());
        embedBuilder.addField("Staff:", staff, false);
        BotManager.getPunishmentChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }

    public static void getKickEmbed(String player, String staff, String reason) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED.brighter());
        embedBuilder.setTitle(player + " - KICK");
        embedBuilder.addField("Staff:", staff, false);
        embedBuilder.addField("Reason:", reason, false);
        BotManager.getPunishmentChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
    public static void getWarnEmbed(String player, String staff, String reason) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED.brighter());
        embedBuilder.setTitle(player + " - WARN");
        embedBuilder.addField("Staff:", staff, false);
        embedBuilder.addField("Reason:", reason, false);
        BotManager.getPunishmentChannel().sendMessageEmbeds(embedBuilder.build()).queue();
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
    public static long convertDurationToMillis(String duration) {
        long milliseconds = 0;

        if (duration == null || duration.isEmpty()) {
            throw new IllegalArgumentException("Duration cannot be null or empty.");
        }

        Pattern pattern = Pattern.compile("(\\d+)([dwhm])");
        Matcher matcher = pattern.matcher(duration);

        while (matcher.find()) {
            long value = Long.parseLong(matcher.group(1));
            char unit = matcher.group(2).charAt(0);

            switch (unit) {
                case 'd':
                    milliseconds += value * 24 * 60 * 60 * 1000;
                    break;
                case 'h':
                    milliseconds += value * 60 * 60 * 1000;
                    break;
                case 'w':
                    milliseconds += value * 7 * 24 * 60 * 60 * 1000;
                    break;
                case 'm':
                    milliseconds += value * 60 * 1000;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported duration unit: " + unit);
            }
        }

        return milliseconds;
    }
}
