package agmas.kingsbutbad.commands;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.utils.Role;
import agmas.kingsbutbad.utils.RoleManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResignCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (sender instanceof Player p && !p.hasCooldown(Material.RED_STAINED_GLASS)) {
         if (KingsButBad.roles.get(p).equals(Role.PRISONER)) {
            sender.sendMessage(ChatColor.RED + "You're stuck here, filth.");
            return true;
         }

         if (KingsButBad.roles.get(p).equals(Role.KING)) {
            sender.sendMessage(ChatColor.GOLD + "Drop your crown instead!");
            return true;
         }

         KingsButBad.roles.put(p, Role.PEASANT);
         RoleManager.givePlayerRole(p);
      }

      return true;
   }
}
