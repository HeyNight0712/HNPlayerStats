package heyblock0712.hnplayerstats.listener;

import heyblock0712.hnplayerstats.HNPlayerStats;
import heyblock0712.hnplayerstats.composition.inventory.StatKilled;
import heyblock0712.hnplayerstats.composition.inventory.StatMenu;
import heyblock0712.hnplayerstats.composition.inventory.StatMined;
import heyblock0712.hnplayerstats.utils.FilterUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Set;

public class InventoryClickListener implements Listener {
    private final JavaPlugin plugin;

    public InventoryClickListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public enum ViewType {
        MENU,
        MINED,
        KILLED,
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (player.hasMetadata("StatInventory")) {
            MetadataValue metadataValue = player.getMetadata("StatInventory").get(0);
            if (metadataValue.asBoolean()) {
                event.setCancelled(true);

                MetadataValue viewMata = player.getMetadata("view").get(0);
                // if (viewMata == null || viewMata.value() instanceof String) { return; }
                String view = (String) viewMata.value();
                HNPlayerStats config = HNPlayerStats.getInstance();

                int currentPage = player.getMetadata("currentPage").isEmpty() ? 1 : player.getMetadata("currentPage").get(0).asInt();


                switch (view) {
                    case "MENU":
                        if (event.getRawSlot() == config.getConfigCore().getInt("menu.mined.slot")) {
                            StatMined.restartDigMenu(plugin, player, 1);
                            setMetadata(plugin, player, "MINED");
                        } else if (event.getRawSlot() == config.getConfigCore().getInt("menu.killed.slot")) {
                            StatKilled.restartKillEntityMenu(plugin, player, 1);
                            setMetadata(plugin, player, "KILLED");
                        } else if (event.getRawSlot() == 49) {
                            player.closeInventory();
                        }
                        break;
                    case "MINED":
                        restartMenu(player, event.getRawSlot(), ViewType.MINED, currentPage);
                        break;
                    case "KILLED":
                        restartMenu(player, event.getRawSlot(), ViewType.KILLED, currentPage);
                        break;
                }
            }
        }
    }

    private void restartMenu(Player player, int rawSlot, ViewType viewType, int currentPage) {
        int totalPages = switch (viewType) {
            case MINED -> calculateTotalPages(plugin);
            case KILLED -> calculateTotalEntityTypePages(plugin);
            default -> 0;
        };

        // Debug
        if (plugin.getConfig().getBoolean("debug")) {plugin.getLogger().info("Clicked slot: "+ rawSlot + " " + viewType);}

        // 上一頁
        if (rawSlot == 47) {
            currentPage = Math.max(1, currentPage - 1);
        }
        // 下一頁
        else if (rawSlot == 51) {
            currentPage = Math.min(totalPages, currentPage + 1);

        }
        // 返回
        else if (rawSlot == 49) {
            StatMenu.restartStatMenu(plugin, player);
            setMetadata(plugin, player, "MENU");
            player.setMetadata("currentPage", new FixedMetadataValue(plugin, 1));
            return;
        }
        // 點擊其他元素
        else {
            return;
        }

        switch (viewType) {
            case MINED:
                StatMined.restartDigMenu(plugin, player, currentPage);
                setMetadata(plugin, player, "MINED");
                break;
            case KILLED:
                StatKilled.restartKillEntityMenu(plugin, player, currentPage);
                setMetadata(plugin, player, "KILLED");
                break;
        }
        player.setMetadata("currentPage", new FixedMetadataValue(plugin, currentPage));
    }

    public static void setMetadata(JavaPlugin plugin, Player player, String name) {
        player.setMetadata("view", new FixedMetadataValue(plugin, name));
        player.setMetadata("StatInventory", new FixedMetadataValue(plugin, true));
    }

    public static int calculateTotalPages(JavaPlugin plugin) {
        Set<Material> filterBlocks = FilterUtils.filterBlcoks(plugin); // 過濾
        long filteredBlockCount = Arrays.stream(Material.values())
                .filter(material -> material.isBlock() && !filterBlocks.contains(material))
                .count();
        return  (int) Math.ceil(filteredBlockCount / 45.0);
    }

    public static int calculateTotalEntityTypePages(JavaPlugin plugin) {
        Set<EntityType> filterEntities = FilterUtils.filterEntities(plugin); // 获取过滤的实体类型
        long filteredEntityCount = Arrays.stream(EntityType.values())
                .filter(entityType -> entityType.isSpawnable() && entityType.isAlive() && !filterEntities.contains(entityType))
                .count(); // 计算未被过滤且可生成生怪蛋的实体数量

        return (int) Math.ceil(filteredEntityCount / 45.0); // 假设每页显示45个项目
    }
}
