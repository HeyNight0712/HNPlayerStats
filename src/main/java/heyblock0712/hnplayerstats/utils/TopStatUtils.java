package heyblock0712.hnplayerstats.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.*;
import java.util.stream.Collectors;

public class TopStatUtils {
    public static List<String> getTopPlayersByStatisticAndMaterial(Statistic statistic, Material material) {
        Map<UUID, String> allPlayerUUIDsAndNames = OfflinePlayerUtils.getAllPlayerUUIDsAndNames();

        Map<String, Integer> playerStats = new HashMap<>();

        for (Map.Entry<UUID, String> entry: allPlayerUUIDsAndNames.entrySet()) {
            UUID uuid = entry.getKey();
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

            if (player.hasPlayedBefore()) {
                int statValue = player.getStatistic(statistic, material);
                playerStats.put(entry.getValue(), statValue);
            }
        }

        List<String> topPlayers = playerStats.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .map(entry ->"&f" + entry.getKey() + "&7: &r" + "&e" + entry.getValue())
                .collect(Collectors.toList());

        List<String> rankedTopPlayers = new ArrayList<>();
        int rank = 1;
        for (String playerInfo : topPlayers) {
            rankedTopPlayers.add("&6" + rank + ". &r" + playerInfo);
            rank++;
        }

        return rankedTopPlayers;
    }

    public static List<String> getTopPlayersByStatisticAndEntityType(Statistic statistic, EntityType entityType) {
        Map<UUID, String> allPlayerUUIDsAndNames = OfflinePlayerUtils.getAllPlayerUUIDsAndNames();

        Map<String, Integer> playerStats = new HashMap<>();

        for (Map.Entry<UUID, String> entry: allPlayerUUIDsAndNames.entrySet()) {
            UUID uuid = entry.getKey();
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

            if (player.hasPlayedBefore()) {
                int statValue = player.getStatistic(statistic, entityType);
                playerStats.put(entry.getValue(), statValue);
            }
        }

        List<String> topPlayers = playerStats.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .map(entry ->"&f" + entry.getKey() + "&7: &r" + "&e" + entry.getValue())
                .collect(Collectors.toList());

        List<String> rankedTopPlayers = new ArrayList<>();
        int rank = 1;
        for (String playerInfo : topPlayers) {
            rankedTopPlayers.add("&6" + rank + ". &r" + playerInfo);
            rank++;
        }

        return rankedTopPlayers;
    }
}
