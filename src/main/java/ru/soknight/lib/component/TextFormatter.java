package ru.soknight.lib.component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.md_5.bungee.api.chat.TextComponent;
import ru.soknight.lib.component.injection.TextComponentInjector;
import ru.soknight.lib.component.replacement.Replacements;
import ru.soknight.lib.component.replacement.TextComponentReplacement;
import ru.soknight.lib.component.replacement.TextReplacement;

public class TextFormatter {

    public static String format(String message, Object... replacements) {
        TextReplacement[] array = Replacements.text(replacements);
        if(array == null || array.length == 0) return message;
        
        for(TextReplacement replacement : array)
            message = replacement.replaceIn(message);
        
        return message;
    }
    
    public static List<String> format(List<String> list, Object... replaces) {
        if(list == null || replaces == null || list.isEmpty()) return list;
        
        return list.stream()
                .map(s -> format(s, replaces))
                .collect(Collectors.toList());
    }
    
    public static TextComponent injectAll(String message, Object... replacements) {
        TextComponentReplacement[] array = Replacements.components(replacements);
        if(array == null || array.length == 0) return new TextComponent("");
        
        return TextComponentInjector.inject(message, array);
    }
    
    public static List<TextComponent> injectAll(List<String> list, Object... replacements) {
        if(list == null || replacements == null || list.isEmpty()) return Collections.emptyList();
        
        return list.stream()
                .map(s -> injectAll(s, replacements))
                .collect(Collectors.toList());
    }
    
}
