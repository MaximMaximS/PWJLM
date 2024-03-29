package io.github.maximmaxims.pwjlm.listeners;

import io.github.maximmaxims.pwjlm.PWJLM;
import io.github.maximmaxims.pwjlm.classes.WorldGroup;
import io.github.maximmaxims.pwjlm.utils.ConfigUtil;
import io.github.maximmaxims.pwjlm.utils.MessageSenderUtil;
import io.github.maximmaxims.pwjlm.utils.PluginUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class PlayerQuitListener implements Listener {
    private final PWJLM plugin;

    public PlayerQuitListener(@NotNull PWJLM plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuitEvent(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("removeDefaultLeave", false)) {
            event.setQuitMessage(null);
        }
        if (plugin.getConfig().getBoolean("ignoreVanished") && PluginUtil.isVanished(player)) return;
        if (plugin.getConfig().getBoolean("ignoreNoPermission") && !player.hasPermission("pwjlm.notify")) return;
        WorldGroup group = WorldGroup.getInstance(plugin, player.getWorld());
        if (group != null && group.getUseServer(false)) {
            String message = group.getServerMessage(false);
            MessageSenderUtil.sendMessage(new HashSet<>(group.getWorlds()), message, player, ConfigUtil.usePapi(plugin), true);
        }
    }
}