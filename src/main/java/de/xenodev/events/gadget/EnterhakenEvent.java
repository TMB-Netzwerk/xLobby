package de.xenodev.events.gadget;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

public class EnterhakenEvent implements Listener {

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        FishHook fishHook = event.getHook();

        if((event.getState().equals(PlayerFishEvent.State.IN_GROUND)
                || (event.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY)
                || (event.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT)))
                && (!fishHook.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isAir()))){
            Location lc = player.getLocation();
            Location to = fishHook.getLocation();

            double g = -0.08;
            double d = to.distance(lc);
            double t = d;
            double v_x = (1.0 + 0.07 * t) * (to.getX() - lc.getX()) / t;
            double v_y = (1.0 + 0.03 * t) * (to.getY() - lc.getY()) / t - 0.5 * g * t;
            double v_z = (1.0 + 0.07 * t) * (to.getZ() - lc.getZ()) / t;
            Vector velocity = player.getVelocity();
            velocity.setX(v_x);
            velocity.setY(v_y);
            velocity.setZ(v_z);
            velocity.multiply(1);
            player.setVelocity(velocity);
        }
    }

}
