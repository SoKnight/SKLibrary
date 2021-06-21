package ru.soknight.lib.argument.suggestion;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The internal utility implementation of the {@link SuggestionProvider}
 * @deprecated Actually needs further consideration...
 * @since 1.12.0
 */
@Deprecated
@AllArgsConstructor(access = AccessLevel.PACKAGE)
final class FilteringSuggestionsProvider implements SuggestionProvider {

    private final ProviderSupplier<Stream<String>> dataProvider;

    @Override
    public List<String> provideSuggestions(CommandSender sender, String input) {
        Stream<String> stream = dataProvider.supply(sender, input);
        if(stream == null)
            return null;

        return stream
                .filter(n -> n.toLowerCase().startsWith(input))
                .collect(Collectors.toList());
    }

}
