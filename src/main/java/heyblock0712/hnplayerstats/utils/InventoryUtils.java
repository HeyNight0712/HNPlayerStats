package heyblock0712.hnplayerstats.utils;

import heyblock0712.hnplayerstats.HNPlayerStats;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
    private Inventory inventory;
    public InventoryUtils(Inventory inventory) {
        this.inventory = inventory;
    }

    public static InventoryUtils setInventory(Inventory inventory) {return new InventoryUtils(inventory);}

    public InventoryUtils setDecorate(int[] slots) {
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
        ItemUtils.setDisplayName(item, " ");
        for (int slot : slots) {
            inventory.setItem(slot, item);
        }
        return this;
    }

    public InventoryUtils setClose (int slot) {
        // 關閉
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemUtils.setDisplayName(item, HNPlayerStats.getInstance().getLanguageCore().getString("gui.close"));
        inventory.setItem(slot, item);
        return this;
    }

    public InventoryUtils setBack (int slot) {
        // 返回
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemUtils.setDisplayName(item, HNPlayerStats.getInstance().getLanguageCore().getString("gui.back"));
        inventory.setItem(slot, item);
        return this;
    }

    public InventoryUtils setPrevPage (int slot) {
        // 上一頁
        ItemStack item = new ItemStack(Material.ARROW);
        ItemUtils.setDisplayName(item, HNPlayerStats.getInstance().getLanguageCore().getString("gui.page.prev"));
        inventory.setItem(slot, item);
        return this;
    }

    public InventoryUtils setNextPage (int slot) {
        // 下一頁
        ItemStack item = new ItemStack(Material.ARROW);
        ItemUtils.setDisplayName(item, HNPlayerStats.getInstance().getLanguageCore().getString("gui.page.next"));
        inventory.setItem(slot, item);
        return this;
    }

    public InventoryUtils setItem (String materialPath, String slotPath, String langPath) {
        int slot = HNPlayerStats.getInstance().getConfigCore().getInt(slotPath);
        String materialName = HNPlayerStats.getInstance().getConfigCore().getString(materialPath); // 獲取物品設置
        Material material = Material.valueOf(materialName.toUpperCase()); // 獲取材質
        String itemName = HNPlayerStats.getInstance().getLanguageCore().getString(langPath); // 獲取 語言文本
        ItemStack item = new ItemStack(material);
        ItemUtils.setDisplayName(item, itemName);
        inventory.setItem(slot, item);
        return this;
    }

    public InventoryUtils setItem (String materialPath, String slotPath, String langPath, String lore) {
        int slot = HNPlayerStats.getInstance().getConfigCore().getInt(slotPath);
        String materialName = HNPlayerStats.getInstance().getConfigCore().getString(materialPath); // 獲取物品設置
        Material material = Material.valueOf(materialName.toUpperCase()); // 獲取材質
        String itemName = HNPlayerStats.getInstance().getLanguageCore().getString(langPath); // 獲取 語言文本
        ItemStack item = new ItemStack(material);
        ItemUtils.setDisplayName(item, itemName);
        ItemUtils.setLore(item, lore);
        inventory.setItem(slot, item);
        return this;
    }
}
