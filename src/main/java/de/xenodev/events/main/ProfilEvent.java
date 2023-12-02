package de.xenodev.events.main;

import de.xenodev.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class ProfilEvent implements Listener {

    @EventHandler
    public void handleProfilItemInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if(event.getItem() == null) return;
        if(event.getItem().getType().equals(Material.PLAYER_HEAD)) {
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §aProfil §7«")) {
                Inventory profilInventory = Bukkit.createInventory(player, 9*5, "§7» §aProfil §7«");

                for (int i = 0; i < 45; i++) {
                    profilInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                }

                profilInventory.setItem(31, new ItemBuilder(Material.GOLDEN_BOOTS).setName("§7» §6Spuren §7«").build());
                profilInventory.setItem(28, new ItemBuilder(Material.CHEST).setName("§7» §6Gadgets §7«").build());
                profilInventory.setItem(34, new ItemBuilder(Material.ENDER_CHEST).setName("§7» §6Shop §7«").build());
                profilInventory.setItem(13, new ItemBuilder(Material.REDSTONE).setName("§7» §6Settings §7«").build());

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1f);
                player.openInventory(profilInventory);
            }
        }
    }

}
