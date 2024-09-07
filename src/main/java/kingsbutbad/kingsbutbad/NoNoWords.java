package kingsbutbad.kingsbutbad;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.utils.Alert;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoNoWords {
   private static List<String> filteredWords = new ArrayList<>();
   public static void reload() {
      File dataFolder = KingsButBad.pl.getDataFolder();
      File filterFile = new File(dataFolder, "filter.json");

      if (!dataFolder.exists())
         dataFolder.mkdirs();
      if (!filterFile.exists()) {
         try {
            if (filterFile.createNewFile()) {
               try (FileOutputStream fos = new FileOutputStream(filterFile);
                    OutputStreamWriter writer = new OutputStreamWriter(fos)) {
                  writer.write("[]");
                  Bukkit.getLogger().info("filter.json created with an empty array.");
               }
            } else {
               Bukkit.getLogger().warning("Failed to create filter.json.");
            }
         } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to create filter.json: " + e.getMessage());
            return;
         }
      }

      try (FileInputStream fis = new FileInputStream(filterFile);
           InputStreamReader reader = new InputStreamReader(fis)) {

         Gson gson = new Gson();
         Type type = new TypeToken<List<String>>() {
         }.getType();
         filteredWords = gson.fromJson(reader, type);

         if (filteredWords == null) {
            Bukkit.getLogger().warning("filter.json is empty or invalid.");
            filteredWords = List.of();
         } else {
            Bukkit.getLogger().info("Filtered words loaded!");
         }

      } catch (IOException e) {
         Bukkit.getLogger().severe("Failed to load filter.json: " + e.getMessage());
      }
   }
   public static HashMap<Player, String> previouslysaid = new HashMap<>();

   private static String cleanMessage(String msg) {
      msg = msg.replaceAll("[\\p{Punct}&&[^']&&[^-]]+$", "");
      return replaceConsecutiveDuplicates(msg);
   }

   private static String replaceConsecutiveDuplicates(String msg) {
      Pattern pattern = Pattern.compile("(.)\\1*", 8);
      Matcher matcher = pattern.matcher(msg);
      StringBuilder sanitized = new StringBuilder();

      while (matcher.find()) {
         sanitized.append(matcher.group(1));
      }

      return sanitized.toString();
   }

   public static String filtermsg(Player p, String msg) {
      String cleanedMsg = cleanMessage(msg).toLowerCase();

      for (String word : filteredWords) {
         if (cleanedMsg.contains(word.toLowerCase())) {
            String name = p.getName().replaceAll("_", "`_`");
            Alert.send(Alert.AlertType.FILTERs, p, msg);
            BotManager.getFilterChannel()
               .sendMessage("**" + name + "** was filtered due to inappropriate content. Msg was: `" + ChatColor.stripColor(msg) + "`!")
               .queue();
            return getRandomReplacement().getMsg();
         }
      }

      return msg;
   }

   public static Boolean isClean(String msg) {
      String cleanedMsg = cleanMessage(msg).toLowerCase();

      for (String word : filteredWords) {
         if (cleanedMsg.contains(word.toLowerCase())) {
            return false;
         }
      }

      return true;
   }

   public static NoNoWords.FilterReplacements getRandomReplacement() {
      Random random = new Random();
      NoNoWords.FilterReplacements[] values = NoNoWords.FilterReplacements.values();
      return values[random.nextInt(values.length)];
   }

   public enum FilterReplacements {
      MINEHUT("Minehut is a great Server!"),
      TEST("Wow, A wonderful Server"),
      RULES("Remember, Always follow the rules!"),
      FR("This is not a filter msg! fr fr"),
      NO("No, This isn't a filter word... jk :)"),
      EPIC("Well this is weird.."),
      AGMASS("Wow, Agmass is epic!"),
      OTTER("Wow, myself this is the worst msg you could ever put here -_Aquaotter_"),
      GAMERWORD("(Very Gamer Word) PBB reference?!"),
      DISCORD("Join the Discord!?!? (Yes this is a filtered word free ads >:)");

      private final String msg;

      FilterReplacements(String msg) {
         this.msg = msg;
      }

      public String getMsg() {
         return this.msg;
      }
   }
}
