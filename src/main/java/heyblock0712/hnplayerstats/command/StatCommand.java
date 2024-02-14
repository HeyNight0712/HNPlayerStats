package heyblock0712.hnplayerstats.command;

import heyblock0712.hnplayerstats.HNPlayerStats;
import heyblock0712.hnplayerstats.composition.inventory.StatMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class StatCommand implements CommandExecutor {
    private JavaPlugin plugin;

    public StatCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 1) {
            commandSender.sendMessage(HNPlayerStats.getInstance().getLanguageCore().getString("command.info"));
            return true;
        } else if (strings.length == 1) {
            return length1(commandSender, strings);
        }
        return false;
    }

    public boolean length1(CommandSender commandSender, String[] strings) {
        Player player = (Player) commandSender;
        if (strings[0].equalsIgnoreCase("info")) {
            commandSender.sendMessage(HNPlayerStats.getInstance().getLanguageCore().getString("command.info"));
        } else if ((strings[0].equalsIgnoreCase("menu"))) {
            StatMenu.restartStatMenu(plugin, player);
        } else if ((strings[0].equalsIgnoreCase("reload"))) {
            HNPlayerStats.getInstance().getConfigCore().reloadConfig();
            commandSender.sendMessage(HNPlayerStats.getInstance().getLanguageCore().getString("command.reload"));
        }
        return true;
    }
}
