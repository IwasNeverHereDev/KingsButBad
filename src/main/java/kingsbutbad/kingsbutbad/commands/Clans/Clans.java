package kingsbutbad.kingsbutbad.commands.Clans;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Clans implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567890123456789L;
    private List<UUID> leaders;
    private List<UUID> generals;
    private List<UUID> members;
    private List<ClanQuests> activeGangQuests;
    private HashMap<UUID, Double> amountPutInBank;
    private String name;
    private double bankAmount;
    private long level;
    private double exp;
    public Clans(List<UUID> leaders, List<UUID> generals, List<UUID> members, String name, double bankAmount, long level, double exp, HashMap<UUID, Double> amountPutInBank, List<ClanQuests> activeGangQuests) {
        this.leaders = leaders;
        this.generals = generals;
        this.members = members;
        this.name = name;
        this.bankAmount = bankAmount;
        this.level = level;
        this.exp = exp;
        this.amountPutInBank = amountPutInBank;
        this.activeGangQuests = activeGangQuests;
    }
    public HashMap<UUID, Double> getAmountPutInBank() {
        return amountPutInBank;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getExp() {
        return Math.min(exp, 0.0);
    }
    public void setExp(double exp) {
        this.exp = exp;
    }
    public double getBankAmount() {
        return bankAmount;
    }
    public void setBankAmount(double bankAmount) {
        this.bankAmount = bankAmount;
    }
    public List<UUID> getGenerals() {
        return generals;
    }
    public void setGenerals(List<UUID> generals) {
        this.generals = generals;
    }
    public List<UUID> getLeaders() {
        return leaders;
    }
    public void setLeaders(List<UUID> leaders) {
        this.leaders = leaders;
    }
    public List<UUID> getMembers() {
        return members;
    }
    public void setMembers(List<UUID> members) {
        this.members = members;
    }
    public long getLevel() {
        return level;
    }
    public void setLevel(long level) {
        this.level = level;
    }
    public List<ClanQuests> getActiveGangQuests() {
        return activeGangQuests;
    }
    public void setActiveGangQuests(List<ClanQuests> activeGangQuests) {
        this.activeGangQuests = activeGangQuests;
    }
    public void broadcast(String message){
        String msg = CreateText.addColors("<gray>[<red>"+name+" Clan<gray>] <gray>(Broadcast): "+message);
        for(Player p : Bukkit.getOnlinePlayers()){
            if(generals.contains(p.getUniqueId())) p.sendMessage(msg);
            if(leaders.contains(p.getUniqueId())) p.sendMessage(msg);
            if(members.contains(p.getUniqueId())) p.sendMessage(msg);
            if(p.hasPermission("kbb.staff.spy")) p.sendMessage(CreateText.addColors("<red>(Staff) (Clan Spy) <white>")+msg);
        }
        BotManager.getStafflogChannel().sendMessage("(Staff) (Clan Spy) "+msg).queue();
    }
    public void sendChat(Player player, String message) {
        // Get the player's role in the clan
        String role = getRole(player); // This method should return "General", "Leader", or "Member"

        // Format the chat message with the clan's name, role, and player's username
        String msg = CreateText.addColors("<gray>[<red>" + name + " Clan<gray>] <yellow>" + role + " " + player.getName() + "<gray>: " + message);

        // Send the message to players based on their roles
        for (Player p : Bukkit.getOnlinePlayers()) {
            // Send to generals
            if (generals.contains(p.getUniqueId())) {
                p.sendMessage(msg);
            }
            // Send to leaders
            if (leaders.contains(p.getUniqueId())) {
                p.sendMessage(msg);
            }
            // Send to members
            if (members.contains(p.getUniqueId())) {
                p.sendMessage(msg);
            }
            // Send to staff with spy permission
            if (p.hasPermission("kbb.staff.spy")) {
                p.sendMessage(CreateText.addColors("<red>(Staff) (Clan Spy) <white>") + msg);
            }
        }

        // Also log the message to the staff log channel (for Discord or other external logs)
        BotManager.getStafflogChannel().sendMessage("(Staff) (Clan Spy) " + msg).queue();
    }
    public String getRole(Player player) {
        if (generals.contains(player.getUniqueId())) {
            return "General";
        }
        if (leaders.contains(player.getUniqueId())) {
            return "Leader";
        }
        if (members.contains(player.getUniqueId())) {
            return "Member";
        }
        return "Member"; // Default role if no specific role is found
    }
}
