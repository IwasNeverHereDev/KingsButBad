package kingsbutbad.kingsbutbad.CommandsCompleters.Dev;

import kingsbutbad.kingsbutbad.Kingdom.Kingdom;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsReader;
import kingsbutbad.kingsbutbad.Loaders.LoadKingdoms;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SwapKingdomTabCompleter implements TabCompleter {
   @Nullable
   public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
      List<String> suggestions = new ArrayList<>();
      LoadKingdoms.reload();
      if (strings.length == 1) {
         String currentInput = strings[0].toUpperCase();

         for (Kingdom kingdom : KingdomsReader.kingdomsList) {
            String kingdomName = kingdom.getName().toUpperCase();
            if (kingdomName.startsWith(currentInput)) {
               suggestions.add(kingdom.getName());
            }
         }
      }

      return suggestions;
   }
}
