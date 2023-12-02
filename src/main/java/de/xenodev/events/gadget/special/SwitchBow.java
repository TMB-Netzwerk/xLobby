package de.xenodev.events.gadget.special;

import de.xenodev.utils.CooldownBuilder;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class SwitchBow implements Listener {

    private ArrayList<Player> cooldownArray = new ArrayList<>();
    private HashMap<Player, Integer> cooldownInt = new HashMap<>();
    private BukkitTask cooldownID;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(player.getInventory().getItemInHand().getType().equals(Material.BOW)) {
                if (player.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §9Switch Bow §7«")) {
                    event.setCancelled(true);

                    if (!CooldownBuilder.switchbowCooldown.containsKey(player)) {
                        CooldownBuilder.switchbowCooldown.put(player, 5);
                        Snowball snowball = player.launchProjectile(Snowball.class);
                        player.playSound(player.getLocation(), Sound.ITEM_CROSSBOW_SHOOT, 1f, 1f);
                    }else{
                        player.sendMessage(xLobby.getPrefix() + "§7Der §9Switchbow §7hat noch §c" + CooldownBuilder.switchbowCooldown.get(player) + "sec §7Cooldown!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event){
        if(event.getEntity().getShooter() instanceof Player && event.getHitEntity() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            Player target = (Player) event.getHitEntity();

            Location oldp = player.getLocation();
            Location oldt = target.getLocation();
            if (event.getEntity() instanceof Snowball) {
                player.teleport(oldt);
                target.teleport(oldp);
                player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1f, 1f);
                target.playSound(target.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1f, 1f);
                Entity snowball = event.getEntity();
                snowball.remove();
            }
        }
    }

    @EventHandler
    public void onLand(ProjectileHitEvent event){
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();
            snowball.remove();
        }
    }

}
