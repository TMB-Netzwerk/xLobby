package de.xenodev.events.shop;

import de.xenodev.mysql.BuyAPI;
import de.xenodev.mysql.PlayersAPI;
import de.xenodev.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ShopGUI {

    public static void openGUI(Player player, String page){
        Inventory shopInventory = Bukkit.createInventory(player, 9*6, "§7» §6Shop §7«");

        for(int i = 2; i < 8; i++){
            shopInventory.setItem(i, new ItemBuilder(Material.AIR).build());
        }

        for(int i = 11; i < 17; i++){
            shopInventory.setItem(i, new ItemBuilder(Material.AIR).build());
        }

        for(int i = 20; i < 26; i++){
            shopInventory.setItem(i, new ItemBuilder(Material.AIR).build());
        }

        for(int i = 29; i < 35; i++){
            shopInventory.setItem(i, new ItemBuilder(Material.AIR).build());
        }

        for(int i = 36; i < 45; i++){
            shopInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        }

        shopInventory.setItem(0, new ItemBuilder(Material.CHEST).setName("§7» §6Gadgets §7«").build());
        shopInventory.setItem(1, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        shopInventory.setItem(9, new ItemBuilder(Material.BLAZE_POWDER).setName("§7» §6Trails §7«").build());
        shopInventory.setItem(10, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        shopInventory.setItem(18, new ItemBuilder(Material.RED_DYE).setName("§7» §6Colors §7«").build());
        shopInventory.setItem(19, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        shopInventory.setItem(27, new ItemBuilder(Material.BEACON).setName("§7» §6Anderes §7«").build());
        shopInventory.setItem(28, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());

        shopInventory.setItem(45, new ItemBuilder(Material.NETHER_STAR).setName("§dShop").setLore("§7» Coins: §b" + PlayersAPI.getCoins(player.getUniqueId()), "§7» Bytes: §b" + PlayersAPI.getBytes(player.getUniqueId())).build());
        shopInventory.setItem(53, new ItemBuilder(Material.ARROW).setName("§7» §6Zurück §7«").build());

        if(page.equalsIgnoreCase("gadgets")){
            player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            shopInventory.setItem(1, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).build());
            if(BuyAPI.getBuy(player.getUniqueId(), "enderperl").equals("false")){
                shopInventory.addItem(new ItemBuilder(Material.ENDER_PEARL).setName("§7» §6Enderperle §7«").setLore("§7» Kosten: §c15 §7Bytes").build());
            }
            if(BuyAPI.getBuy(player.getUniqueId(), "enterhaken").equals("false")){
                shopInventory.addItem(new ItemBuilder(Material.FISHING_ROD).setName("§7» §6Enterhaken §7«").setLore("§7» Kosten: §c20 §7Bytes").build());
            }
            if(BuyAPI.getBuy(player.getUniqueId(), "flugstab").equals("false")){
                shopInventory.addItem(new ItemBuilder(Material.BLAZE_ROD).setName("§7» §6Flugstab §7«").setLore("§7» Kosten: §c50 §7Bytes").build());
            }
        }else if(page.equalsIgnoreCase("trails")){
            player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            shopInventory.setItem(10, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).build());
            if(BuyAPI.getBuy(player.getUniqueId(), "hearttrail").equals("false")){
                shopInventory.addItem(new ItemBuilder(Material.LEATHER_BOOTS).setName("§7» §6Heart-Trail §7«").setLore("§7» Kosten: §c30 §7Bytes").setColor(Color.RED).build());
            }
            if(BuyAPI.getBuy(player.getUniqueId(), "notetrail").equals("false")){
                shopInventory.addItem(new ItemBuilder(Material.LEATHER_BOOTS).setName("§7» §6Note-Trail §7«").setLore("§7» Kosten: §c30 §7Bytes").setColor(Color.LIME).build());
            }
            if(BuyAPI.getBuy(player.getUniqueId(), "flametrail").equals("false")){
                shopInventory.addItem(new ItemBuilder(Material.LEATHER_BOOTS).setName("§7» §6Flame-Trail §7«").setLore("§7» Kosten: §c30 §7Bytes").setColor(Color.ORANGE).build());
            }
        }else if(page.equalsIgnoreCase("colors")){
            player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            shopInventory.setItem(19, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).build());
            shopInventory.addItem(new ItemBuilder(Material.WHITE_DYE).setName(ChatColor.WHITE + "Weiß").setLore("§7» Kosten: §d25000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.LIGHT_GRAY_DYE).setName(ChatColor.GRAY + "Hellgrau").setLore("§7» Kosten: §dFree").build());
            shopInventory.addItem(new ItemBuilder(Material.GRAY_DYE).setName(ChatColor.DARK_GRAY + "Grau").setLore("§7» Kosten: §d5000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.BLACK_DYE).setName(ChatColor.BLACK + "Schwarz").setLore("§7» Kosten: §d15000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.RED_DYE).setName(ChatColor.DARK_RED + "Dunkelrot").setLore("§7» Kosten: §d50000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.BROWN_DYE).setName(ChatColor.RED + "Rot").setLore("§7» Kosten: §d50000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.ORANGE_DYE).setName(ChatColor.GOLD + "Orange").setLore("§7» Kosten: §d30000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.YELLOW_DYE).setName(ChatColor.YELLOW + "Gelb").setLore("§7» Kosten: §d30000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.LIME_DYE).setName(ChatColor.GREEN + "Hellgrün").setLore("§7» Kosten: §d45000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.GREEN_DYE).setName(ChatColor.DARK_GREEN + "Grün").setLore("§7» Kosten: §d45000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.CYAN_DYE).setName(ChatColor.AQUA + "Türkis").setLore("§7» Kosten: §d50000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.MAGENTA_DYE).setName(ChatColor.DARK_AQUA + "Dunkeltürkis").setLore("§7» Kosten: §d50000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.LIGHT_BLUE_DYE).setName(ChatColor.BLUE + "Hellblau").setLore("§7» Kosten: §d25000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.BLUE_DYE).setName(ChatColor.DARK_BLUE + "Blau").setLore("§7» Kosten: §d25000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.PURPLE_DYE).setName(ChatColor.DARK_PURPLE + "Lila").setLore("§7» Kosten: §d20000 §7Coins").build());
            shopInventory.addItem(new ItemBuilder(Material.PINK_DYE).setName(ChatColor.LIGHT_PURPLE + "Pink").setLore("§7» Kosten: §d20000 §7Coins").build());
        }else if(page.equalsIgnoreCase("other")){
            player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            shopInventory.setItem(28, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).build());
            shopInventory.addItem(new ItemBuilder(Material.EMERALD).setName("§7» §6Get Bytes §7«").setLore("§7» Trade: §d5000 §7Coins -> §c1 §7Byte").build());
        }else {}

        player.openInventory(shopInventory);
    }

    public static boolean checkBuyColor(Player player, int amount){
        if(PlayersAPI.getCoins(player.getUniqueId()) >= amount) {
            PlayersAPI.removeCoins(player.getUniqueId(), amount);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100f);
            return true;
        }else{
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
            return false;
        }
    }

    public static void checkBuyGadget(Player player, int amount, String gadget){
        if(PlayersAPI.getBytes(player.getUniqueId()) >= amount){
            PlayersAPI.removeBytes(player.getUniqueId(), amount);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100f);
            BuyAPI.setBuy(player.getUniqueId(), gadget, "true");
        }else {
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
        }
    }

    public static void checkBuyTrail(Player player, int amount, String trail){
        if(PlayersAPI.getBytes(player.getUniqueId()) >= amount){
            PlayersAPI.removeBytes(player.getUniqueId(), amount);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100f);
            BuyAPI.setBuy(player.getUniqueId(), trail, "true");
        }else {
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
        }
    }

    public static void setTeamColor(Player player, ChatColor color){
        Scoreboard scoreboard = player.getScoreboard();
        Team team;
        for(Team teams : scoreboard.getTeams()){
            if(teams.hasEntry(player.getName())){
                team = teams;
                team.setColor(color);
            }
        }
    }

}
