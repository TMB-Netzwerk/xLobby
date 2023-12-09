package de.xenodev.events.main;

import de.xenodev.mysql.SettingAPI;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.xLobby;
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

public class SettingsEvent implements Listener {

    @EventHandler
    public void handleSettingsInventoryInteractOpen(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §aProfil §7«")) {
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Settings §7«")){
                event.setCancelled(true);
                checkInventory(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }
        }
    }

    private void checkInventory(Player player){
        Inventory settingsInventory = Bukkit.createInventory(player, 9*6, "§7» §6Settings §7«");

        for(int i = 36; i < 45; i++){
            settingsInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        }

        if(SettingAPI.getSetting(player.getUniqueId(), "Eggboost_other").equals("true")){
            settingsInventory.setItem(0, new ItemBuilder(Material.EGG).setName("§7» §6Eggboost Other §7«").setLore("§7Eggbomb Otherboost §8[§2activated§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        }else{
            settingsInventory.setItem(0, new ItemBuilder(Material.EGG).setName("§7» §6Eggboost Other §7«").setLore("§7Eggbomb Otherboost §8[§cdeactivated§8]").build());
        }
        if(SettingAPI.getSetting(player.getUniqueId(), "Eggboost_self").equals("true")){
            settingsInventory.setItem(1, new ItemBuilder(Material.EGG).setName("§7» §6Eggboost Self §7«").setLore("§7Eggbomb Selfboost §8[§2activated§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        }else{
            settingsInventory.setItem(1, new ItemBuilder(Material.EGG).setName("§7» §6Eggboost Self §7«").setLore("§7Eggbomb Selfboost §8[§cdeactivated§8]").build());
        }
        if(SettingAPI.getSetting(player.getUniqueId(), "Hide").equals("true")){
            settingsInventory.setItem(2, new ItemBuilder(Material.TOTEM_OF_UNDYING).setName("§7» §6Hider §7«").setLore("§7Spieler verstecken §8[§2activated§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        }else{
            settingsInventory.setItem(2, new ItemBuilder(Material.TOTEM_OF_UNDYING).setName("§7» §6Hider §7«").setLore("§7Spieler verstecken §8[§cdeactivated§8]").build());
        }
        if(SettingAPI.getSetting(player.getUniqueId(), "Snowfall").equals("true")){
            settingsInventory.setItem(3, new ItemBuilder(Material.SNOWBALL).setName("§7» §6Snowfall §7«").setLore("§7Schneefall §8[§2activated§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        }else{
            settingsInventory.setItem(3, new ItemBuilder(Material.SNOWBALL).setName("§7» §6Snowfall §7«").setLore("§7Schneefall §8[§cdeactivated§8]").build());
        }
        if(SettingAPI.getSetting(player.getUniqueId(), "Traileffect").equals("true")){
            settingsInventory.setItem(4, new ItemBuilder(Material.NETHERITE_BOOTS).setName("§7» §6Traileffect §7«").setLore("§7Traileffekte anzeigen §8[§2activated§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
        }else{
            settingsInventory.setItem(4, new ItemBuilder(Material.NETHERITE_BOOTS).setName("§7» §6Traileffect §7«").setLore("§7Traileffekte anzeigen §8[§cdeactivated§8]").build());
        }

        settingsInventory.setItem(52, new ItemBuilder(Material.BARRIER).setName("§7» §cZurücksetzen §7«").build());
        settingsInventory.setItem(53, new ItemBuilder(Material.ARROW).setName("§7» §6Zurück §7«").build());

        player.openInventory(settingsInventory);
    }

    @EventHandler
    public void handleSettingsInventoryInteract(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §6Settings §7«")){
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE) || event.getCurrentItem().getType().equals(Material.AIR)) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §cZurücksetzen §7«")){
                SettingAPI.setSetting(player.getUniqueId(), "Hide", "false");
                for(Player all : Bukkit.getOnlinePlayers()){
                    player.showPlayer(xLobby.getInstance(), all);
                }
                SettingAPI.setSetting(player.getUniqueId(), "Eggboost_other", "false");
                SettingAPI.setSetting(player.getUniqueId(), "Eggboost_self", "false");
                SettingAPI.setSetting(player.getUniqueId(), "Snowfall", "false");
                SettingAPI.setSetting(player.getUniqueId(), "Traileffect", "false");
                checkInventory(player);
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Zurück §7«")){
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
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Hider §7«")){
                if(SettingAPI.getSetting(player.getUniqueId(), "Hide").equals("true")) {
                    SettingAPI.setSetting(player.getUniqueId(), "Hide", "false");
                    for(Player all : Bukkit.getOnlinePlayers()){
                        player.showPlayer(xLobby.getInstance(), all);
                    }
                }else if(SettingAPI.getSetting(player.getUniqueId(), "Hide").equals("false")){
                    SettingAPI.setSetting(player.getUniqueId(), "Hide", "true");
                    SettingAPI.setSetting(player.getUniqueId(), "Eggboost_other", "false");
                    SettingAPI.setSetting(player.getUniqueId(), "Traileffect", "false");
                    for(Player all : Bukkit.getOnlinePlayers()){
                        player.hidePlayer(xLobby.getInstance(), all);
                    }
                }
                checkInventory(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Eggboost Other §7«")){
                if(SettingAPI.getSetting(player.getUniqueId(), "Eggboost_other").equals("true")) {
                    SettingAPI.setSetting(player.getUniqueId(), "Eggboost_other", "false");
                }else if(SettingAPI.getSetting(player.getUniqueId(), "Eggboost_other").equals("false")){
                    SettingAPI.setSetting(player.getUniqueId(), "Eggboost_other", "true");
                }
                checkInventory(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Eggboost Self §7«")){
                if(SettingAPI.getSetting(player.getUniqueId(), "Eggboost_self").equals("true")) {
                    SettingAPI.setSetting(player.getUniqueId(), "Eggboost_self", "false");
                }else if(SettingAPI.getSetting(player.getUniqueId(), "Eggboost_self").equals("false")){
                    SettingAPI.setSetting(player.getUniqueId(), "Eggboost_self", "true");
                }
                checkInventory(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Snowfall §7«")){
                if(SettingAPI.getSetting(player.getUniqueId(), "Snowfall").equals("true")) {
                    SettingAPI.setSetting(player.getUniqueId(), "Snowfall", "false");
                }else if(SettingAPI.getSetting(player.getUniqueId(), "Snowfall").equals("false")){
                    SettingAPI.setSetting(player.getUniqueId(), "Snowfall", "true");
                }
                checkInventory(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Traileffect §7«")){
                if(SettingAPI.getSetting(player.getUniqueId(), "Traileffect").equals("true")) {
                    SettingAPI.setSetting(player.getUniqueId(), "Traileffect", "false");
                }else if(SettingAPI.getSetting(player.getUniqueId(), "Traileffect").equals("false")){
                    SettingAPI.setSetting(player.getUniqueId(), "Traileffect", "true");
                }
                checkInventory(player);
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }
        }
    }

}
