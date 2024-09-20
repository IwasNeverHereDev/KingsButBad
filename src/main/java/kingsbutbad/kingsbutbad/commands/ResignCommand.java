package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.FormatUtils;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ResignCommand implements CommandExecutor {
   public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
      if (sender instanceof Player p && !p.hasCooldown(Material.RED_STAINED_GLASS)) {
         Role role = KingsButBad.roles.getOrDefault(p, Role.PEASANT);
         if (role.equals(Role.PRISONER)) {
            sender.sendMessage(CreateText.addColors("<red>You're stuck here, filth."));
            return true;
         }

         if (role.equals(Role.KING) && KingsButBad.king == p) {
            sender.sendMessage(CreateText.addColors("<gold>Drop your crown instead!"));
            return true;
         }

         if(role.equals(Role.CRIMINAL) && p.hasPotionEffect(PotionEffectType.BAD_OMEN)){
            sender.sendMessage(CreateText.addColors("<red>Pls wait before doing this as Criminal! <gray>(<white>"+ FormatUtils.parseDoubleTicksToTime(Objects.requireNonNull(p.getPotionEffect(PotionEffectType.BAD_OMEN)).getDuration()) +"<gray>)"));
            return true;
         }

         KingsButBad.roles.put(p, Role.PEASANT);
         if(KingsButBad.king2 == p)
            KingsButBad.king2 = null;
         RoleManager.givePlayerRole(p);
      }
      return true;
   }
}
