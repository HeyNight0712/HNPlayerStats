package heyblock0712.hnplayerstats.listener;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryCloseListener implements Listener {
    private final JavaPlugin plugin;

    public InventoryCloseListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (player.hasMetadata("view")) {
            player.removeMetadata("view", plugin);
        }

        if (player.hasMetadata("StatInventory")) {
            player.removeMetadata("StatInventory", plugin);
        }
    }


}
