package kingsbutbad.kingsbutbad.utils;

import org.bukkit.ChatColor;

@SuppressWarnings("deprecation")
public enum Role {
   PRISONER(
      CreateText.addColors("<gold>PRISONER"), CreateText.addColors("<gray>You got caught!<gray>"), "<gold>PRISONER", false, ChatColor.GRAY, "P"
   ),
   PEASANT(CreateText.addColors("<#59442B>PEASANT"), CreateText.addColors("<gray>Survive.<gray>"), "<#59442B>PEASANT", false, ChatColor.GRAY, "P"),
   SERVANT(CreateText.addColors("<#867143>SERVANT"), CreateText.addColors("<gray>Serve the king.<gray>"), "<#867143>SERVANT", false, ChatColor.GRAY, "S"),
   CRIMINAL(CreateText.addColors("<red>CRIMINAL"), CreateText.addColors("<gray>Don't get caught!<gray>"), "<red>CRIMINAL", false, ChatColor.GRAY, "C"),
   OUTLAW(CreateText.addColors("<gold>OUTLAW"), CreateText.addColors("<gray>Overthrow the monarchs!<gray>"), "<gold>OUTLAW", false, ChatColor.GRAY, "O"),
   KNIGHT(CreateText.addColors("<gray>KNIGHT"), CreateText.addColors("<blue>Keep the kingdom safe!<gray>"), "<gray>KNIGHT", true, ChatColor.GRAY, "K"),
   BODYGUARD(
      CreateText.addColors("<dark_gray>BODYGUARD"), CreateText.addColors("<blue>Keep the monarchs safe!<gray>"), "<dark_gray>BODYGUARD", true, ChatColor.GRAY, "BG"
   ),
   PRISON_GUARD(
      CreateText.addColors("<blue>PRISON GUARD"), CreateText.addColors("<blue>Protect The Prison.<gray>"), "<blue>PRISON GUARD", true, ChatColor.GRAY, "PG"
   ),
   KING(
      CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>KING<b></gradient>"),
      CreateText.addColors("<gradient:#FFFF52:#FFBA52><i>Live the royal life</gradient>"),
      "<gradient:#FFFF52:#FFBA52><b>KING<b></gradient>",
      true,
      ChatColor.YELLOW,
           "null"
   ),
   PRINCE(
      CreateText.addColors("<gradient:#FFFF52:#FFBA52>PRINCE</gradient>"),
      CreateText.addColors("<gradient:#FFFF52:#FFBA52><i>Live the royal life</gradient>"),
      "<gradient:#FFFF52:#FFBA52>PRINCE</gradient>",
      true,
      ChatColor.YELLOW,
           "null"
   );

   public String tag;
   public String objective;
   public String uncompressedColors;
   public Boolean isPowerful;
   public ChatColor chatColor;
   public String smallTag;

   private Role(String tag, String objective, String uncompressedColors, Boolean isPowerful, ChatColor chatColor, String smallTag) {
      this.tag = tag;
      this.objective = objective;
      this.uncompressedColors = uncompressedColors;
      this.isPowerful = isPowerful;
      this.chatColor = chatColor;
      this.smallTag = smallTag;
   }
}
