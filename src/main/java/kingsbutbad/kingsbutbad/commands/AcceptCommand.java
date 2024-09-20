package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AcceptCommand implements CommandExecutor {
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (sender instanceof Player p) {
         switch (KingsButBad.invitations.get(p)) {
            case KING:
               KingsButBad.roles.put(p, Role.KING);
               RoleManager.showKingMessages(
                  p, CreateText.addColors("<dark_gray>You were sidekicked; Welcome, <gradient:#FFFF52:#FFBA52><b>" + KingsButBad.kingPrefix2.toUpperCase())
               );
               KingsButBad.king2 = p;
               RoleManager.givePlayerRole(p);
               break;
            case KNIGHT:
               KingsButBad.roles.put(p, Role.KNIGHT);
               RoleManager.givePlayerRole(p);
               break;
            case PRISON_GUARD:
               KingsButBad.roles.put(p, Role.PRISON_GUARD);
               RoleManager.givePlayerRole(p);
               break;
            case BODYGUARD:
               KingsButBad.roles.put(p, Role.BODYGUARD);
               RoleManager.givePlayerRole(p);
               break;
            case PRINCE:
               KingsButBad.roles.put(p, Role.PRINCE);
               RoleManager.givePlayerRole(p);
               break;
            default:
               p.sendMessage(
                  CreateText.addColors(
                     "<gradient:#FFFF52:#FFBA52><b>"
                        + KingsButBad.kingPrefix.toUpperCase()
                        + KingsButBad.king.getName().toUpperCase()
                        + "</b><red> hasn't invited you to being any roles."
                  )
               );
               return true;
         }

         KingsButBad.invitations.remove(p);
      }

      return true;
   }
}
