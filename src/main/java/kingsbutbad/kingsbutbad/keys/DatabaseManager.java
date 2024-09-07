package kingsbutbad.kingsbutbad.keys;

import kingsbutbad.kingsbutbad.KingsButBad;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DatabaseManager implements Serializable {
    @Serial
    private static final long serialVersionUID = -1681012206529286330L;

    private final HashMap<UUID, HashMap<String, Object>> userKeys;

    public DatabaseManager(HashMap<UUID, HashMap<String, Object>> userKeys) {
        this.userKeys = userKeys;
    }

    public DatabaseManager(DatabaseManager loadedData) {
        this.userKeys = loadedData.userKeys;
    }

    public boolean saveData(String filePath) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             GZIPOutputStream gzipOut = new GZIPOutputStream(fileOut);
             ObjectOutputStream out = new ObjectOutputStream(gzipOut)) {
            out.writeObject(this);
            return true;
        } catch (IOException e) {
            Bukkit.getLogger().severe(e.getMessage());
            return false;
        }
    }

    public static DatabaseManager loadData(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist: " + filePath);
            return new DatabaseManager(new HashMap<>());
        }

        try (FileInputStream fileIn = new FileInputStream(filePath);
             GZIPInputStream gzipIn = new GZIPInputStream(fileIn);
             ObjectInputStream in = new ObjectInputStream(gzipIn)) {
            DatabaseManager loadedData = (DatabaseManager) in.readObject();
            if (loadedData == null || loadedData.userKeys == null) {
                return new DatabaseManager(new HashMap<>());
            }
            return loadedData;
        } catch (ClassNotFoundException | IOException e) {
            Bukkit.getLogger().severe(e.getMessage());
            return new DatabaseManager(new HashMap<>());
        }
    }

    public Object getValue(UUID playerId, String keyName) {
        HashMap<String, Object> playerKeys = userKeys.get(playerId);
        if (playerKeys != null) {
            return playerKeys.getOrDefault(keyName, null);
        }
        return null;
    }

    public void setValue(UUID playerId, String keyName, Object value) {
        userKeys.computeIfAbsent(playerId, k -> new HashMap<>()).put(keyName, value);
    }

    public static File getDataFolder() {
        File dataFolder = KingsButBad.pl.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        return dataFolder;
    }

    public static Object getKeyPlayer(OfflinePlayer player, String keyName) {
        UUID playerId = player.getUniqueId();
        DatabaseManager dbManager = loadData(new File(getDataFolder(), "playerdata.db").getPath());
        return dbManager.getValue(playerId, keyName);
    }
}