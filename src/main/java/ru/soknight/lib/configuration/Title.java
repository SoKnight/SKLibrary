package ru.soknight.lib.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
@AllArgsConstructor
public final class Title {

    private final String title;
    private final String subtitle;
    private final int fadeInTicks;
    private final int stayTicks;
    private final int fadeOutTicks;

    public void send(@NotNull CommandSender receiver) {
        if(receiver instanceof Player) {
            ((Player) receiver).sendTitle(title, subtitle, fadeInTicks, stayTicks, fadeOutTicks);
            return;
        }

        receiver.sendMessage(ChatColor.RED + "You could see this message if you were player, because it's a title message.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Title other = (Title) o;
        return fadeInTicks == other.fadeInTicks &&
                stayTicks == other.stayTicks &&
                fadeOutTicks == other.fadeOutTicks &&
                Objects.equals(title, other.title) &&
                Objects.equals(subtitle, other.subtitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, subtitle, fadeInTicks, stayTicks, fadeOutTicks);
    }

    @Override
    public @NotNull String toString() {
        return "Title{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", fadeInTicks=" + fadeInTicks +
                ", stayTicks=" + stayTicks +
                ", fadeOutTicks=" + fadeOutTicks +
                '}';
    }

}
