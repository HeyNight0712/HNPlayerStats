package heyblock0712.hnplayerstats.command;

import heyblock0712.hnplayerstats.utils.TabCompleterUtils;
import heyblock0712.hnplayerstats.utils.command.StatTabCompleterUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class StatTabCompleter implements org.bukkit.command.TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) { return null;}
        if (strings.length == 1) { return TabCompleterUtils.inputSuggestions(StatTabCompleterUtils.statSuggestions(commandSender), strings[0]);}


        return null;
    }
}
