package de.xenodev.events.main;

import de.xenodev.utils.ItemBuilder;
import de.xenodev.utils.LocationBuilder;
import eu.cloudnetservice.driver.inject.InjectionLayer;
import eu.cloudnetservice.driver.permission.PermissionGroup;
import eu.cloudnetservice.driver.provider.CloudServiceProvider;
import eu.cloudnetservice.modules.bridge.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.awt.print.Book;

public class NavigatorEvent implements Listener {

    private Integer navigatorID;

    @EventHandler
    public void handleNavigatorItemInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if(event.getItem() == null) return;
        if(event.getItem().getType().equals(Material.COMPASS)){
            if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Navigator §7«")){
                Inventory navigatorInventory = Bukkit.createInventory(player, 9*5, "§7» §6Navigator §7«");

                for(int i = 0; i < 45; i++){
                    navigatorInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                }

                navigatorInventory.setItem(11, new ItemBuilder(Material.SPYGLASS).setName("§7» §5TMB Craft 6 §7«").build());
                navigatorInventory.setItem(13, new ItemBuilder(Material.TURTLE_EGG).setName("§7» §6Spawn §7«").setLore("§oNineJ bedankt sich fürs Turtle Egg").build());
                navigatorInventory.setItem(15, new ItemBuilder(Material.HORN_CORAL).setName("§7» §2Bau-Server §7«").build());

                navigatorInventory.setItem(28, new ItemBuilder(Material.RED_BED).setName("§7» §cBed§fWars §7«").build());
                navigatorInventory.setItem(30, new ItemBuilder(Material.BARRIER).setName("§7» §cServer 2 §7«").build());
                navigatorInventory.setItem(32, new ItemBuilder(Material.BARRIER).setName("§7» §cServer 3 §7«").build());
                navigatorInventory.setItem(34, new ItemBuilder(Material.BARRIER).setName("§7» §cServer 4 §7«").build());

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1f);

                player.openInventory(navigatorInventory);
            }
        }
    }

    @EventHandler
    public void handleNavigatorInventoryInteract(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §6Navigator §7«")){
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)) return;
            if(event.getCurrentItem().getType().equals(Material.BARRIER)){
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
            }else if(event.getCurrentItem().getType().equals(Material.TURTLE_EGG)){
                player.teleport(LocationBuilder.getLocation("Spawn"));
                player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 100, 1f);
            }else if(event.getCurrentItem().getType().equals(Material.SPYGLASS)){
                player.teleport(LocationBuilder.getLocation("TMBCraft"));
                player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 100, 1f);
            }else if(event.getCurrentItem().getType().equals(Material.HORN_CORAL)){
                player.teleport(LocationBuilder.getLocation("Bauserver"));
                player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 100, 1f);
            }else if(event.getCurrentItem().getType().equals(Material.RED_BED)){
                player.teleport(LocationBuilder.getLocation("Bedwars"));
                player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 100, 1f);
            }
        }
    }



}
