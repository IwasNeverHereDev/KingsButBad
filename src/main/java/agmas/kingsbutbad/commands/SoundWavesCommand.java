package agmas.kingsbutbad.commands;

import agmas.kingsbutbad.KingsButBad;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SoundWavesCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (sender instanceof Player p) {
         if (!KingsButBad.soundWaves.containsKey(p)) {
            p.sendMessage(ChatColor.GREEN + "turned sound waves on lmao");
            KingsButBad.soundWaves.put(p, true);
         } else {
            p.sendMessage(ChatColor.RED + "turned sound waves off lmao");
            KingsButBad.soundWaves.remove(p);
         }
      }

      return true;
   }
}
