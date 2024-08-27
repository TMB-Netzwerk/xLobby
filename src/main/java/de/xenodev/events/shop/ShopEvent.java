package de.xenodev.events.shop;

import de.xenodev.events.main.ProfilEvent;
import de.xenodev.mysql.PlayersAPI;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;

public class ShopEvent implements Listener {

    @EventHandler
    public void handleOpenShopGUI(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §aProfil §7«")) {
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Shop §7«")){
                event.setCancelled(true);
                ShopGUI.openGUI(player, "default");
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }
        }
    }

    @EventHandler
    public void handleChangeShopPage(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §6Shop §7«")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Gadgets §7«")){
                ShopGUI.openGUI(player, "gadgets");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Trails §7«")){
                ShopGUI.openGUI(player, "trails");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Colors §7«")){
                ShopGUI.openGUI(player, "colors");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Anderes §7«")){
                ShopGUI.openGUI(player, "other");
            }
        }
    }

    @EventHandler
    public void handleCloseShop(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase("§7» §6Shop §7«")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Zurück §7«")) {
                ProfilEvent.open(player);
            }
        }
    }

    @EventHandler
    public void handleBuyColors(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §6Shop §7«")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType().equals(Material.WHITE_DYE)){
                if(!ShopGUI.checkBuyColor(player, 25000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "WHITE");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.LIGHT_GRAY_DYE)){
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100f);
                PlayersAPI.setColor(player.getUniqueId(), "GRAY");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.GRAY_DYE)){
                if(!ShopGUI.checkBuyColor(player, 5000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "DARK_GRAY");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.BLACK_DYE)){
                if(!ShopGUI.checkBuyColor(player, 15000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "BLACK");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.RED_DYE) && !event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Colors §7«")){
                if(!ShopGUI.checkBuyColor(player, 50000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "DARK_RED");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.BROWN_DYE)){
                if(!ShopGUI.checkBuyColor(player, 50000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "RED");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.ORANGE_DYE)){
                if(!ShopGUI.checkBuyColor(player, 30000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "GOLD");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.YELLOW_DYE)){
                if(!ShopGUI.checkBuyColor(player, 30000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "YELLOW");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.LIME_DYE)){
                if(!ShopGUI.checkBuyColor(player, 45000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "GREEN");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.GREEN_DYE)){
                if(!ShopGUI.checkBuyColor(player, 45000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "DARK_GREEN");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.CYAN_DYE)){
                if(!ShopGUI.checkBuyColor(player, 50000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "AQUA");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.MAGENTA_DYE)){
                if(!ShopGUI.checkBuyColor(player, 50000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "DARK_AQUA");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.LIGHT_BLUE_DYE)){
                if(!ShopGUI.checkBuyColor(player, 25000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "BLUE");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.BLUE_DYE)){
                if(!ShopGUI.checkBuyColor(player, 25000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "DARK_BLUE");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.PURPLE_DYE)){
                if(!ShopGUI.checkBuyColor(player, 20000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "DARK_PURPLE");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }else if(event.getCurrentItem().getType().equals(Material.PINK_DYE)){
                if(!ShopGUI.checkBuyColor(player, 20000)) return;
                PlayersAPI.setColor(player.getUniqueId(), "LIGHT_PURPLE");
                ChatColor color = ChatColor.valueOf(PlayersAPI.getColor(player.getUniqueId()));
                player.sendMessage(xLobby.getPrefix() + "§7Dein Name wird so angezeigt: " + color + player.getName());
                ShopGUI.setTeamColor(player, color);
            }
        }
    }

    @EventHandler
    public void handleBuyGadgets(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §6Shop §7«")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Enderperle §7«")){
                ShopGUI.checkBuyGadget(player, 15, "enderperl");
                ShopGUI.openGUI(player, "gadgets");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Enterhaken §7«")){
                ShopGUI.checkBuyGadget(player, 20, "enterhaken");
                ShopGUI.openGUI(player, "gadgets");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Flugstab §7«")){
                ShopGUI.checkBuyGadget(player, 50, "flugstab");
                ShopGUI.openGUI(player, "gadgets");
            }
        }
    }

    @EventHandler
    public void handleBuyTrails(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §6Shop §7«")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Heart-Trail §7«")){
                ShopGUI.checkBuyTrail(player, 30, "hearttrail");
                ShopGUI.openGUI(player, "trails");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Note-Trail §7«")){
                ShopGUI.checkBuyTrail(player, 30, "notetrail");
                ShopGUI.openGUI(player, "trails");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Flame-Trail §7«")){
                ShopGUI.checkBuyTrail(player, 30, "flametrail");
                ShopGUI.openGUI(player, "trails");
            }
        }
    }

    @EventHandler
    public void handleBuyOthers(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §6Shop §7«")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Get Bytes §7«")){
                player.closeInventory();
                player.sendMessage(xLobby.getPrefix() + "§7Wie viele Bytes möchtest du? Antworte im Chat!");
                player.sendMessage(xLobby.getPrefix() + "§7§o(Ein Byte kostet 5000 Coins)");
                if(!checkBuyBytes.contains(player)){
                    checkBuyBytes.add(player);
                }
            }
        }
    }

    private ArrayList<Player> checkBuyBytes = new ArrayList<>();

    @EventHandler
    public void checkBuyBytesChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(checkBuyBytes.contains(player)){
            event.setCancelled(true);
            Integer bytes = null;
            try{
                bytes = Integer.valueOf(event.getMessage());
                if(bytes >= 1){
                    if(PlayersAPI.getCoins(player.getUniqueId()) >= bytes * 5000){
                        PlayersAPI.removeCoins(player.getUniqueId(), bytes * 5000);
                        PlayersAPI.addBytes(player.getUniqueId(), bytes);
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100f);
                        player.sendMessage(xLobby.getPrefix() + "§7Du hast §c" + bytes + " §7Bytes im Wert von §6" + (bytes * 5000) + " §7Coins gekauft");
                        checkBuyBytes.remove(player);
                    }else {
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
                        player.sendMessage(xLobby.getPrefix() + "§cDu hast nicht genügend Coins");
                        checkBuyBytes.remove(player);
                    }
                }else {
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
                    player.sendMessage(xLobby.getPrefix() + "§cDu hast nicht genügend Bytes angegeben");
                }
            }catch (NumberFormatException e) {
                player.sendMessage(xLobby.getPrefix() + "§cDu hast keine Zahl angegeben");
            }
        }
    }

}
