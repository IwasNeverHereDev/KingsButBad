package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.tasks.MiscTask;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class KingCommand implements CommandExecutor {
   public static HashMap<UUID, Integer> kinglasttimer = new HashMap<>();
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (sender instanceof Player p) {
         if (KingsButBad.roles.get(p).equals(Role.PRISONER)) {
            p.sendMessage(ChatColor.RED + "You can't become a king as a prisoner. Stay in, scum.");
            return true;
         }

         if (KingsButBad.roles.get(p).equals(Role.PRINCE)) {
            if (args.length >= 2) {
               if (args[0].equals("gender")) {
                  String var16 = args[1];
                  switch (var16) {
                     case "male":
                        KingsButBad.princeGender.put(p, "Prince");
                        break;
                     case "female":
                        KingsButBad.princeGender.put(p, "Princess");
                        break;
                     case "sussy":
                        KingsButBad.princeGender.put(p, "Cringe");
                        break;
                     default:
                        KingsButBad.princeGender.put(p, "Royal Heir");
                  }
               }

               return true;
            }

            return true;
         }

         if (KingsButBad.king == null) {
            if (KingsButBad.cooldown <= 0) {
               if (p == KingsButBad.lastKing || p == KingsButBad.lastKing2) {
                  p.sendMessage(CreateText.addColors("<red>You were king last time! <gray>(<white>"+ MiscTask.parseTicksToTime(kinglasttimer.getOrDefault(((Player) sender).getUniqueId(), 20*60*5)) +"<gray>)"));
                  if(!kinglasttimer.containsKey(((Player) sender).getUniqueId()))
                     kinglasttimer.put(((Player) sender).getUniqueId(), 20*60*5);
                  return true;
               }

               KingsButBad.thirst.put(p, 300);
               KingsButBad.invitations.clear();
               KingsButBad.king = p;
               KingsButBad.roles.put(p, Role.KING);
               RoleManager.showKingMessages(p, Role.KING.objective);
               RoleManager.givePlayerRole(p);
               KingsButBad.kingGender = "King";

               for (Player pe : Bukkit.getOnlinePlayers()) {
                  pe.sendTitle(
                     CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>KING " + p.getName().toUpperCase()), ChatColor.GREEN + "is your new overlord!"
                  );
               }
            }

            return true;
         }

         if (KingsButBad.king == p && args.length > 0 && args[0].equals("sidekick") && KingsButBad.king2 == null && args.length > 2) {
            if (Bukkit.getPlayer(args[1]) != null) {
               Player pe = Bukkit.getPlayer(args[1]);
               if(Keys.vanish.get(pe, false)){
                  p.sendMessage(
                          CreateText.addColors(
                                  "<gradient:#FFFF52:#FFBA52><b>"
                                          + KingsButBad.kingGender.toUpperCase()
                                          + " "
                                          + p.getName().toUpperCase()
                                          + "</b><blue>, that player <red>does not exist."
                          )
                  );
                  return true;
               }
               RoleManager.setKingGender(false, args[1]);
               if (KingsButBad.roles.get(pe) == Role.PEASANT) {
                  KingsButBad.invitations.put(pe, Role.KING);
                  pe.sendMessage(
                     CreateText.addColors(
                        "<gradient:#FFFF52:#FFBA52><b>"
                           + KingsButBad.kingGender.toUpperCase()
                           + " "
                           + p.getName().toUpperCase()
                           + "</b><blue> has invited you to being <gradient:#FFFF52:#FFBA52><b>their sidekick</b><gradient:#FFFF52:#FFBA52>! <red>use /accept to accept."
                     )
                  );
                  sender.sendMessage(
                          CreateText.addColors(
                                  "<gradient:#FFFF52:#FFBA52><b>"
                                          + KingsButBad.kingGender.toUpperCase()
                                          + " "
                                          + p.getName().toUpperCase()
                                          + "</b><blue> you, have invited "+pe.getName()+" to become <gradient:#FFFF52:#FFBA52><b>your sidekick</b><gradient:#FFFF52:#FFBA52>!"
                          )
                  );
               } else {
                  p.sendMessage(
                     CreateText.addColors(
                        "<gradient:#FFFF52:#FFBA52><b>"
                           + KingsButBad.kingGender.toUpperCase()
                           + " "
                           + p.getName().toUpperCase()
                           + "</b><blue>, that player <red>isn't a peasant."
                     )
                  );
               }
            } else {
               p.sendMessage(
                  CreateText.addColors(
                     "<gradient:#FFFF52:#FFBA52><b>"
                        + KingsButBad.kingGender.toUpperCase()
                        + " "
                        + p.getName().toUpperCase()
                        + "</b><blue>, that player <red>does not exist."
                  )
               );
            }
         }

         if (RoleManager.isKingAtAll(p)) {
            if (args.length > 0) {
               if (args[0].equals("gender")) {
                  RoleManager.setKingGender(p, args[1]);
               }

               if (args[0].equals("knight") && args.length > 1) {
                  if (Bukkit.getPlayer(args[1]) != null) {
                     Player pe = Bukkit.getPlayer(args[1]);
                     if(Keys.vanish.get(pe, false)){
                        p.sendMessage(
                                CreateText.addColors(
                                        "<gradient:#FFFF52:#FFBA52><b>"
                                                + RoleManager.getKingGender(p)
                                                + " "
                                                + p.getName().toUpperCase()
                                                + "</b><blue>, that player <red>does not exist."
                                )
                        );
                        return true;
                     }
                     if (KingsButBad.roles.get(pe) == Role.PEASANT) {
                        KingsButBad.invitations.put(pe, Role.KNIGHT);
                        pe.sendMessage(
                           CreateText.addColors(
                              "<gradient:#FFFF52:#FFBA52><b>"
                                 + RoleManager.getKingGender(p)
                                 + " "
                                 + p.getName().toUpperCase()
                                 + "</b><blue> has invited you to being a <gray>knight! <red>use /accept to accept."
                           )
                        );
                        sender.sendMessage(
                                CreateText.addColors(
                                        "<gradient:#FFFF52:#FFBA52><b>"
                                                + RoleManager.getKingGender(p)
                                                + " "
                                                + p.getName().toUpperCase()
                                                + "</b><blue> you, have invited "+pe.getName()+" to become a <gray>knight!"
                                )
                        );
                     } else {
                        p.sendMessage(
                           CreateText.addColors(
                              "<gradient:#FFFF52:#FFBA52><b>"
                                 + RoleManager.getKingGender(p)
                                 + p.getName().toUpperCase()
                                 + "</b><blue>, that player <red>isn't a peasant."
                           )
                        );
                     }
                  } else {
                     p.sendMessage(
                        CreateText.addColors(
                           "<gradient:#FFFF52:#FFBA52><b>"
                              + RoleManager.getKingGender(p)
                              + " "
                              + p.getName().toUpperCase()
                              + "</b><blue>, that player <red>does not exist."
                        )
                     );
                  }
               }

               if (args[0].equals("prince") && args.length > 1) {
                  if (Bukkit.getPlayer(args[1]) == null || Keys.vanish.get(Bukkit.getPlayer(args[1]), false)) {
                     p.sendMessage(
                        CreateText.addColors(
                           "<gradient:#FFFF52:#FFBA52><b>"
                              + RoleManager.getKingGender(p)
                              + " "
                              + p.getName().toUpperCase()
                              + "</b><blue>, that player <red>does not exist."
                        )
                     );
                  } else {
                     int i = 0;

                     for (Player de : Bukkit.getOnlinePlayers()) {
                        if (KingsButBad.roles.get(de).equals(Role.PRINCE)) {
                           i++;
                        }

                        if (KingsButBad.invitations.containsKey(de) && KingsButBad.invitations.get(de).equals(Role.PRINCE)) {
                           i++;
                        }
                     }

                     if (i >= 2) {
                        p.sendMessage(ChatColor.RED + "Too many princes! (Max: 2)");
                        return true;
                     }

                     Player pe = Bukkit.getPlayer(args[1]);
                     if (KingsButBad.roles.get(pe) == Role.PEASANT) {
                        KingsButBad.invitations.put(pe, Role.PRINCE);
                        pe.sendMessage(
                           CreateText.addColors(
                              "<gradient:#FFFF52:#FFBA52><b>"
                                 + RoleManager.getKingGender(p)
                                 + " "
                                 + p.getName().toUpperCase()
                                 + "</b><blue> has invited you to being <yellow>The Prince! <red>use /accept to accept."
                           )
                        );
                        sender.sendMessage(
                                CreateText.addColors(
                                        "<gradient:#FFFF52:#FFBA52><b>"
                                                + RoleManager.getKingGender(p)
                                                + " "
                                                + p.getName().toUpperCase()
                                                + "</b><blue> you, have invited "+sender+" to become a <yellow>The Prince!"
                                )
                        );
                     } else {
                        p.sendMessage(
                           CreateText.addColors(
                              "<gradient:#FFFF52:#FFBA52><b>"
                                 + RoleManager.getKingGender(p)
                                 + " "
                                 + p.getName().toUpperCase()
                                 + "</b><blue>, that player <red>isn't a peasant."
                           )
                        );
                     }
                  }
               }

               if (args[0].equals("prisonguard") && args.length > 1) {
                  if (Bukkit.getPlayer(args[1]) != null) {
                     Player pe = Bukkit.getPlayer(args[1]);
                     if(Keys.vanish.get(pe, false)){
                        p.sendMessage(
                                CreateText.addColors(
                                        "<gradient:#FFFF52:#FFBA52><b>"
                                                + RoleManager.getKingGender(p)
                                                + " "
                                                + p.getName().toUpperCase()
                                                + "</b><blue>, that player <red>does not exist."
                                )
                        );
                        return true;
                     }
                     if (KingsButBad.roles.get(pe) == Role.PEASANT) {
                        KingsButBad.invitations.put(pe, Role.PRISON_GUARD);
                        pe.sendMessage(
                           CreateText.addColors(
                              "<gradient:#FFFF52:#FFBA52><b>"
                                 + RoleManager.getKingGender(p)
                                 + " "
                                 + p.getName().toUpperCase()
                                 + "</b><blue> has invited you to being a <blue><b>Prison Guard<reset><blue>! <red>use /accept to accept."
                           )
                        );
                        sender.sendMessage(
                                CreateText.addColors(
                                        "<gradient:#FFFF52:#FFBA52><b>"
                                                + RoleManager.getKingGender(p)
                                                + " "
                                                + p.getName().toUpperCase()
                                                + "</b><blue> you, have invited "+pe.getName()+" to become a <blue><b>Prison Guard<reset><blue>!"
                                )
                        );
                     } else {
                        p.sendMessage(
                           CreateText.addColors(
                              "<gradient:#FFFF52:#FFBA52><b>"
                                 + RoleManager.getKingGender(p)
                                 + " "
                                 + p.getName().toUpperCase()
                                 + "</b><blue>, that player <red>isn't a peasant."
                           )
                        );
                     }
                  } else {
                     p.sendMessage(
                        CreateText.addColors(
                           "<gradient:#FFFF52:#FFBA52><b>"
                              + RoleManager.getKingGender(p)
                              + " "
                              + p.getName().toUpperCase()
                              + "</b><blue>, that player <red>does not exist."
                        )
                     );
                  }
               }

               if (args[0].equals("bodyguard") && args.length > 1) {
                  if (Bukkit.getPlayer(args[1]) != null) {
                     Player pe = Bukkit.getPlayer(args[1]);
                     if(Keys.vanish.get(pe, false)){
                        p.sendMessage(
                                CreateText.addColors(
                                        "<gradient:#FFFF52:#FFBA52><b>"
                                                + RoleManager.getKingGender(p)
                                                + " "
                                                + p.getName().toUpperCase()
                                                + "</b><blue>, that player <red>does not exist."
                                )
                        );
                        return true;
                     }
                     if (KingsButBad.roles.get(pe) == Role.PEASANT) {
                        KingsButBad.bodyLink.put(pe, p);
                        KingsButBad.invitations.put(pe, Role.BODYGUARD);
                        pe.sendMessage(
                           CreateText.addColors(
                              "<gradient:#FFFF52:#FFBA52><b>"
                                 + RoleManager.getKingGender(p)
                                 + " "
                                 + p.getName().toUpperCase()
                                 + "</b><blue> has invited you to being a <dark_gray><b>Body Guard<reset><blue>! <red>use /accept to accept."
                           )
                        );
                        sender.sendMessage(
                                CreateText.addColors(
                                        "<gradient:#FFFF52:#FFBA52><b>"
                                                + RoleManager.getKingGender(p)
                                                + " "
                                                + p.getName().toUpperCase()
                                                + "</b><blue> you, have invited "+pe.getName()+" to become a <dark_gray><b>Body Guard<reset><blue>!"
                                )
                        );
                     } else {
                        p.sendMessage(
                           CreateText.addColors(
                              "<gradient:#FFFF52:#FFBA52><b>"
                                 + RoleManager.getKingGender(p)
                                 + " "
                                 + p.getName().toUpperCase()
                                 + "</b><blue>, that player <red>isn't a peasant."
                           )
                        );
                     }
                  } else {
                     p.sendMessage(
                        CreateText.addColors(
                           "<gradient:#FFFF52:#FFBA52><b>"
                              + RoleManager.getKingGender(p)
                              + " "
                              + p.getName().toUpperCase()
                              + "</b><blue>, that player <red>does not exist."
                        )
                     );
                  }
               }

               if (args[0].equals("fire") && args.length > 1) {
                  if (Bukkit.getPlayer(args[1]) != null) {
                     Player pe = Bukkit.getPlayer(args[1]);
                     if(Keys.vanish.get(pe, false)){
                        p.sendMessage(
                                CreateText.addColors(
                                        "<gradient:#FFFF52:#FFBA52><b>"
                                                + RoleManager.getKingGender(p)
                                                + " "
                                                + p.getName().toUpperCase()
                                                + "</b><blue>, that player <red>does not exist."
                                )
                        );
                        return true;
                     }
                     if (KingsButBad.roles.get(pe) != Role.PEASANT && KingsButBad.roles.get(pe) != Role.KING) {
                        KingsButBad.roles.put(pe, Role.PEASANT);
                        RoleManager.givePlayerRole(pe);
                        sender.sendMessage(CreateText.addColors("<gray>You have banished <white>"+pe.getName()+"<gray>from under your command."));
                     } else {
                        p.sendMessage(
                           CreateText.addColors(
                              "<gradient:#FFFF52:#FFBA52><b>"
                                 + RoleManager.getKingGender(p)
                                 + " "
                                 + p.getName().toUpperCase()
                                 + "</b><blue>, that player <red>is a peasant."
                           )
                        );
                     }
                  } else {
                     p.sendMessage(
                        CreateText.addColors(
                           "<gradient:#FFFF52:#FFBA52><b>"
                              + RoleManager.getKingGender(p)
                              + " "
                              + p.getName().toUpperCase()
                              + "</b><blue>, that player <red>does not exist."
                        )
                     );
                  }
               }

               if (args[0].equals("help")) {
                  p.sendMessage(CreateText.addColors("<gray>----- <gradient:#FFFF52:#FFBA52>KING HELP <gray>-----"));
                  p.sendMessage(
                     CreateText.addColors(
                        "<blue>Hello, <gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingGender.toUpperCase() + p.getName().toUpperCase() + "</b><blue>."
                     )
                  );
                  p.sendMessage(CreateText.addColors("<blue>I'm the <gradient:#FFFF52:#FFBA52><b>ROYAL<blue> Villager.<blue> I help new kings get settled!"));
                  p.sendMessage(CreateText.addColors("<blue> Your goal is to <red>survive long<blue> and be <gold>powerful."));
                  p.sendMessage(
                     CreateText.addColors(
                        "<red>To get started, I'd reccomend getting some <gradient:#0095ff:#1e00ff>Knights<red> to help you in <dark_red>combat."
                     )
                  );
                  p.sendMessage("");
                  p.sendMessage(CreateText.addColors("<gray>----- <gradient:#FFFF52:#FFBA52>KING'S COMMANDS <gray>-----"));
                  p.sendMessage(CreateText.addColors("<blue>/<gradient:#FFFF52:#FFBA52>king<blue> help <gray>- Shows this menu."));
                  p.sendMessage(CreateText.addColors("<blue>/<gradient:#FFFF52:#FFBA52>king<blue> knight [name] <gray>- Knights a player."));
                  p.sendMessage(CreateText.addColors("<blue>/<gradient:#FFFF52:#FFBA52>king<blue> fire [name] <gray>- Fires a player."));
                  p.sendMessage(CreateText.addColors("<blue>/<gradient:#FFFF52:#FFBA52>king<blue> prisonguard [name] <gray>- Makes a player a Prison Guard."));
                  p.sendMessage(
                     CreateText.addColors(
                        "<blue>/<gradient:#FFFF52:#FFBA52>king<blue> bodyguard [name] <gray>- Makes a player a Body Guard.<red> (Will link to the player who SENT the invite!)"
                     )
                  );
                  p.sendMessage(
                     CreateText.addColors(
                        "<blue>/<gradient:#FFFF52:#FFBA52>king<blue> sidekick [name] [male/female/other] <gray>- Allow another player to be your second king/queen/monarch."
                     )
                  );
                  p.sendMessage(CreateText.addColors("<blue>/<gradient:#FFFF52:#FFBA52>king<blue> prince [name] <gray>- Makes another player a prince."));
                  p.sendMessage(
                     CreateText.addColors(
                        "<blue>/<gradient:#FFFF52:#FFBA52>king<blue> gender [male/female/other] <gray>- Changes you between King, Queen, Monarch or Among Us Impostor."
                     )
                  );
                  p.sendMessage(CreateText.addColors("<gray>----- -----"));
               }
            }
         } else if (KingsButBad.king2 == null) {
            p.sendMessage(
               CreateText.addColors(
                  "<red>>> <dark_red>You're not the <gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingGender.toUpperCase() + "<b></gradient><dark_red>!"
               )
            );
         } else {
            p.sendMessage(
               CreateText.addColors(
                  "<red>>> <dark_red>You're not the <gradient:#FFFF52:#FFBA52><b>"
                     + KingsButBad.kingGender.toUpperCase()
                     + "<b></gradient><red> or "
                     + KingsButBad.kingGender2.toUpperCase()
                     + " !"
               )
            );
         }
      }

      return true;
   }
}
