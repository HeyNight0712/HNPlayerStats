package heyblock0712.hnplayerstats.composition.inventory;

import heyblock0712.hnplayerstats.HNPlayerStats;
import heyblock0712.hnplayerstats.listener.InventoryClickListener;
import heyblock0712.hnplayerstats.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StatKilled {
    public static void restartKillEntityMenu(JavaPlugin plugin, Player player, int page) {
        player.closeInventory();
        player.sendMessage(HNPlayerStats.getInstance().getLanguageCore().getString("view.loading"));

        new BukkitRunnable() {
            @Override
            public void run() {
                int totalPages = InventoryClickListener.calculateTotalEntityTypePages(plugin);
                int currentPage = Math.max(1, Math.min(page, totalPages));
                Inventory inventory = Bukkit.createInventory(player, 54, BasicUtils.setColor("生物擊殺統計 &f頁數: &b" + currentPage + "&f/&b" + totalPages));

                // 裝飾
                InventoryUtils.setInventory(inventory)
                        .setDecorate(new int[] {45, 46, 48, 50, 52, 53})
                        .setBack(49)
                        .setPrevPage(47)
                        .setNextPage(51);

                int startIndex = (currentPage - 1) * 45;
                int endIndex = startIndex + 45;
                int currentIndex = 0;
                int slot = 0;

                Set<EntityType> filteredEntities = FilterUtils.filterEntities(plugin);
                // 遍历所有实体类型
                for (EntityType entityType : EntityType.values()) {
                    if (entityType.isSpawnable() && entityType.isAlive() && !filteredEntities.contains(entityType)) {
                        if (currentIndex >= startIndex && currentIndex < endIndex) {
                            String entityName = HNPlayerStats.getInstance().getLanguageCore().getString("entity.minecraft." + entityType.toString().toLowerCase());
                            if (plugin.getConfig().getBoolean("debug")) {plugin.getLogger().info("killView: " + slot + " " + entityType + " " + entityName);}
                            Material spawnEgg = Material.matchMaterial(entityType.name() + "_SPAWN_EGG");
                            int killCount = player.getStatistic(Statistic.KILL_ENTITY, entityType);
                            List<String> lore = new ArrayList<>();

                            List<String> topMiners = TopStatUtils.getTopPlayersByStatisticAndEntityType(Statistic.KILL_ENTITY, entityType);
                            if (spawnEgg != null) {
                                ItemStack item = new ItemStack(spawnEgg);
                                ItemUtils.setDisplayName(item, "&f" + entityName);
                                lore.addAll(topMiners);
                                lore.add("&7擊殺數: &a" + killCount);
                                ItemUtils.setLore(item, lore);
                                inventory.setItem(slot++, item);
                            } else {
                                ItemStack item = new ItemStack(Material.EGG);
                                ItemUtils.setDisplayName(item, "&f" + entityName);
                                lore.addAll(topMiners);
                                lore.add("&7擊殺數: &a" + killCount);
                                ItemUtils.setLore(item, lore);
                                inventory.setItem(slot++, item);
                            }
                        }
                        currentIndex++;
                        if (slot >= 45) break;
                    }
                }

                // 主線程打開背包
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.openInventory(inventory);
                        InventoryClickListener.setMetadata(plugin, player, "KILLED");
                    }
                }.runTask(plugin);
            }
        }.runTaskAsynchronously(plugin);
    }
}
