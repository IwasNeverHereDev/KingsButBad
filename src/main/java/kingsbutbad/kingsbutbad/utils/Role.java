package kingsbutbad.kingsbutbad.utils;

import org.bukkit.ChatColor;

public enum Role {
   PRISONER(
      CreateText.addColors("<gold>PRISONER"), CreateText.addColors("<gray>You got caught! Stay for 2m.<gray>"), "<gold>PRISONER", false, ChatColor.GRAY
   ),
   PEASANT(CreateText.addColors("<#59442B>PEASANT"), CreateText.addColors("<gray>Survive.<gray>"), "<#59442B>PEASANT", false, ChatColor.GRAY),
   SERVANT(CreateText.addColors("<#867143>SERVANT"), CreateText.addColors("<gray>Serve the king.<gray>"), "<#867143>SERVANT", false, ChatColor.GRAY),
   CRIMINAl(CreateText.addColors("<red>CRIMINAL"), CreateText.addColors("<gray>Don't get caught!<gray>"), "<red>CRIMINAL", false, ChatColor.GRAY),
   OUTLAW(CreateText.addColors("<gold>OUTLAW"), CreateText.addColors("<gray>OVER THROW THE KING!<gray>"), "<gold>OUTLAW", false, ChatColor.GRAY),
   KNIGHT(CreateText.addColors("<gray>KNIGHT"), CreateText.addColors("<blue>Keep the kingdom safe!<gray>"), "<gray>KNIGHT", true, ChatColor.GRAY),
   BODYGUARD(
      CreateText.addColors("<dark_gray>BODYGUARD"), CreateText.addColors("<blue>Keep the monarchs safe!<gray>"), "<dark_gray>BODYGUARD", true, ChatColor.GRAY
   ),
   PRISON_GUARD(
      CreateText.addColors("<blue>PRISON GUARD"), CreateText.addColors("<blue>Protect The Prison.<gray>"), "<blue>PRISON GUARD", true, ChatColor.GRAY
   ),
   KING(
      CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>KING<b></gradient>"),
      CreateText.addColors("<gradient:#FFFF52:#FFBA52><i>Live the royal life</gradient>"),
      "<gradient:#FFFF52:#FFBA52><b>KING<b></gradient>",
      true,
      ChatColor.YELLOW
   ),
   PRINCE(
      CreateText.addColors("<gradient:#FFFF52:#FFBA52>PRINCE</gradient>"),
      CreateText.addColors("<gradient:#FFFF52:#FFBA52><i>Live the royal life</gradient>"),
      "<gradient:#FFFF52:#FFBA52>PRINCE</gradient>",
      true,
      ChatColor.YELLOW
   );

   public String tag;
   public String objective;
   public String uncompressedColors;
   public Boolean isPowerful;
   public ChatColor chatColor;

   private Role(String tag, String objective, String uncompressedColors, Boolean isPowerful, ChatColor chatColor) {
      this.tag = tag;
      this.objective = objective;
      this.uncompressedColors = uncompressedColors;
      this.isPowerful = isPowerful;
      this.chatColor = chatColor;
   }
}
