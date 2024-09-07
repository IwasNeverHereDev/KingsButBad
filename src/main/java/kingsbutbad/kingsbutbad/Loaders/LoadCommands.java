package kingsbutbad.kingsbutbad.Loaders;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.commands.*;
import kingsbutbad.kingsbutbad.commands.Builders.*;
import kingsbutbad.kingsbutbad.commands.Builders.KingdomsCommands.MapsCommand;
import kingsbutbad.kingsbutbad.commands.Builders.KingdomsCommands.SwapKingdomCommand;
import kingsbutbad.kingsbutbad.commands.Dev.*;
import kingsbutbad.kingsbutbad.commands.Clans.ClanCommand;
import kingsbutbad.kingsbutbad.commands.Leaderboards.LeaderboardCommand;
import kingsbutbad.kingsbutbad.commands.Misc.ChangelogCommand;
import kingsbutbad.kingsbutbad.commands.Misc.DiscordCommand;
import kingsbutbad.kingsbutbad.commands.Misc.LinkCommand;
import kingsbutbad.kingsbutbad.commands.Misc.UnLinkCommand;
import kingsbutbad.kingsbutbad.commands.Staff.ClearChatCommand;
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
      setExecutor("resetVillagers", new ResetVillagersCommand());
      setExecutor("pay", new PayCommand());
      setExecutor("swapKingdom", new SwapKingdomCommand());
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
      setExecutor("clearchat", new ClearChatCommand());
      setExecutor("rules", new RulesCommand());
      setExecutor("reboot", new RebootCommand());
      setExecutor("playtime", new PlaytimeCommand());
      setExecutor("changelog", new ChangelogCommand());
      setExecutor("hello", new HelloCommand());
      setExecutor("key", new KeysCommand());
      setExecutor("leaderboard", new LeaderboardCommand());
      setExecutor("clan", new ClanCommand());
      setExecutor("debugclan", new DebugClanCommand());
      setExecutor("kingdomreload", new KingdomReloadCommand());
   }

   private static void setExecutor(String cmd, CommandExecutor executor) {
      PluginCommand command = KingsButBad.pl.getCommand(cmd);
      if (command != null) {
         command.setExecutor(executor);
      }
   }
}
