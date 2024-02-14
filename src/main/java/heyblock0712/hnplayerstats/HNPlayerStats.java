package heyblock0712.hnplayerstats;

import heyblock0712.hnplayerstats.command.StatCommand;
import heyblock0712.hnplayerstats.command.StatTabCompleter;
import heyblock0712.hnplayerstats.core.ConfigCore;
import heyblock0712.hnplayerstats.core.LanguageCore;
import heyblock0712.hnplayerstats.listener.InventoryClickListener;
import heyblock0712.hnplayerstats.listener.InventoryCloseListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class HNPlayerStats extends JavaPlugin {
    private static JavaPlugin instance;
    private ConfigCore configCore;
    private LanguageCore languageCore;


    @Override
    public void onEnable() {
        // init
        instance = this;
        saveDefaultConfig();
        this.configCore = new ConfigCore(this);
        this.languageCore = new LanguageCore(this);
        registerCommands();

        // Listener
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(this), this);

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void registerCommands() {

        PluginCommand statistic = this.getCommand("statistic");
        if (statistic != null) {
            statistic.setExecutor(new StatCommand(this));
            statistic.setTabCompleter(new StatTabCompleter());
        }
    }

    public static HNPlayerStats getInstance() {return (HNPlayerStats) instance;}

    public ConfigCore getConfigCore() {
        return configCore;
    }

    public LanguageCore getLanguageCore() {
        return languageCore;
    }
}
