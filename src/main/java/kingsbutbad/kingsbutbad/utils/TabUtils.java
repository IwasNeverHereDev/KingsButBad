package kingsbutbad.kingsbutbad.utils;

import kingsbutbad.kingsbutbad.KingsButBad;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class TabUtils {
    public static HashMap<Player, Integer> tabValues = new HashMap<>();

    public static void reload() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        for (Player p : Bukkit.getOnlinePlayers()) {
            Role role = KingsButBad.roles.getOrDefault(p, Role.PEASANT);
            int roleValue = getRoleValue(role);

            tabValues.put(p, roleValue);

            String teamName = "Rank" + roleValue + getLuckPermsWeight(p);
            if (teamName.length() > 16)
                teamName = teamName.substring(0, 16);

            Team team = scoreboard.getTeam(teamName);
            if (team == null)
                team = scoreboard.registerNewTeam(teamName);

            team.addEntry(p.getName());
        }
    }

    private static int getRoleValue(Role role) {
        int roleValue;

        switch (role) {
            case OUTLAW -> roleValue = 9;
            case CRIMINAL -> roleValue = 8;
            case PRISONER -> roleValue = 7;
            case SERVANT -> roleValue = 5;
            case KNIGHT -> roleValue = 4;
            case PRISON_GUARD -> roleValue = 3;
            case BODYGUARD -> roleValue = 2;
            case PRINCE -> roleValue = 1;
            case KING -> roleValue = 0;
            default -> roleValue = 6;
        }
        return roleValue;
    }

    private static int getLuckPermsWeight(Player player) {
        User user = KingsButBad.api.getUserManager().getUser(player.getUniqueId());
        if (user == null)
            return 0;

        return user.getNodes().stream()
                .filter(InheritanceNode.class::isInstance)
                .map(InheritanceNode.class::cast)
                .mapToInt(node -> KingsButBad.api.getGroupManager().getGroup(node.getGroupName()).getWeight().orElse(0))
                .max().orElse(0);
    }
}
