package heyblock0712.hnplayerstats.utils;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterUtils {
    public static Set<Material> filterBlcoks (JavaPlugin plugin) {
        List<String> blockNames = plugin.getConfig().getStringList("filter.blocks");
        Set<Material> filterBlocks = new HashSet<>();
        for (String name : blockNames) {
            Material material = Material.getMaterial(name);
            if (material != null) {
                filterBlocks.add(material);
            } else {
                plugin.getLogger().warning("Unknown block in filter.blocks config: " + name);
            }
        }
        return filterBlocks;
    }

    public static Set<EntityType> filterEntities(JavaPlugin plugin) {
        List<String> entityNames = plugin.getConfig().getStringList("filter.entities");
        Set<EntityType> filterEntities = new HashSet<>();
        for (String name : entityNames) {
            try {
                EntityType entityType = EntityType.valueOf(name.toUpperCase());
                filterEntities.add(entityType);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Unknown entity in filter.entities config: " + name);
            }
        }
        return filterEntities;
    }
}
