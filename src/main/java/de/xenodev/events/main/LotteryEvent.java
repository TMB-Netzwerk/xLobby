package de.xenodev.events.main;

import de.xenodev.mysql.CoinAPI;
import de.xenodev.mysql.LotteryAPI;
import de.xenodev.mysql.TicketAPI;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.utils.LotteryBuilder;
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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class LotteryEvent implements Listener {

    @EventHandler
    public void handleLotteryInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getClickedBlock() == null || !event.getClickedBlock().getType().equals(Material.ENDER_CHEST) || !event.getClickedBlock().getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.END_PORTAL_FRAME)) return;
        if(player.isSneaking()){
            Inventory inventory = Bukkit.createInventory(null, 9*1, "§8» §3Lottery Stats §8«");

            inventory.setItem(0, new ItemBuilder(Material.PAPER).setName("§5Lottery-Ticket").setLore("§7Kosten: §e" + xLobby.getInstance().getConfig().getInt("Settings.Lotterycost"), "", "§7Geöffnet: §c" + LotteryAPI.getUses("open")).build());
            inventory.setItem(1, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
            inventory.setItem(2, new ItemBuilder(Material.COPPER_INGOT).setName("§c§lTiny-Win").setLore("§7Prozent: §d" + LotteryAPI.getPercent("tiny"), "§7Coins: §e50 §7- §e450", "", "§7Geöffnet: §c" + LotteryAPI.getUses("tiny")).setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
            inventory.setItem(3, new ItemBuilder(Material.IRON_INGOT).setName("§7§lSmall-Win").setLore("§7Prozent: §d" + String.format("%.2f", LotteryAPI.getPercent("small")), "§7Coins: §e600 §7- §e1100", "", "§7Geöffnet: §c" + LotteryAPI.getUses("small")).setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
            inventory.setItem(4, new ItemBuilder(Material.GOLD_INGOT).setName("§6§lMedium-Win").setLore("§7Prozent: §d" + String.format("%.3f", LotteryAPI.getPercent("medium")), "§7Coins: §e1500 §7- §e3500", "", "§7Geöffnet: §c" + LotteryAPI.getUses("medium")).setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
            inventory.setItem(5, new ItemBuilder(Material.EMERALD).setName("§2§lGood-Win").setLore("§7Prozent: §d" + String.format("%.3f", LotteryAPI.getPercent("good")), "§7Coins: §e12000 §7- §e14500", "", "§7Geöffnet: §c" + LotteryAPI.getUses("good")).setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
            inventory.setItem(6, new ItemBuilder(Material.DIAMOND).setName("§3§lBig-Win").setLore("§7Prozent: §d" + String.format("%.4f", LotteryAPI.getPercent("big")), "§7Coins: §e30000 §7- §e45000", "", "§7Geöffnet: §c" + LotteryAPI.getUses("big")).setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
            inventory.setItem(7, new ItemBuilder(Material.NETHERITE_INGOT).setName("§5§lHyper-Win").setLore("§7Prozent: §d" + String.format("%.5f", LotteryAPI.getPercent("hyper")), "§7Coins: §e1000000", "", "§7Geöffnet: §c" + LotteryAPI.getUses("hyper")).setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
            inventory.setItem(8, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());

            player.openInventory(inventory);
        }else{
            LotteryBuilder.getCurrentPageMap().put(player, 1);
            LotteryBuilder.openNewPage(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleInteractLotteryInv(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().contains("Lottery")){
            event.setCancelled(true);
            if(event.getView().getTitle().contains("Page")){
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Close")){
                    player.closeInventory();
                    LotteryBuilder.getCurrentPageMap().remove(player);
                    player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 1f);
                }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Zurück")){
                    LotteryBuilder.getCurrentPageMap().replace(player, LotteryBuilder.getCurrentPage(player), LotteryBuilder.getCurrentPage(player) - 1);
                    LotteryBuilder.openNewPage(player);
                    player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
                }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Weiter")){
                    LotteryBuilder.getCurrentPageMap().replace(player, LotteryBuilder.getCurrentPage(player), LotteryBuilder.getCurrentPage(player) + 1);
                    LotteryBuilder.openNewPage(player);
                    player.playSound(player.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 100, 100f);
                }else if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Buy a Ticket")){
                    if(CoinAPI.getCoins(player.getUniqueId()) >= xLobby.getInstance().getConfig().getInt("Settings.Lotterycost")){
                        CoinAPI.removeCoins(player.getUniqueId(), xLobby.getInstance().getConfig().getInt("Settings.Lotterycost"));
                        TicketAPI.addTickets(player.getUniqueId(), 1);
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100f);
                        LotteryBuilder.openNewPage(player);
                    }else {
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
                    }
                }else if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Lottery Ticket")){
                    LotteryBuilder.loadRandomLottery(player);
                }
            }else if(event.getView().getTitle().contains("Opening")){
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§dWähle eine Kiste")){
                    ArrayList arrayList = LotteryBuilder.loadRandomCoins();
                    int randomCoins = (int) arrayList.get(0);
                    String luckName = (String) arrayList.get(1);
                    if(luckName.equalsIgnoreCase("Hyper")){
                        event.getInventory().setItem(event.getSlot(), new ItemBuilder(Material.NETHERITE_INGOT).setName("§5§lHyper-Win " + "§8[§e" + randomCoins + "§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                        PlayerManager playerManager = InjectionLayer.ext().instance(ServiceRegistry.class).firstProvider(PlayerManager.class);
                        List<CloudPlayer> cloudPlayers = List.copyOf(playerManager.onlinePlayers().players());
                        for(CloudPlayer cloudPlayer : cloudPlayers){
                            playerManager.playerExecutor(cloudPlayer.uniqueId()).sendChatMessage(Component.text(xLobby.getPrefix() + "§e" + player.getName() + " §7hat einen §6Hyper-Win §7gezogen!"));
                        }
                        Bukkit.getWorld(player.getWorld().getUID()).playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);
                    }else if(luckName.equalsIgnoreCase("Big")){
                        event.getInventory().setItem(event.getSlot(), new ItemBuilder(Material.DIAMOND).setName("§3§lBig-Win " + "§8[§e" + randomCoins + "§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 100f);
                    }else if(luckName.equalsIgnoreCase("Good")){
                        event.getInventory().setItem(event.getSlot(), new ItemBuilder(Material.EMERALD).setName("§2§lGood-Win " + "§8[§e" + randomCoins + "§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 100f);
                    }else if(luckName.equalsIgnoreCase("Medium")){
                        event.getInventory().setItem(event.getSlot(), new ItemBuilder(Material.GOLD_INGOT).setName("§6§lMedium-Win " + "§8[§e" + randomCoins + "§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 100f);
                    }else if(luckName.equalsIgnoreCase("Small")){
                        event.getInventory().setItem(event.getSlot(), new ItemBuilder(Material.IRON_INGOT).setName("§7§lSmall-Win " + "§8[§e" + randomCoins + "§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 100f);
                    }else if(luckName.equalsIgnoreCase("Tiny")){
                        event.getInventory().setItem(event.getSlot(), new ItemBuilder(Material.COPPER_INGOT).setName("§c§lTiny-Win " + "§8[§e" + randomCoins + "§8]").setEnchantment(Enchantment.CHANNELING, 1).setFlag(ItemFlag.HIDE_ENCHANTS).build());
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 100f);
                    }
                    LotteryBuilder.getCurrentChestMap().replace(player, LotteryBuilder.getCurrentChest(player), LotteryBuilder.getCurrentChest(player) + 1);
                    LotteryBuilder.getCurrentCoinsMap().replace(player, LotteryBuilder.getCurrentCoins(player), LotteryBuilder.getCurrentCoins(player) + randomCoins);

                    if(LotteryBuilder.getCurrentChest(player) == 5){
                        LotteryBuilder.closeOnFinish(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void handleLotteryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if(event.getView().getTitle().contains("Lottery Opening")){
            LotteryBuilder.closeInOpen(player);
        }
    }

}
