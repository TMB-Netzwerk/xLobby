package de.xenodev.events;

import de.xenodev.utils.LocationBuilder;
import de.xenodev.xLobby;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class DefaultChangesEvent implements Listener {

    @EventHandler
    public void handleBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("tmb.function.lobbybuild") && player.getGameMode() == GameMode.CREATIVE){
            event.setCancelled(false);
        }else{
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (player.hasPermission("tmb.function.lobbybuild") && player.getGameMode() == GameMode.CREATIVE){
            event.setCancelled(false);
        }else{
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleDamageByEntity(EntityDamageByEntityEvent event){
        if(xLobby.xLobbyModule_Games) {
            if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
                event.setCancelled(true);
            }
        }
        if(event.getDamager() instanceof  Player && event.getEntity() instanceof Painting){
            event.setCancelled(true);
        }
        if(event.getDamager() instanceof  Player && event.getEntity() instanceof Minecart){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleEntityDamage(EntityDamageEvent event){
        if(xLobby.xLobbyModule_Games) {
            if (event.getEntity() instanceof ItemFrame || event.getEntity() instanceof ArmorStand) {
                event.setCancelled(true);
            }
        }else{
            if (event.getEntity() instanceof Player || event.getEntity() instanceof ItemFrame || event.getEntity() instanceof ArmorStand) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handleFoodLevel(FoodLevelChangeEvent event){
        Player player = (Player) event.getEntity();
        player.setFoodLevel(20);
        player.setSaturation(20);
        event.setCancelled(true);
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (player.hasPermission("tmb.function.lobbybuild") && player.getGameMode() == GameMode.CREATIVE){
            event.setCancelled(false);
        }else{
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleDropItem(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        if (player.hasPermission("tmb.function.lobbybuild") && player.getGameMode() == GameMode.CREATIVE){
            event.setCancelled(false);
        }else{
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handlePickItem(PlayerPickupItemEvent event){
        Player player = event.getPlayer();
        if (player.hasPermission("tmb.function.lobbybuild") && player.getGameMode() == GameMode.CREATIVE){
            event.setCancelled(false);
        }else{
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleSwapHand(PlayerSwapHandItemsEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void handleWeatherChange(WeatherChangeEvent event){
        World world = event.getWorld();
        world.setStorm(false);
        world.setThundering(false);
        event.setCancelled(true);
    }

    @EventHandler
    public void handleXPChange(PlayerExpChangeEvent event){
        Player player = event.getPlayer();
        event.setAmount(0);
        player.setLevel(0);
        player.setExp(0);
    }

    @EventHandler
    public void handleInventoryClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (player.hasPermission("tmb.function.lobbybuild") && player.getGameMode() == GameMode.CREATIVE){
            event.setCancelled(false);
        }else{
            if(player.getInventory().getItemInMainHand().getType().equals(Material.FISHING_ROD)){
                event.setCancelled(false);
            }else{
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void handleExplotions(EntityExplodeEvent event){
        if(event.getEntityType().equals(EntityType.TNT_MINECART)) {
            event.blockList().clear();
            event.setCancelled(true);
        }
    }

}
