package ru.soknight.lib.component.replacement;

import org.bukkit.ChatColor;

import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Entity;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.md_5.bungee.api.chat.hover.content.Text;
import ru.soknight.lib.component.injection.TextComponentInjector;

/**
 * You want to create a new instance?
 * <br>
 * Then you should use
 */
@Getter
public class TextComponentReplacement {

    private final String placeholder;
    private final TextComponent value;

    TextComponentReplacement(String placeholder, TextComponent value) {
        this.placeholder = placeholder;
        this.value = value;
    }
    
    public TextComponent injectIn(String text) {
        return TextComponentInjector.inject(text, placeholder, value);
    }
    
    public TextComponentReplacement onClick(ClickEvent.Action action, String value) {
        this.value.setClickEvent(new ClickEvent(action, value));
        return this;
    }
    
    public TextComponentReplacement onClickChangePage(int pageNumber) {
        return onClick(ClickEvent.Action.CHANGE_PAGE, String.valueOf(pageNumber));
    }
    
    public TextComponentReplacement onClickOpenUrl(String url) {
        return onClick(ClickEvent.Action.OPEN_URL, url);
    }
    
    public TextComponentReplacement onClickRunCommand(String command) {
        return onClick(ClickEvent.Action.RUN_COMMAND, command);
    }
    
    public TextComponentReplacement onClickSuggestCommand(String command) {
        return onClick(ClickEvent.Action.SUGGEST_COMMAND, command);
    }
    
    public TextComponentReplacement onHover(HoverEvent.Action action, Content... contents) {
        this.value.setHoverEvent(new HoverEvent(action, contents));
        return this;
    }
    
    public TextComponentReplacement onHoverShowEntity(String type, String id, String name) {
        return onHoverShowEntity(type, id, new TextComponent(colorize(name)));
    }
    
    public TextComponentReplacement onHoverShowEntity(String type, String id, BaseComponent name) {
        return onHover(HoverEvent.Action.SHOW_ENTITY, new Entity(type, id, name));
    }
    
    public TextComponentReplacement onHoverShowItem(String id, int count, ItemTag tag) {
        return onHover(HoverEvent.Action.SHOW_ITEM, new Item(id, count, tag));
    }
    
    public TextComponentReplacement onHoverShowText(String text) {
        return onHoverShowText(TextComponent.fromLegacyText(colorize(text)));
    }
    
    public TextComponentReplacement onHoverShowText(BaseComponent[] text) {
        return onHover(HoverEvent.Action.SHOW_TEXT, new Text(text));
    }
    
    private static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    
}
