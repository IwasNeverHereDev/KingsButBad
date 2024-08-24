package kingsbutbad.kingsbutbad.Loaders;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.commands.*;
import kingsbutbad.kingsbutbad.commands.Builders.BuilderChatCommand;
import kingsbutbad.kingsbutbad.commands.Builders.BuilderModeCommand;
import kingsbutbad.kingsbutbad.commands.Builders.MapsCommand;
import kingsbutbad.kingsbutbad.commands.Builders.SwapKingdomCommand;
import kingsbutbad.kingsbutbad.commands.Dev.*;
import kingsbutbad.kingsbutbad.commands.Misc.DiscordCommand;
import kingsbutbad.kingsbutbad.commands.Misc.LinkCommand;
import kingsbutbad.kingsbutbad.commands.Misc.UnLinkCommand;
import kingsbutbad.kingsbutbad.commands.Staff.StaffChatCommand;
import kingsbutbad.kingsbutbad.commands.Staff.VanishCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;

public class LoadCommands {
   public static void init() {
      setExecutor("king", new KingCommand());
      setExecutor("accept", new AcceptCommand());
      setExecutor("setMoney", new SetMoneyCommand());
      setExecutor("resign", new ResignCommand());
      setExecutor("discord", new DiscordCommand());
      setExecutor("soundWaves", new SoundWavesCommand());
      setExecutor("resetVillagers", new ResetVillagersCommand());
      setExecutor("pay", new PayCommand());
      setExecutor("swapKingdom", new SwapKingdomCommand());
      setExecutor("serverStats", new ServerStatsCommand());
      setExecutor("maps", new MapsCommand());
      setExecutor("cells", new CellCommand());
      setExecutor("builderMode", new BuilderModeCommand());
      setExecutor("setRole", new SetRoleCommand());
      setExecutor("link", new LinkCommand());
      setExecutor("whois", new WhoisCommand());
      setExecutor("unlink", new UnLinkCommand());
      setExecutor("reloadFilter", new ReloadFilterCommand());
      setExecutor("kbbSettings", new kbbSettingsCommand());
      setExecutor("staffChat", new StaffChatCommand());
      setExecutor("builderChat", new BuilderChatCommand());
      setExecutor("vanish", new VanishCommand());
   }

   private static void setExecutor(String cmd, CommandExecutor executor) {
      PluginCommand command = KingsButBad.pl.getCommand(cmd);
      if (command != null) {
         command.setExecutor(executor);
      }
   }
}
