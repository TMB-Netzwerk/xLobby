package de.xenodev.events.main;

import de.xenodev.mysql.BuyAPI;
import de.xenodev.mysql.SettingAPI;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

public class TrailEvent implements Listener {

    @EventHandler
    public void handleTrails(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase("§7» §aProfil §7«")) {
            if(event.getCurrentItem().getType().equals(Material.GOLDEN_BOOTS)){
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Spuren §7«")){
                    event.setCancelled(true);
                    player.closeInventory();
                    Inventory inv = Bukkit.createInventory(null, 9*6, "§7» §6Spuren §7«");


                    for(int i = 36; i < 45; i++){
                        inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                    }

                    if(BuyAPI.getBuy(player.getUniqueId(), "hearttrail").equals("true")){
                        inv.addItem(new ItemBuilder(Material.LEATHER_BOOTS).setName("§7» §6Heart §7«").setColor(Color.RED).setFlag(ItemFlag.HIDE_ATTRIBUTES).build());
                    }
                    if(BuyAPI.getBuy(player.getUniqueId(), "notetrail").equals("true")){
                        inv.addItem(new ItemBuilder(Material.LEATHER_BOOTS).setName("§7» §6Note §7«").setColor(Color.LIME).setFlag(ItemFlag.HIDE_ATTRIBUTES).build());
                    }
                    if(BuyAPI.getBuy(player.getUniqueId(), "flametrail").equals("true")){
                        inv.addItem(new ItemBuilder(Material.LEATHER_BOOTS).setName("§7» §6Flame §7«").setColor(Color.ORANGE).setFlag(ItemFlag.HIDE_ATTRIBUTES).build());
                    }
                    if(BuyAPI.getBuy(player.getUniqueId(), "colortrail").equals("true")){
                        inv.addItem(new ItemBuilder(Material.LEATHER_BOOTS).setName("§7» §6Color §7«").setLore("§oTrail von Ostern 2022").setColor(Color.YELLOW).setFlag(ItemFlag.HIDE_ATTRIBUTES).build());
                    }
                    if(BuyAPI.getBuy(player.getUniqueId(), "ghosttrail").equals("true")){
                        inv.addItem(new ItemBuilder(Material.LEATHER_BOOTS).setName("§7» §6Ghost §7«").setLore("§oTrail von Halloween 2022").setColor(Color.RED).setFlag(ItemFlag.HIDE_ATTRIBUTES).build());
                    }

                    inv.setItem(52, new ItemBuilder(Material.BARRIER).setName("§7» §cSpur löschen §7«").build());
                    inv.setItem(53, new ItemBuilder(Material.ARROW).setName("§7» §6Zurück §7«").build());

                    player.openInventory(inv);
                    player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
                }
            }
        }else if(event.getView().getTitle().equalsIgnoreCase("§7» §6Spuren §7«")){
            event.setCancelled(true);
            if(event.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE) || event.getCurrentItem().getType().equals(Material.AIR) || event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §cSpur löschen §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "hearttrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "notetrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "flametrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "colortrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "ghosttrail", false);
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Zurück §7«")) {
                player.closeInventory();
                Inventory profilInventory = Bukkit.createInventory(player, 9*5, "§7» §aProfil §7«");

                for (int i = 0; i < 45; i++) {
                    profilInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                }

                profilInventory.setItem(31, new ItemBuilder(Material.GOLDEN_BOOTS).setName("§7» §6Spuren §7«").build());
                profilInventory.setItem(28, new ItemBuilder(Material.CHEST).setName("§7» §6Gadgets §7«").build());
                profilInventory.setItem(34, new ItemBuilder(Material.ENDER_CHEST).setName("§7» §6Shop §7«").build());
                profilInventory.setItem(13, new ItemBuilder(Material.REDSTONE).setName("§7» §6Settings §7«").build());

                player.openInventory(profilInventory);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 1f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Heart §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "hearttrail", true);
                SettingAPI.setSetting(player.getUniqueId(), "notetrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "flametrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "colortrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "ghosttrail", false);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
                player.sendMessage(xLobby.getInstance().getPrefix() + " §7Du hast die §6Heart-Spur §7ausgewählt");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Note §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "hearttrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "notetrail", true);
                SettingAPI.setSetting(player.getUniqueId(), "flametrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "colortrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "ghosttrail", false);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
                player.sendMessage(xLobby.getInstance().getPrefix() + " §7Du hast die §6Note-Spur §7ausgewählt");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Flame §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "hearttrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "notetrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "flametrail", true);
                SettingAPI.setSetting(player.getUniqueId(), "colortrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "ghosttrail", false);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
                player.sendMessage(xLobby.getInstance().getPrefix() + " §7Du hast die §6Flame-Spur §7ausgewählt");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Color §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "hearttrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "notetrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "flametrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "colortrail", true);
                SettingAPI.setSetting(player.getUniqueId(), "ghosttrail", false);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
                player.sendMessage(xLobby.getInstance().getPrefix() + " §7Du hast die §6Color-Spur §7ausgewählt");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Ghost §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "hearttrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "notetrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "flametrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "colortrail", false);
                SettingAPI.setSetting(player.getUniqueId(), "ghosttrail", true);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
                player.sendMessage(xLobby.getInstance().getPrefix() + " §7Du hast die §6Ghost-Spur §7ausgewählt");
            }
        }
    }
}