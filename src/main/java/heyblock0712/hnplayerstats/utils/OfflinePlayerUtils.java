package heyblock0712.hnplayerstats.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.*;

public class OfflinePlayerUtils {
    public static Map<UUID, String> getAllPlayerUUIDsAndNames() {
        Map<UUID, String> playerInfo = new HashMap<>();

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {

            UUID uuid = player.getUniqueId();
            String name = player.getName();

            if (name != null) {
                playerInfo.put(uuid, name);
            }
        }
        return playerInfo;
    }
}
