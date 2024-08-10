package net.neocosmic.neovoidless.listeners;

import net.neocosmic.neovoidless.info.Information;
import net.neocosmic.neovoidless.teleport.TeleportType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GeneralListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (!Information.isValid(player.getWorld())) {
            return;
        }

        if (Information.Y_TELEPORT_TYPE == TeleportType.GLOBAL && player.getLocation().getY() > Information.Y_GLOBAL_LOCATION) {
            return;
        }

        if (!Information.SPECIFIC_Y.containsKey(player.getWorld())) {
            throw new IllegalStateException("voidless is enabled, but no specific y found for this world!");
        }

        if (Information.Y_TELEPORT_TYPE == TeleportType.SPECIFIC && player.getLocation().getY() > Information.SPECIFIC_Y.get(player.getWorld())) {
            return;
        }

        Location location = Information.getLocation(player.getLocation().getWorld());

        if (location == null) {
            throw new IllegalStateException("voidless is enabled, but no location found for this world!");
        }

        player.teleport(location);

        if (Information.MESSAGE_ENABLED) {
            player.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', Information.MESSAGE.replace("%player%", player.getName()))
            );
        }
    }
}
