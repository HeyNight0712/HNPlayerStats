package heyblock0712.hnplayerstats.composition.inventory;

import heyblock0712.hnplayerstats.HNPlayerStats;
import heyblock0712.hnplayerstats.listener.InventoryClickListener;
import heyblock0712.hnplayerstats.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StatMined {
    public static void restartDigMenu(JavaPlugin plugin, Player player, int page) {
        player.closeInventory();
        player.sendMessage(HNPlayerStats.getInstance().getLanguageCore().getString("view.loading"));

        new BukkitRunnable() {
            @Override
            public void run() {
                int totalPages = InventoryClickListener.calculateTotalPages(plugin);
                int currentPage = Math.max(1, Math.min(page, totalPages));
                Inventory inventory = Bukkit.createInventory(player, 54, BasicUtils.setColor("Stat 挖掘次數 &f頁數: &b" + currentPage + "&f/&b" + totalPages));

                 // 裝飾
                InventoryUtils.setInventory(inventory)
                        .setDecorate(new int[] {45, 46, 48, 50, 52, 53})
                        .setBack(49)
                        .setPrevPage(47)
                        .setNextPage(51);

                // 頁數物品 44/1 Page
                int startIndex = (currentPage - 1) * 45;
                int endIndex = startIndex + 45;
                int currentIndex = 0;

                // 統計材料
                Set<Material> filterBlocks = FilterUtils.filterBlcoks(plugin); // 過濾
                Material[] blockMaterials = Material.values();
                int slot = 0;
                for (Material material : blockMaterials) {
                    if (material.isBlock() && !filterBlocks.contains(material)) {
                        if (currentIndex >= startIndex && currentIndex < endIndex) {
                            if (plugin.getConfig().getBoolean("debug")) {plugin.getLogger().info("digView: "+ slot + " " + material);};
                            ItemStack item = new ItemStack(material);

                            // lore
                            int digCount = player.getStatistic(Statistic.MINE_BLOCK, material);
                            List<String> lore = new  ArrayList<>();

                            List<String> topMiners = TopStatUtils.getTopPlayersByStatisticAndMaterial(Statistic.MINE_BLOCK, material);
                            lore.addAll(topMiners);
                            lore.add("&7挖掘數量: &a" + digCount);
                            ItemUtils.setLore(item, lore);

                            inventory.setItem(slot++, item);
                        }
                        currentIndex++;
                        if (slot >= 45) break;
                    }
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.openInventory(inventory);
                        InventoryClickListener.setMetadata(plugin, player, "MINED");
                    }
                }.runTask(plugin);
            }
        }.runTaskAsynchronously(plugin);
    }
}
