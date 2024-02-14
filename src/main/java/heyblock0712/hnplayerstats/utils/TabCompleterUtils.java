package heyblock0712.hnplayerstats.utils;

import java.util.ArrayList;
import java.util.List;

public class TabCompleterUtils {
    public static List<String> inputSuggestions(List<String> suggestions, String input) {
        List<String> inputSuggestions = new ArrayList<>();
        if (input != null && !input.isEmpty()) {
            String inputLowerCase = input.toLowerCase();
            for (String suggestion : suggestions) {
                if (suggestion.toLowerCase().startsWith(inputLowerCase)) {
                    inputSuggestions.add(suggestion);
                }
            }
        } else {
            // 為空則返回 全部
            inputSuggestions = new ArrayList<>(suggestions);
        }
        return inputSuggestions;
    }
}
