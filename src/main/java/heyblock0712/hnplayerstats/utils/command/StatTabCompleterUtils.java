package heyblock0712.hnplayerstats.utils.command;

import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class StatTabCompleterUtils {
    public static @Nullable List<String> statSuggestions(CommandSender commandSender) {
        List<String> tabSuggestions = new ArrayList<>();
        tabSuggestions.add("menu");

        if (commandSender.hasPermission("hnplayerstats.admin")) {
            tabSuggestions.add("reload");
        }
        return tabSuggestions;
    }
}
