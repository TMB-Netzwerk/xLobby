package de.xenodev.events.main;

import de.xenodev.mysql.CoinAPI;
import de.xenodev.mysql.LotteryAPI;
import de.xenodev.mysql.TicketAPI;
import de.xenodev.utils.CooldownBuilder;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.xLobby;
import eu.cloudnetservice.driver.inject.InjectionLayer;
import eu.cloudnetservice.driver.registry.ServiceRegistry;
import eu.cloudnetservice.modules.bridge.player.CloudPlayer;
import eu.cloudnetservice.modules.bridge.player.PlayerManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LotteryEvent implements Listener {

    private static HashMap<Player, Integer> currentChest = new HashMap<>();
    private static HashMap<Player, Integer> endCoins = new HashMap<>();

    @EventHandler
    public void handleLotteryOpen(PlayerInteractEvent event){
        Player player = event.getPlayer();
        int amount = xLobby.getInstance().getConfig().getInt("Settings.Lotterycost");
        if(event.getClickedBlock() == null) return;
        if(event.getClickedBlock().getType().equals(Material.ENDER_CHEST)){
            if(event.getClickedBlock().getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.END_PORTAL_FRAME)){
                if(!player.isSneaking()) {
                    Inventory inventory = Bukkit.createInventory(player, 9*6, "§7» §aLottery-Inv §7«");

                    for(int i = 0; i < 9; i++){
                        inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                    }

                    for(int i = 0; i < TicketAPI.getTickets(player.getUniqueId()); i++){
                        inventory.addItem(new ItemBuilder(Material.PAPER).setEnchantment(Enchantment.CHANNELING, i).setName("§2Lottery-Ticket").setFlag(ItemFlag.HIDE_ENCHANTS).build());
                    }

                    for(int i = 45; i < 54; i++){
                        inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                    }

                    player.openInventory(inventory);
                }else{
                    Inventory inventory = Bukkit.createInventory(player, 9 * 1, "§7» §aLottery-Info §7«");

                    for (int i = 0; i < 9; i++) {
                        inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                    }
                    inventory.setItem(0, new ItemBuilder(Material.PAPER).setName("§5Lottery-Ticket").setLore("§7Kosten: §e" + xLobby.getInstance().getConfig().getInt("Settings.Lotterycost"), "", "§7Geöffnet: §c" + LotteryAPI.getLottery("open")).build());
                    inventory.setItem(2, new ItemBuilder(Material.COPPER_INGOT).setName("§c§lTiny-Win").setLore("§7Prozent: §d90%", "§7Coins: §e1 §7- §e400", "", "§7Geöffnet: §c" + LotteryAPI.getLottery("tiny")).setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                    inventory.setItem(3, new ItemBuilder(Material.IRON_INGOT).setName("§7§lSmall-Win").setLore("§7Prozent: §d10%", "§7Coins: §e500 §7- §e1000", "", "§7Geöffnet: §c" + LotteryAPI.getLottery("small")).setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                    inventory.setItem(4, new ItemBuilder(Material.GOLD_INGOT).setName("§6§lMedium-Win").setLore("§7Prozent: §d5%", "§7Coins: §e1200 §7- §e3000", "", "§7Geöffnet: §c" + LotteryAPI.getLottery("medium")).setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                    inventory.setItem(5, new ItemBuilder(Material.EMERALD).setName("§2§lGood-Win").setLore("§7Prozent: §d1%", "§7Coins: §e10000 §7- §e13500", "", "§7Geöffnet: §c" + LotteryAPI.getLottery("good")).setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                    inventory.setItem(6, new ItemBuilder(Material.DIAMOND).setName("§3§lBig-Win").setLore("§7Prozent: §d0,25%", "§7Coins: §e15000 §7- §e25000", "", "§7Geöffnet: §c" + LotteryAPI.getLottery("big")).setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                    inventory.setItem(7, new ItemBuilder(Material.NETHERITE_INGOT).setName("§5§lHyper-Win").setLore("§7Prozent: §d0,01%", "§7Coins: §e1000000", "", "§7Geöffnet: §c" + LotteryAPI.getLottery("hyper")).setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());

                    player.openInventory(inventory);
                }
            }
        }
    }

    @EventHandler
    public void handleInventoryLottery(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("§7» §aLottery §7«")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType().equals(Material.ENDER_CHEST)){
                if(CooldownBuilder.lotteryCooldown.containsKey(player)) return;
                int giveCoins = checkChestCoins();
                if(currentChest.containsKey(player)) {
                    currentChest.replace(player, currentChest.get(player), currentChest.get(player) + 1);
                }else{
                    currentChest.put(player, 1);
                    endCoins.put(player, 0);
                }

                if(giveCoins >= 1 && giveCoins <= 400){
                    event.getInventory().setItem(event.getRawSlot(), new ItemBuilder(Material.COPPER_INGOT).setName("§c§lTiny-Win " + "§8[§e" + giveCoins + "§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 100f);
                    LotteryAPI.addLottery("tiny", 1);
                }else if(giveCoins >= 500 && giveCoins <= 1000){
                    event.getInventory().setItem(event.getRawSlot(), new ItemBuilder(Material.IRON_INGOT).setName("§7§lSmall-Win " + "§8[§e" + giveCoins + "§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 100f);
                    LotteryAPI.addLottery("small", 1);
                }else if(giveCoins >= 800 && giveCoins <= 3000){
                    event.getInventory().setItem(event.getRawSlot(), new ItemBuilder(Material.GOLD_INGOT).setName("§6§lMedium-Win " + "§8[§e" + giveCoins + "§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 100f);
                    LotteryAPI.addLottery("medium", 1);
                }else if(giveCoins >= 10000 && giveCoins <= 13500){
                    event.getInventory().setItem(event.getRawSlot(), new ItemBuilder(Material.EMERALD).setName("§2§lGood-Win " + "§8[§e" + giveCoins + "§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 100f);
                    LotteryAPI.addLottery("good", 1);
                }else if(giveCoins >= 15000 && giveCoins <= 25000){
                    event.getInventory().setItem(event.getRawSlot(), new ItemBuilder(Material.DIAMOND).setName("§3§lBig-Win " + "§8[§e" + giveCoins + "§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 100f);
                    LotteryAPI.addLottery("big", 1);
                }else if(giveCoins == 1000000){
                    event.getInventory().setItem(event.getRawSlot(), new ItemBuilder(Material.NETHERITE_INGOT).setName("§5§lHyper-Win " + "§8[§e" + giveCoins + "§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                    LotteryAPI.addLottery("hyper", 1);
                    PlayerManager playerManager = InjectionLayer.ext().instance(ServiceRegistry.class).firstProvider(PlayerManager.class);
                    List<CloudPlayer> cloudPlayers = List.copyOf(playerManager.onlinePlayers().players());
                    for(CloudPlayer cloudPlayer : cloudPlayers){
                        playerManager.playerExecutor(cloudPlayer.uniqueId()).sendChatMessage(Component.text(xLobby.getPrefix() + "§e" + player.getName() + " §7hat einen §6Hyper-Win §7gezogen!"));
                    }
                    Bukkit.getWorld(player.getWorld().getUID()).playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);
                }

                if(currentChest.get(player) == 1){
                    endCoins.replace(player, endCoins.get(player), endCoins.get(player) + giveCoins);
                }else if(currentChest.get(player) == 2){
                    endCoins.replace(player, endCoins.get(player), endCoins.get(player) + giveCoins);
                }else if(currentChest.get(player) == 3){
                    endCoins.replace(player, endCoins.get(player), endCoins.get(player) + giveCoins);
                }else if(currentChest.get(player) == 4){
                    endCoins.replace(player, endCoins.get(player), endCoins.get(player) + giveCoins);
                }else if(currentChest.get(player) == 5) {
                    endCoins.replace(player, endCoins.get(player), endCoins.get(player) + giveCoins);
                    CoinAPI.addCoins(player.getUniqueId(), endCoins.get(player));
                    player.sendMessage(xLobby.getPrefix() + "§7Du hast §e§l" + endCoins.get(player) + " §7Coins erhalten");
                    LotteryAPI.addLottery("open", 1);
                    CooldownBuilder.lotteryCooldown.put(player, 3);
                    player.getOpenInventory().setTitle("§7» §aLottery-Finish §7«");
                    for(int i = 0; i < 27; i++){
                        if(player.getOpenInventory().getItem(i).getType().equals(Material.ENDER_CHEST)){
                            player.getOpenInventory().setItem(i, new ItemBuilder(Material.CHEST).build());
                        }
                    }
                }
            }
        }else if(event.getView().getTitle().equals("§7» §aLottery-Info §7«")){
            event.setCancelled(true);
        }else if(event.getView().getTitle().equals("§7» §aLottery-Inv §7«")){
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType().equals(Material.PAPER)){
                if(CooldownBuilder.lotteryCooldown.containsKey(player)){
                    player.sendMessage(xLobby.getPrefix() + "§7Du verwendest die Lottery zu schnell");
                    return;
                }
                if (!CooldownBuilder.lotteryStopSpam.containsKey(player)) {
                    CooldownBuilder.lotteryStopSpam.put(player, 3);
                    TicketAPI.removeTickets(player.getUniqueId(), 1);
                    Inventory inventory = Bukkit.createInventory(player, 9 * 3, "§7» §aLottery §7«");

                    for (int i = 0; i < 27; i++) {
                        inventory.setItem(i, new ItemBuilder(Material.ENDER_CHEST).build());
                    }

                    player.openInventory(inventory);
                }
            }
        }
    }

    @EventHandler
    public void handleCloseLottery(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if (event.getView().getTitle().equalsIgnoreCase("§7» §aLottery §7«")) {
            if(!CooldownBuilder.lotteryCooldown.containsKey(player)){
                TicketAPI.addTickets(player.getUniqueId(), 1);
                if(currentChest.containsKey(player)) currentChest.remove(player);
                if(endCoins.containsKey(player)) endCoins.remove(player);
            }
        }else if (event.getView().getTitle().equalsIgnoreCase("§7» §aLottery-Finish §7«")) {
            if(currentChest.containsKey(player)) currentChest.remove(player);
            if(endCoins.containsKey(player)) endCoins.remove(player);
        }
    }

    private Random random = new Random();
    private int checkChestCoins(){
        int giveProcent = random.nextInt(100000);
        int giveCoins = 0;
        if(giveProcent < 10){
            //Das sind 0,01% | Hyper-Win
            giveCoins = 1000000;
        }else if(giveProcent < 250){
            //Das sind 0,25% | Big-Win
            giveCoins = random.nextInt(15000, 25000);
        }else if(giveProcent < 1000){
            //Das sind 1% | Good-Win
            giveCoins = random.nextInt(10000, 13500);
        }else if(giveProcent < 5000){
            //Das sind 5% | Medium-Win
            giveCoins = random.nextInt(1200, 3000);
        }else if(giveProcent < 10000){
            //Das sind 10% | Small-Win
            giveCoins = random.nextInt(500, 1000);
        }else{
            //Das sind 90% | Tiny-Win
            giveCoins = random.nextInt(1, 400);
        }
        return giveCoins;
    }
}
