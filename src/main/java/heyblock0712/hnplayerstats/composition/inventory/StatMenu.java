package heyblock0712.hnplayerstats.composition.inventory;

import heyblock0712.hnplayerstats.HNPlayerStats;
import heyblock0712.hnplayerstats.utils.InventoryUtils;
import heyblock0712.hnplayerstats.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class StatMenu {

    public static void restartStatMenu(JavaPlugin plugin, Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, "Stat 統計");
        player.setMetadata("view", new FixedMetadataValue(plugin, "MENU"));
        player.setMetadata("StatInventory", new FixedMetadataValue(plugin, true));

        // 裝飾
        InventoryUtils.setInventory(inventory)
                .setDecorate(new int[] {45, 46, 47, 48, 50, 51, 52, 53})
                .setClose(49)
                .setItem("gui.menu.mined.material", "gui.menu.mined.slot", "gui.stat.mined")
                .setItem("gui.menu.killed.material", "gui.menu.killed.slot", "gui.stat.killed");

        player.openInventory(inventory);
    }
}
