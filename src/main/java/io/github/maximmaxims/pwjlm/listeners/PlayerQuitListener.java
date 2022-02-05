package io.github.maximmaxims.pwjlm.listeners;

import io.github.maximmaxims.pwjlm.PWJLM;
import io.github.maximmaxims.pwjlm.classes.WorldGroup;
import io.github.maximmaxims.pwjlm.utils.MessageSenderUtil;
import io.github.maximmaxims.pwjlm.utils.PluginUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerQuitListener implements Listener {
    private final PWJLM plugin;

    public PlayerQuitListener(@NotNull PWJLM plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuitEvent(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        boolean checkVanish = plugin.getConfig().getBoolean("ignoreVanished");
        if (checkVanish && PluginUtil.isVanished(player)) return;
        WorldGroup group = WorldGroup.getInstance(plugin, player.getWorld());
        if (group != null && group.getUseServer(false)) {
            if (group.getRemoveDefault(true)) {
                //noinspection deprecation
                event.setQuitMessage(null);
            }
            String message = group.getServerMessage(false);
            message = message.replace("{PLAYER}", player.getName()); // Add player name
            MessageSenderUtil.sendMessage(group.getWorlds(), message, PluginUtil.usePapi(plugin));
        }
    }
}