package agmas.kingsbutbad.Loaders;

import agmas.kingsbutbad.KingsButBad;
import agmas.kingsbutbad.CommandsCompleters.KingCommandTabCompleter;
import agmas.kingsbutbad.CommandsCompleters.PayCommandTabCompleter;
import agmas.kingsbutbad.CommandsCompleters.Dev.SetMoneyTabCompleter;
import agmas.kingsbutbad.CommandsCompleters.Dev.SwapKingdomTabCompleter;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

public class LoadTabCompleters {
   public static void init() {
      setTabCompleter("king", new KingCommandTabCompleter());
      setTabCompleter("setMoney", new SetMoneyTabCompleter());
      setTabCompleter("pay", new PayCommandTabCompleter());
      setTabCompleter("swapKingdom", new SwapKingdomTabCompleter());
   }

   private static void setTabCompleter(String cmd, TabCompleter tabCompleter) {
      PluginCommand pluginCommand = KingsButBad.pl.getCommand(cmd);
      if (pluginCommand != null) {
         pluginCommand.setTabCompleter(tabCompleter);
      }
   }
}
