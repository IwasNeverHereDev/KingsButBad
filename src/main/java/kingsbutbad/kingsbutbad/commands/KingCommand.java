package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.Advancements.AdvancementManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.NoNoWords;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.tasks.MiscTask;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Pacts;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class KingCommand implements CommandExecutor { // TODO: Clean up This File (KingCommand.java)
   public static HashMap<UUID, Integer> kingLastTimer = new HashMap<>();
   @SuppressWarnings("deprecation")
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (sender instanceof Player p) {
         if (KingsButBad.roles.get(p).equals(Role.PRISONER)) {
            p.sendMessage(CreateText.addColors("<red>You can't become a king as a prisoner. Stay in, scum."));
            return true;
         }
         if(Keys.activePact.get(p, Pacts.NONE.name()) == Pacts.CRIMINAL.name()) {
            p.sendMessage(CreateText.addColors("<red>Your pacts says you can't become ANY kingdom role!"));
            return true;
         }

         if (KingsButBad.roles.get(p).equals(Role.PRINCE)) {
            if (args.length >= 2) {
               if (args[0].equals("prefix")) {
                  String restArgs = args[1];
                     if (args.length > 1) {
                        for (int i = 1; i < args.length; i++) {
                           restArgs += " " + args[i];
                        }
                     }

                     if (restArgs.isEmpty()) {
                        restArgs = "PRINCE";
                     } else {
                        restArgs = restArgs.trim();
                     }

                  if(!NoNoWords.isClean(restArgs)){
                     sender.sendMessage(CreateText.addColors("<red>You can't not set your Prefix a Filtered Word!"));
                     return true;
                  }
                  if(restArgs.length() >= 20){
                     sender.sendMessage(CreateText.addColors("<red>Sorry, Your prefix must be less then 15!"));
                     return true;
                  }
                  if(restArgs.contains("&k")){
                     sender.sendMessage(CreateText.addColors("<red>Sorry, You can't us Obstructed in your prefix!"));
                     return true;
                  }
                  List<String> listOfCommonRanks = new ArrayList<>();
                  listOfCommonRanks.add("Builder");
                  listOfCommonRanks.add("Owner");
                  listOfCommonRanks.add("Admin");
                  listOfCommonRanks.add("Mod");
                  listOfCommonRanks.add("Helper");
                  listOfCommonRanks.add("Booster");
                  listOfCommonRanks.add("Support");
                  listOfCommonRanks.addAll(Arrays.stream(Role.values())
                          .map(Role::name)
                          .collect(Collectors.toList()));
                  for(String noRanks :listOfCommonRanks) {
                     if (noRanks.toUpperCase().contains("PRINCE")) continue;
                     if (restArgs.toUpperCase().contains(noRanks.toUpperCase())) {
                        p.sendMessage(CreateText.addColors("<red>Sorry, Your prefix can't contain Common Ranks!"));
                        return true;
                     }
                  }
                  KingsButBad.princePrefix.put(p, CreateText.convertAmpersandToMiniMessage(restArgs));
               }

               return true;
            }

            return true;
         }

         if (KingsButBad.king == null) {
            if (KingsButBad.cooldown <= 0) {
               if (p.getUniqueId() == KingsButBad.lastKing || p.getUniqueId() == KingsButBad.lastKing2) {
                  p.sendMessage(CreateText.addColors("<red>You were king last time! <gray>(<white>"+ MiscTask.parseTicksToTime(kingLastTimer.getOrDefault(((Player) sender).getUniqueId(), 20*60*5)) +"<gray>)"));
                  if(!kingLastTimer.containsKey(((Player) sender).getUniqueId()))
                     kingLastTimer.put(((Player) sender).getUniqueId(), 20*60*3);
                  return true;
               }

               KingsButBad.thirst.put(p, 300F);
               KingsButBad.invitations.clear();
               KingsButBad.king = p;
               KingsButBad.roles.put(p, Role.KING);
               RoleManager.showKingMessages(p, Role.KING.objective);
               RoleManager.givePlayerRole(p);
               KingsButBad.kingPrefix = "King";
               KingsButBad.taxesPerctage = 0;
               AdvancementManager.giveAdvancement(p, "king");

               for (Player pe : Bukkit.getOnlinePlayers()) {
                   if(p.isInsideVehicle())
                       Objects.requireNonNull(pe.getVehicle()).removePassenger(pe);
                  if(pe == KingsButBad.king) continue;
                  Role role = KingsButBad.roles.getOrDefault(pe, Role.PEASANT);
                  if(role.isPowerful){
                     KingsButBad.roles.put(pe, Role.PEASANT);
                     RoleManager.givePlayerRole(pe);
                  }
                  pe.sendTitle(
                     CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>KING " + p.getName().toUpperCase()), ChatColor.GREEN + "is your new overlord!"
                  );
               }
            }

            return true;
         }

         if(KingsButBad.king == p && args.length > 0 && args[0].equals("taxes")){
               p.sendMessage(CreateText.addColors("<red>Coming soon..."));
               return true;
         }

         if (KingsButBad.king == p && args.length > 0 && args[0].equals("sidekick") && KingsButBad.king2 == null && args.length > 1) {
            if (Bukkit.getPlayer(args[1]) != null) {
               Player pe = Bukkit.getPlayer(args[1]);
               if(Keys.vanish.get(pe, false)){
                  p.sendMessage(
                          CreateText.addColors(
                                  "<gradient:#FFFF52:#FFBA52><b>"
                                          + KingsButBad.kingPrefix.toUpperCase()
                                          + " "
                                          + p.getName().toUpperCase()
                                          + "</b><blue>, that player <red>does not exist."
                          )
                  );
                  return true;
               }
               if(KingsButBad.lastKing == pe.getUniqueId() || KingsButBad.lastKing2 == pe.getUniqueId()){
                  sender.sendMessage(CreateText.addColors("<red>Sorry, You can't sidekick this Player! <gray>(<white>They've been king too recently!<white>)"));
                  return true;
               }
               if(Keys.activePact.get(pe,"") == Pacts.CRIMINAL.name()) {
                  p.sendMessage(CreateText.addColors("<red>The player's pact doesn't allow to do this!"));
                  return true;
               }
               if (KingsButBad.roles.get(pe) == Role.PEASANT) {
                  KingsButBad.invitations.put(pe, Role.KING);
                  if(pe != null)
                     pe.sendMessage(
                     CreateText.addColors(
                        "<gradient:#FFFF52:#FFBA52><b>"
                           + KingsButBad.kingPrefix.toUpperCase()
                           + " "
                           + p.getName().toUpperCase()
                           + "</b><blue> has invited you to being <gradient:#FFFF52:#FFBA52><b>their sidekick</b><gradient:#FFFF52:#FFBA52>! <red>use /accept to accept."
                     )
                  );
                  if(pe != null)
                   sender.sendMessage(
                          CreateText.addColors(
                                  "<gradient:#FFFF52:#FFBA52><b>"
                                          + KingsButBad.kingPrefix.toUpperCase()
                                          + " "
                                          + p.getName().toUpperCase()
                                          + "</b><blue> you, have invited "+pe.getName()+" to become <gradient:#FFFF52:#FFBA52><b>your sidekick</b><gradient:#FFFF52:#FFBA52>!"
                          )
                  );
               } else {
                  p.sendMessage(
                     CreateText.addColors(
                        "<gradient:#FFFF52:#FFBA52><b>"
                           + KingsButBad.kingPrefix.toUpperCase()
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
                        + KingsButBad.kingPrefix.toUpperCase()
                        + " "
                        + p.getName().toUpperCase()
                        + "</b><blue>, that player <red>does not exist."
                  )
               );
            }
         }

         if (RoleManager.isKingAtAll(p)) {
            if (args.length > 0) {
               if (args[0].equals("prefix")) {
                  String restArgs = "";
                  if (args.length > 1) {
                     for (int i = 1; i < args.length; i++) {
                        restArgs += " " + args[i];
                     }
                  }

                  if (restArgs.isEmpty()) {
                     restArgs = "KING";
                  } else {
                     restArgs = restArgs.trim();
                  }

                  RoleManager.setKingPrefix(p, restArgs);
                  return true;
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
                     if(Keys.activePact.get(pe,"") == Pacts.CRIMINAL.name()) {
                        p.sendMessage(CreateText.addColors("<red>The player's pact doesn't allow to do this!"));
                        return true;
                     }
                     if (KingsButBad.roles.get(pe) == Role.PEASANT) {
                        KingsButBad.invitations.put(pe, Role.KNIGHT);
                        if(pe != null)
                           pe.sendMessage(
                           CreateText.addColors(
                              "<gradient:#FFFF52:#FFBA52><b>"
                                 + RoleManager.getKingGender(p)
                                 + " "
                                 + p.getName().toUpperCase()
                                 + "</b><blue> has invited you to being a <gray>knight! <red>use /accept to accept."
                           )
                        );
                        if(pe != null)
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
                     if(KingsButBad.lastKing == pe.getUniqueId() || KingsButBad.lastKing2 == pe.getUniqueId()){
                        sender.sendMessage(CreateText.addColors("<red>Sorry, You can't /king Prince this Player! <gray>(<white>They were last King!<white>)"));
                        return true;
                     }
                     if(Keys.activePact.get(pe,"") == Pacts.CRIMINAL.name()) {
                        p.sendMessage(CreateText.addColors("<red>The player's pact doesn't allow to do this!"));
                        return true;
                     }
                     if (KingsButBad.roles.get(pe) == Role.PEASANT) {
                        KingsButBad.invitations.put(pe, Role.PRINCE);
                        if(pe != null)
                           pe.sendMessage(
                           CreateText.addColors(
                              "<gradient:#FFFF52:#FFBA52><b>"
                                 + RoleManager.getKingGender(p)
                                 + " "
                                 + p.getName().toUpperCase()
                                 + "</b><blue> has invited you to being <yellow>The Prince! <red>use /accept to accept."
                           )
                        );
                        if(pe != null)
                           sender.sendMessage(
                                CreateText.addColors(
                                        "<gradient:#FFFF52:#FFBA52><b>"
                                                + RoleManager.getKingGender(p)
                                                + " "
                                                + p.getName().toUpperCase()
                                                + "</b><blue> you, have invited "+ Objects.requireNonNull(((Player) sender).getPlayer()).getName() +" to become a <yellow>The Prince!"
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
                     if(pe == null) return true;
                     if(Keys.activePact.get(pe,"") == Pacts.CRIMINAL.name()) {
                        p.sendMessage(CreateText.addColors("<red>The player's pact doesn't allow to do this!"));
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
                     if(pe == null) return true;
                     if(Keys.activePact.get(pe,"") == Pacts.CRIMINAL.name()) {
                        p.sendMessage(CreateText.addColors("<red>The player's pact doesn't allow to do this!"));
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
                     if(pe == null) return true;
                     if (KingsButBad.roles.get(pe) != Role.KING && KingsButBad.roles.get(pe).isPowerful) {
                        pe.eject();
                        pe.getPassengers().forEach(Entity::eject);
                        KingsButBad.roles.put(pe, Role.PEASANT);
                        RoleManager.givePlayerRole(pe);
                        sender.sendMessage(CreateText.addColors("<gray>You have banished <white>"+pe.getName()+" <gray>from under your command."));
                        pe.sendMessage(CreateText.addColors("<gray>You have banished by <white>"+sender.getName()+" <gray>from under there command."));
                     } else {
                        p.sendMessage(
                           CreateText.addColors(
                              "<gradient:#FFFF52:#FFBA52><b>"
                                 + RoleManager.getKingGender(p)
                                 + " "
                                 + p.getName().toUpperCase()
                                 + "</b><blue>, that player <red>is not under your command."
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
                        "<blue>Hello, <gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingPrefix.toUpperCase() + p.getName().toUpperCase() + "</b><blue>."
                     )
                  );
                  p.sendMessage(CreateText.addColors("<blue>I'm the <gradient:#FFFF52:#FFBA52><b>ROYAL<blue> Villager.<blue> I help new kings get settled!"));
                  p.sendMessage(CreateText.addColors("<blue> Your goal is to <red>survive long<blue> and be <gold>powerful."));
                  p.sendMessage(
                     CreateText.addColors(
                        "<red>To get started, I'd recommend getting some <gradient:#0095ff:#1e00ff>Knights<red> to help you in <dark_red>combat."
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
                  "<red>>> <dark_red>You're not the <gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingPrefix.toUpperCase() + "<b></gradient><dark_red>!"
               )
            );
         } else {
            p.sendMessage(
               CreateText.addColors(
                  "<red>>> <dark_red>You're not the <gradient:#FFFF52:#FFBA52><b>"
                     + KingsButBad.kingPrefix.toUpperCase()
                     + "<b></gradient><red> or "
                     + KingsButBad.kingPrefix2.toUpperCase()
                     + " !"
               )
            );
         }
      }

      return true;
   }
}
