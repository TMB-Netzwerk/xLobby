package de.xenodev.events.main;

import de.xenodev.mysql.BuyAPI;
import de.xenodev.mysql.SettingAPI;
import de.xenodev.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

public class GadgetEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase("§7» §aProfil §7«")) {
            if(event.getCurrentItem().getType().equals(Material.CHEST)){
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Gadgets §7«")){
                    event.setCancelled(true);
                    player.closeInventory();
                    Inventory inv = Bukkit.createInventory(null, 9*4, "§7» §6Gadgets §7«");

                    for(int i = 18; i < 27; i++){
                        inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                    }

                    if(BuyAPI.getBuy(player.getUniqueId(), "enderperl").equals("true")){
                        inv.addItem(new ItemBuilder(Material.ENDER_PEARL).setName("§7» §6Enderperle §7«").build());
                    }
                    if(BuyAPI.getBuy(player.getUniqueId(), "enterhaken").equals("true")){
                        inv.addItem(new ItemBuilder(Material.FISHING_ROD).setName("§7» §6Enterhaken §7«").build());
                    }
                    if(BuyAPI.getBuy(player.getUniqueId(), "flugstab").equals("true")){
                        inv.addItem(new ItemBuilder(Material.BLAZE_ROD).setName("§7» §6Flugstab §7«").build());
                    }
                    if(BuyAPI.getBuy(player.getUniqueId(), "eggbomb").equals("true")){
                        inv.addItem(new ItemBuilder(Material.EGG).setName("§7» §6Eggbomb §7«").setLore("§oGadget von Ostern 2021").build());
                    }
                    if(BuyAPI.getBuy(player.getUniqueId(), "switchbow").equals("true")){
                        inv.addItem(new ItemBuilder(Material.BOW).setName("§7» §6Switch Bow §7«").setLore("§oGadget von Weihnachten 2021").build());
                    }

                    inv.setItem(34, new ItemBuilder(Material.BARRIER).setName("§7» §cGadget löschen §7«").build());
                    inv.setItem(35, new ItemBuilder(Material.ARROW).setName("§7» §6Zurück §7«").build());

                    player.openInventory(inv);
                    player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
                }
            }
        }
    }

    @EventHandler
    public void onClickKleider(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §6Gadgets §7«")){
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE) || event.getCurrentItem().getType().equals(Material.AIR) || event.getCurrentItem().getType().equals(Material.NETHER_STAR)) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §cGadget löschen §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "enterhaken", "false");
                SettingAPI.setSetting(player.getUniqueId(), "flugstab", "false");
                SettingAPI.setSetting(player.getUniqueId(), "eggbomb", "false");
                SettingAPI.setSetting(player.getUniqueId(), "enderperl", "false");
                SettingAPI.setSetting(player.getUniqueId(), "switchbow", "false");
                player.getInventory().setItem(0, new ItemBuilder(Material.BARRIER).setName("§7» §4Kein Gadget ausgewählt §7«").build());
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Zurück §7«")) {
                player.closeInventory();
                Inventory profilInventory = Bukkit.createInventory(player, 9*4, "§7» §aProfil §7«");

                for (int i = 0; i < 35; i++) {
                    profilInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                }

                profilInventory.setItem(10, new ItemBuilder(Material.GOLDEN_BOOTS).setFlag(ItemFlag.HIDE_ATTRIBUTES).setName("§7» §6Spuren §7«").build());
                profilInventory.setItem(13, new ItemBuilder(Material.CHEST).setName("§7» §6Gadgets §7«").build());
                profilInventory.setItem(16, new ItemBuilder(Material.ENDER_CHEST).setName("§7» §6Shop §7«").build());
                profilInventory.setItem(35, new ItemBuilder(Material.REDSTONE).setEnchantment(Enchantment.CHANNELING, 0).setFlag(ItemFlag.HIDE_ENCHANTS).setName("§7» §6Settings §7«").build());

                player.openInventory(profilInventory);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 1f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Enterhaken §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "enterhaken", "true");
                SettingAPI.setSetting(player.getUniqueId(), "flugstab", "false");
                SettingAPI.setSetting(player.getUniqueId(), "eggbomb", "false");
                SettingAPI.setSetting(player.getUniqueId(), "enderperl", "false");
                SettingAPI.setSetting(player.getUniqueId(), "switchbow", "false");
                player.getInventory().setItem(0, new ItemBuilder(Material.FISHING_ROD).setName("§7» §9Enterhaken §7«").setUnbreakable().build());
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Flugstab §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "enterhaken", "false");
                SettingAPI.setSetting(player.getUniqueId(), "flugstab", "true");
                SettingAPI.setSetting(player.getUniqueId(), "eggbomb", "false");
                SettingAPI.setSetting(player.getUniqueId(), "enderperl", "false");
                SettingAPI.setSetting(player.getUniqueId(), "switchbow", "false");
                player.getInventory().setItem(0, new ItemBuilder(Material.BLAZE_ROD).setName("§7» §9Flugstab §7«").build());
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Eggbomb §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "enterhaken", "false");
                SettingAPI.setSetting(player.getUniqueId(), "flugstab", "false");
                SettingAPI.setSetting(player.getUniqueId(), "eggbomb", "true");
                SettingAPI.setSetting(player.getUniqueId(), "enderperl", "false");
                SettingAPI.setSetting(player.getUniqueId(), "switchbow", "false");
                player.getInventory().setItem(0, new ItemBuilder(Material.EGG).setName("§7» §9Eggbomb §7«").build());
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Enderperle §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "enterhaken", "false");
                SettingAPI.setSetting(player.getUniqueId(), "flugstab", "false");
                SettingAPI.setSetting(player.getUniqueId(), "eggbomb", "false");
                SettingAPI.setSetting(player.getUniqueId(), "enderperl", "true");
                SettingAPI.setSetting(player.getUniqueId(), "switchbow", "false");
                player.getInventory().setItem(0, new ItemBuilder(Material.ENDER_PEARL).setName("§7» §9Enderperle §7«").build());
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Switch Bow §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "enterhaken", "false");
                SettingAPI.setSetting(player.getUniqueId(), "flugstab", "false");
                SettingAPI.setSetting(player.getUniqueId(), "eggbomb", "false");
                SettingAPI.setSetting(player.getUniqueId(), "enderperl", "false");
                SettingAPI.setSetting(player.getUniqueId(), "switchbow", "true");
                player.getInventory().setItem(0, new ItemBuilder(Material.BOW).setName("§7» §9Switch Bow §7«").build());
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }
        }
    }
}
