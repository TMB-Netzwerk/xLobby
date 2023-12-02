package de.xenodev.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.ArrayList;

public class DoubleJumpEvent implements Listener {

    private ArrayList<Player> cooldownArray = new ArrayList<>();

    @EventHandler
    public void handleDoubleJumpJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setAllowFlight(true);
        if (cooldownArray.contains(player)) {
            cooldownArray.remove(player);
        }
    }

    @EventHandler
    public void handleDoubleJumpToggle(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode().equals(GameMode.ADVENTURE)) {
            event.setCancelled(true);
            if (cooldownArray.contains(player)) return;
            player.setVelocity(player.getLocation().getDirection().setY(1));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 100, 100);
            cooldownArray.add(player);
            player.setAllowFlight(false);
        } else {
            cooldownArray.add(player);
        }
    }

    @EventHandler
    public void handleDoubleJumpMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode().equals(GameMode.ADVENTURE)) {
            if (!cooldownArray.contains(player)) return;
            if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isAir()) return;
            cooldownArray.remove(player);
            player.setAllowFlight(true);
        } else {
            if (!cooldownArray.contains(player)) cooldownArray.add(player);
        }
    }

    @EventHandler
    public void handleDoubleJumpGamemode(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode().equals(GameMode.ADVENTURE)) {
            if (!cooldownArray.contains(player)) return;
            cooldownArray.remove(player);
            player.setAllowFlight(true);
        } else {
            cooldownArray.add(player);
        }
    }
}
