package de.xenodev.events.main;

import de.xenodev.mysql.PlayersAPI;
import de.xenodev.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

public class ProfilEvent implements Listener {

    @EventHandler
    public void handleProfilItemInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if(event.getItem() == null) return;
        if(event.getItem().getType().equals(Material.PLAYER_HEAD)) {
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §aProfil §7«")) {
                open(player);
            }
        }
    }

    public static void open(Player player){
        player.closeInventory();
        Inventory profilInventory = Bukkit.createInventory(player, 9*6, "§7» §aProfil §7«");

        for (int i = 0; i < 53; i++) {
            profilInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        }

        String first_join = PlayersAPI.getFirstJoin(player.getUniqueId());
        String time = PlayersAPI.formateTime(player.getUniqueId());
        Integer joins = PlayersAPI.getJoins(player.getUniqueId());
        profilInventory.setItem(10, new ItemBuilder(Material.PAPER).setLore("§8• §7Beitritt: §b" + first_join, "§8• §7Zeit: §b" + time, "§8• §7Joins: §b" + joins).setName("§7» §6Spielzeit").build());

        int coins = PlayersAPI.getCoins(player.getUniqueId());
        int bytes = PlayersAPI.getBytes(player.getUniqueId());
        int tickets = PlayersAPI.getTickets(player.getUniqueId());
        String reward_time = PlayersAPI.remainingReward(PlayersAPI.getRewardTime(player.getUniqueId()) - System.currentTimeMillis());
        int reward_streak = PlayersAPI.getRewardStreak(player.getUniqueId());
        profilInventory.setItem(19, new ItemBuilder(Material.GOLD_INGOT).setLore("§8• §7Coins: §b" + coins, "§8• §7Bytes: §b" + bytes, "§8• §7Tickets: §b" + tickets, "", "§8• §7Reward Time: §b" + reward_time, "§8• §7Reward Streak: §b" + reward_streak).setName("§7» §6Currency").build());
        profilInventory.setItem(28, new ItemBuilder(Material.BARRIER).setName("§7» §cPlatzhalter §7«").build());
        profilInventory.setItem(37, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("45368f5635ff6c3407f0f356c5b6e0947bcd5e38490c9aa8b8b582a4f21ae3cb").setName("§7» §6Adventskalender").build());

        profilInventory.setItem(14, new ItemBuilder(Material.GOLDEN_BOOTS).setFlag(ItemFlag.HIDE_ATTRIBUTES).setName("§7» §6Spuren §7«").build());
        profilInventory.setItem(16, new ItemBuilder(Material.CHEST).setName("§7» §6Gadgets §7«").build());
        profilInventory.setItem(33, new ItemBuilder(Material.ENDER_CHEST).setName("§7» §6Shop §7«").build());
        profilInventory.setItem(53, new ItemBuilder(Material.REDSTONE).setEnchantment(Enchantment.CHANNELING, 0).setFlag(ItemFlag.HIDE_ENCHANTS).setName("§7» §6Settings §7«").build());

        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1f);
        player.openInventory(profilInventory);
    }

}
