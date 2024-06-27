package de.xenodev.events.gadget.special;

import de.xenodev.mysql.SettingAPI;
import de.xenodev.utils.CooldownBuilder;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class EggBombEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getInventory().getItemInHand().getType().equals(Material.EGG)) {
                if (player.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §9Eggbomb §7«")) {
                    event.setCancelled(true);

                    if (!CooldownBuilder.eggbombCooldown.containsKey(player)) {
                        CooldownBuilder.eggbombCooldown.put(player, 3);
                        player.launchProjectile(Egg.class);
                        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1f, 1f);
                    }else{
                        player.sendMessage(xLobby.getPrefix() + "§7Die §9Eggbomb §7hat noch §c" + CooldownBuilder.eggbombCooldown.get(player) + "sec §7Cooldown!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onThrow(ProjectileHitEvent event){
        ArrayList<Player> arrayList = new ArrayList<>();
        if(event.getEntity().getShooter() instanceof Player){
            Player player = (Player) event.getEntity().getShooter();
            if(event.getEntity() instanceof Egg){
                Egg egg = (Egg) event.getEntity();
                for(Entity entity : egg.getNearbyEntities(5.0D, 7.0D, 5.0D)){
                    Player target = (Player) entity;
                    if (SettingAPI.getSetting(target.getUniqueId(), "Eggboost_other").equals("true")) {
                        target.playSound(egg.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
                        target.playEffect(egg.getLocation(), Effect.EXTINGUISH, 100);
                        Vector v = target.getVelocity();
                        v.setX(target.getLocation().getDirection().multiply(4).getX());
                        v.setY(2);
                        v.setZ(target.getLocation().getDirection().multiply(4).getZ());
                        target.setVelocity(v);
                    }
                    if (SettingAPI.getSetting(player.getUniqueId(), "Eggboost_self").equals("true")) {
                        target.playSound(egg.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
                        target.playEffect(egg.getLocation(), Effect.EXTINGUISH, 100);
                        Vector v = target.getVelocity();
                        v.setX(target.getLocation().getDirection().multiply(4).getX());
                        v.setY(2);
                        v.setZ(target.getLocation().getDirection().multiply(4).getZ());
                        target.setVelocity(v);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event){
        if(event.getEntity() instanceof Chicken){
            event.setCancelled(true);
        }
    }
}
