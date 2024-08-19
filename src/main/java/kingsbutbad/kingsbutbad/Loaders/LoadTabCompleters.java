package kingsbutbad.kingsbutbad.Loaders;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.CommandsCompleters.KingCommandTabCompleter;
import kingsbutbad.kingsbutbad.CommandsCompleters.PayCommandTabCompleter;
import kingsbutbad.kingsbutbad.CommandsCompleters.Dev.SetMoneyTabCompleter;
import kingsbutbad.kingsbutbad.CommandsCompleters.Dev.SwapKingdomTabCompleter;
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
