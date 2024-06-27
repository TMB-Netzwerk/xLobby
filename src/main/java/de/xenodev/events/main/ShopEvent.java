package de.xenodev.events.main;

import de.xenodev.mysql.*;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.HashMap;

public class ShopEvent implements Listener {

    private HashMap<Player, Integer> bytesAmount = new HashMap<>();

    @EventHandler
    public void handleShopInventoryInteractOpen(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §aProfil §7«")) {
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Shop §7«")){
                event.setCancelled(true);
                checkInventory(player, "start");
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            }
        }
    }

    @EventHandler
    public void handleShopInventoryInteractOpenByName(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §6Shop §7«")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Gadgets §7«")){
                checkInventory(player, "gatgets");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Spuren §7«")){
                checkInventory(player, "spuren");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Extras §7«")){
                checkInventory(player, "extras");
            }
        }
    }

    @EventHandler
    public void handleShopInventoryInteractBuy(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §6Shop §7«")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Zurück §7«")) {
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
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Enderperle §7«")){
                checkBuy(player, "enderperl", 15, "bytes", null);
                checkInventory(player, "gatgets");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Enterhaken §7«")){
                checkBuy(player, "enterhaken", 20, "bytes", null);
                checkInventory(player, "gatgets");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Flugstab §7«")){
                checkBuy(player, "flugstab", 50, "bytes", null);
                checkInventory(player, "gatgets");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Eggbomb §7«")){
                checkBuy(player, "eggbomb", 130, "bytes", null);
                checkInventory(player, "gatgets");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Switch Bow §7«")){
                checkBuy(player, "switchbow", 130, "bytes", null);
                checkInventory(player, "gatgets");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Heart-Trail §7«")){
                checkBuy(player, "hearttrail", 30, "bytes", null);
                checkInventory(player, "spuren");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Note-Trail §7«")){
                checkBuy(player, "notetrail", 30, "bytes", null);
                checkInventory(player, "spuren");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Flame-Trail §7«")){
                checkBuy(player, "flametrail", 30, "bytes", null);
                checkInventory(player, "spuren");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Color-Trail §7«")){
                checkBuy(player, "colortrail", 70, "bytes", null);
                checkInventory(player, "spuren");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Ghost-Trail §7«")){
                checkBuy(player, "ghosttrail", 90, "bytes", null);
                checkInventory(player, "spuren");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Christmas-Trail §7«")){
                checkBuy(player, "christmastrail", 99999, "bytes", null);
                checkInventory(player, "spuren");
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Coins -> Bytes §7«")){
                if(bytesAmount.containsKey(player)){
                    bytesAmount.remove(player);
                }
                bytesAmount.put(player, 1);
                Inventory shopInventory = Bukkit.createInventory(player, 9*3, "§7» §6Bytes §7«");

                for(int i = 0; i < 27; i++){
                    shopInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                }

                shopInventory.setItem(1, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("a99aaf2456a6122de8f6b62683f2bc2eed9abb81fd5bea1b4c23a58156b669").setName("§7Byte §2+1").build());
                shopInventory.setItem(2, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("7d695d335e6be8cb2a34e05e18ea2d12c3b17b8166ba62d6982a643df71ffac5").setName("§7Byte §2+10").build());

                shopInventory.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("2e2cc42015e6678f8fd49ccc01fbf787f1ba2c32bcf559a015332fc5db50").setName("§6Coins wechseln").setLore("§7Wechsle §6" + 5000*bytesAmount.get(player) + " §7Coins zu §c" + 1*bytesAmount.get(player) + " §7Byte").build());

                shopInventory.setItem(6, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("437862cdc159998ed6b6fdccaaa4675867d4484db512a84c367fabf4caf60").setName("§7Byte §c-10").build());
                shopInventory.setItem(7, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("3912d45b1c78cc22452723ee66ba2d15777cc288568d6c1b62a545b29c7187").setName("§7Byte §c-1").build());

                shopInventory.setItem(26, new ItemBuilder(Material.ARROW).setName("§7» §6Zurück §7«").build());

                player.openInventory(shopInventory);
            }
        }
    }

    @EventHandler
    public void handleShopInventoryInteractBytes(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase("§7» §6Bytes §7«")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §6Zurück §7«")) {
                player.closeInventory();

                bytesAmount.remove(player);
                checkInventory(player, "start");
                player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 1f);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Byte §c-1")){
                if(bytesAmount.get(player) >= 2) {
                    bytesAmount.replace(player, bytesAmount.get(player), bytesAmount.get(player) - 1);
                }
                event.getInventory().setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("2e2cc42015e6678f8fd49ccc01fbf787f1ba2c32bcf559a015332fc5db50").setName("§6Coins wechseln").setLore("§7Wechsle §6" + 5000*bytesAmount.get(player) + " §7Coins zu §c" + 1*bytesAmount.get(player) + " §7Byte").build());
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Byte §2+1")){
                bytesAmount.replace(player, bytesAmount.get(player), bytesAmount.get(player) + 1);
                event.getInventory().setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("2e2cc42015e6678f8fd49ccc01fbf787f1ba2c32bcf559a015332fc5db50").setName("§6Coins wechseln").setLore("§7Wechsle §6" + 5000*bytesAmount.get(player) + " §7Coins zu §c" + 1*bytesAmount.get(player) + " §7Byte").build());
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Byte §c-10")){
                if(bytesAmount.get(player) >= 11){
                    bytesAmount.replace(player, bytesAmount.get(player), bytesAmount.get(player) - 10);
                }
                event.getInventory().setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("2e2cc42015e6678f8fd49ccc01fbf787f1ba2c32bcf559a015332fc5db50").setName("§6Coins wechseln").setLore("§7Wechsle §6" + 5000*bytesAmount.get(player) + " §7Coins zu §c" + 1*bytesAmount.get(player) + " §7Byte").build());
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Byte §2+10")){
                bytesAmount.replace(player, bytesAmount.get(player), bytesAmount.get(player) + 10);
                event.getInventory().setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("2e2cc42015e6678f8fd49ccc01fbf787f1ba2c32bcf559a015332fc5db50").setName("§6Coins wechseln").setLore("§7Wechsle §6" + 5000*bytesAmount.get(player) + " §7Coins zu §c" + 1*bytesAmount.get(player) + " §7Byte").build());
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Coins wechseln")){
                checkBuy(player, "bytes", 5000, "coins", bytesAmount.get(player));
                checkInventory(player, "extras");
            }
        }
    }

    private void checkInventory(Player player, String shopName){
        Integer amountCoins = CoinAPI.getCoins(player.getUniqueId());
        Integer amountBytes = BytesAPI.getBytes(player.getUniqueId());
        Integer amountTickets = TicketAPI.getTickets(player.getUniqueId());

        Inventory shopInventory = Bukkit.createInventory(player, 9*5, "§7» §6Shop §7«");

        for(int i = 0; i < 44; i++){
            shopInventory.setItem(i, new ItemBuilder(Material.AIR).build());
        }

        for(int i = 27; i < 36; i++){
            shopInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        }

        shopInventory.setItem(1, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        shopInventory.setItem(10, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        shopInventory.setItem(19, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());

        shopInventory.setItem(0, new ItemBuilder(Material.CHEST).setName("§7» §6Gadgets §7«").build());
        shopInventory.setItem(9, new ItemBuilder(Material.GOLDEN_BOOTS).setName("§7» §6Spuren §7«").build());
        shopInventory.setItem(18, new ItemBuilder(Material.BEACON).setName("§7» §6Extras §7«").build());

        shopInventory.setItem(36, new ItemBuilder(Material.NETHER_STAR).setName("§7» §7Coins: §6" + amountCoins + " §8| §7Bytes: §6" + amountBytes + " §8| §7Tickets: §6" + amountTickets + " §7«").build());
        shopInventory.setItem(44, new ItemBuilder(Material.ARROW).setName("§7» §6Zurück §7«").build());

        if(shopName.equalsIgnoreCase("gatgets")){
            player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            shopInventory.setItem(1, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).build());
            shopInventory.setItem(10, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
            shopInventory.setItem(19, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
            if(BuyAPI.getBuy(player.getUniqueId(), "enderperl").equals("false")){
                shopInventory.addItem(new ItemBuilder(Material.ENDER_PEARL).setName("§7» §6Enderperle §7«").setLore("§7Die §6Enderperle §7kostet §c15 §7Bytes").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
            }
            if(BuyAPI.getBuy(player.getUniqueId(), "enterhaken").equals("false")){
                shopInventory.addItem(new ItemBuilder(Material.FISHING_ROD).setName("§7» §6Enterhaken §7«").setLore("§7Der §6Enterhaken §7kostet §c20 §7Bytes").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
            }
            if(BuyAPI.getBuy(player.getUniqueId(), "flugstab").equals("false")){
                shopInventory.addItem(new ItemBuilder(Material.BLAZE_ROD).setName("§7» §6Flugstab §7«").setLore("§7Der §6Flugstab §7kostet §c50 §7Bytes").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
            }
            if(EventAPI.getEvent().equalsIgnoreCase("Easter")) {
            }
            if(EventAPI.getEvent().equalsIgnoreCase("Christmas")) {
            }
            shopInventory.setItem(36, new ItemBuilder(Material.NETHER_STAR).setName("§7» §7Coins: §6" + amountCoins + " §8| §7Bytes: §6" + amountBytes + " §8| §7Tickets: §6" + amountTickets + " §7«").build());
        }else if(shopName.equalsIgnoreCase("spuren")){
            player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            shopInventory.setItem(1, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
            shopInventory.setItem(10, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).build());
            shopInventory.setItem(19, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
            if(BuyAPI.getBuy(player.getUniqueId(), "hearttrail").equals("false")){
                shopInventory.addItem(new ItemBuilder(Material.LEATHER_BOOTS).setName("§7» §6Heart-Trail §7«").setLore("§7Die §6Heart-Trail §7kosten §c30 §7Bytes").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).setFlag(ItemFlag.HIDE_ATTRIBUTES).setColor(Color.RED).build());
            }
            if(BuyAPI.getBuy(player.getUniqueId(), "notetrail").equals("false")){
                shopInventory.addItem(new ItemBuilder(Material.LEATHER_BOOTS).setName("§7» §6Note-Trail §7«").setLore("§7Die §6Note-Trail §7kosten §c30 §7Bytes").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).setFlag(ItemFlag.HIDE_ATTRIBUTES).setColor(Color.LIME).build());
            }
            if(BuyAPI.getBuy(player.getUniqueId(), "flametrail").equals("false")){
                shopInventory.addItem(new ItemBuilder(Material.LEATHER_BOOTS).setName("§7» §6Flame-Trail §7«").setLore("§7Die §6Flame-Trail §7kosten §c30 §7Bytes").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).setFlag(ItemFlag.HIDE_ATTRIBUTES).setColor(Color.ORANGE).build());
            }
            if(EventAPI.getEvent().equalsIgnoreCase("Easter")) {
            }
            if(EventAPI.getEvent().equalsIgnoreCase("Halloween")) {
            }
            if(EventAPI.getEvent().equalsIgnoreCase("Christmas")) {
            }
            shopInventory.setItem(36, new ItemBuilder(Material.NETHER_STAR).setName("§7» §7Coins: §6" + amountCoins + " §8| §7Bytes: §6" + amountBytes + " §8| §7Tickets: §6" + amountTickets + " §7«").build());
        }else if(shopName.equalsIgnoreCase("extras")){
            player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
            shopInventory.setItem(1, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
            shopInventory.setItem(10, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
            shopInventory.setItem(19, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).build());
            shopInventory.addItem(new ItemBuilder(Material.EMERALD).setName("§7» §6Coins -> Bytes §7«").setLore("§7Wechsle §65000 §7Coins zu §c1 §7Byte").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
            shopInventory.setItem(36, new ItemBuilder(Material.NETHER_STAR).setName("§7» §7Coins: §6" + amountCoins + " §8| §7Bytes: §6" + amountBytes + " §8| §7Tickets: §6" + amountTickets + " §7«").build());
            if(bytesAmount.containsKey(player)){
                bytesAmount.remove(player);
            }
            bytesAmount.put(player, 1);
        }else{}

        player.openInventory(shopInventory);
    }

    private void checkBuy(Player player, String buyedName, Integer buyedAmount, String buyedWallet, Integer amount){
        if(buyedWallet.equals("bytes")){
            if(BytesAPI.getBytes(player.getUniqueId()) >= buyedAmount){
                BytesAPI.removeBytes(player.getUniqueId(), buyedAmount);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100f);
                BuyAPI.setBuy(player.getUniqueId(), buyedName, "true");
            }else {
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
            }
        }else if(buyedWallet.equals("coins")){
            if(CoinAPI.getCoins(player.getUniqueId()) >= buyedAmount*amount){
                CoinAPI.removeCoins(player.getUniqueId(), buyedAmount*amount);
                BytesAPI.addBytes(player.getUniqueId(), 1*amount);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100f);
            }else {
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
            }
        }
    }
}
