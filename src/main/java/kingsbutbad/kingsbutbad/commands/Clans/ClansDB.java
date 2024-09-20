package kingsbutbad.kingsbutbad.commands.Clans;

import kingsbutbad.kingsbutbad.KingsButBad;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ClansDB implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567890123456789L;

    private final HashMap<String, Clans> gangs;

    public ClansDB(HashMap<String, Clans> gangs) {
        this.gangs = gangs;
    }

    public ClansDB(ClansDB loadedData) {
        this.gangs = new HashMap<>(loadedData.gangs);
    }

    public void saveData(String filePath) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             GZIPOutputStream gzipOut = new GZIPOutputStream(fileOut);
             ObjectOutputStream out = new ObjectOutputStream(gzipOut)) {
            out.writeObject(this);
        } catch (IOException e) {
            Bukkit.getLogger().severe(e.getMessage());
        }
    }

    public static ClansDB loadData(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            Bukkit.getLogger().severe("File does not exist: " + filePath);
            return new ClansDB(new HashMap<>());
        }

        try (FileInputStream fileIn = new FileInputStream(filePath);
             GZIPInputStream gzipIn = new GZIPInputStream(fileIn);
             ObjectInputStream in = new ObjectInputStream(gzipIn)) {
            ClansDB loadedData = (ClansDB) in.readObject();
            return loadedData != null ? loadedData : new ClansDB(new HashMap<>());
        } catch (ClassNotFoundException | IOException e) {
            Bukkit.getLogger().severe(e.getMessage());
            return new ClansDB(new HashMap<>());
        }
    }

    public HashMap<String, Clans> getGangs() {
        return gangs;
    }
    public void addExpToUsersGang(Player p, double amount){
        Clans targetGang = getPlayerGang(p);
        if(targetGang == null) return;
        targetGang.setExp(targetGang.getExp() + amount);
    }
    private Clans getPlayerGang(Player player) {
        for (Map.Entry<String, Clans> entry : getGangs().entrySet()) {
            if (entry.getValue().getMembers().contains(player.getUniqueId()))
                return entry.getValue();
            if (entry.getValue().getGenerals().contains(player.getUniqueId()))
                return entry.getValue();
            if (entry.getValue().getLeaders().contains(player.getUniqueId()))
                return entry.getValue();
        }
        return null;
    }

    public static File getDataFolder() {
        File dataFolder = KingsButBad.pl.getDataFolder();
        if (!dataFolder.exists())
            dataFolder.mkdirs();
        return dataFolder;
    }
}
