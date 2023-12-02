package de.xenodev.events.gadget;

import de.xenodev.utils.CooldownBuilder;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class EnderperleEvent implements Listener {

    private ArrayList<Player> cooldownArray = new ArrayList<>();
    private HashMap<Player, Integer> cooldownInt = new HashMap<>();
    private BukkitTask cooldownID;

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Endermite) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (player.getItemInHand().getType().equals(Material.ENDER_PEARL) && !player.isSneaking()) {
                event.setCancelled(true);

                if (!CooldownBuilder.enderperlCooldown.containsKey(player)) {
                    CooldownBuilder.enderperlCooldown.put(player, 5);
                    EnderPearl enderPearl = player.launchProjectile(EnderPearl.class);
                    enderPearl.setPassenger(player);
                }else{
                    player.sendMessage(xLobby.getPrefix() + "§7Die §9Enderperle §7hat noch §c" + CooldownBuilder.enderperlCooldown.get(player) + "sec §7Cooldown!");
                }
            }
        }
    }

    @EventHandler
    public void onVehicleLeave(EntityDismountEvent event){
        Entity entity = event.getDismounted();

        if(entity instanceof EnderPearl){
            entity.remove();
        }

    }
}
