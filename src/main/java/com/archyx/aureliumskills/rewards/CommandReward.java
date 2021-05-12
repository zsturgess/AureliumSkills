package com.archyx.aureliumskills.rewards;

import com.archyx.aureliumskills.AureliumSkills;
import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.util.item.LoreUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Locale;

public class CommandReward extends MessageCustomizableReward {

    private final CommandExecutor executor;
    private final String command;

    public CommandReward(AureliumSkills plugin, String menuMessage, String chatMessage, CommandExecutor executor, String command) {
        super(plugin, menuMessage, chatMessage);
        this.executor = executor;
        this.command = command;
    }

    @Override
    public void giveReward(Player player, Skill skill, int level) {
        String command = this.command;
        // Apply placeholders
        command = LoreUtil.replace(command, "{player}", player.getName(),
                "{skill}", skill.toString().toLowerCase(Locale.ROOT),
                "{level}", String.valueOf(level));
        if (plugin.isPlaceholderAPIEnabled()) {
            command = PlaceholderAPI.setPlaceholders(player, command);
        }

        // Executes the commands
        if (executor == CommandExecutor.CONSOLE) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        } else {
            player.performCommand(command);
        }
    }

    public enum CommandExecutor {
        CONSOLE,
        PLAYER
    }

}
