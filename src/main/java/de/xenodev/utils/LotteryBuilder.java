package de.xenodev.utils;

import de.xenodev.mysql.CoinAPI;
import de.xenodev.mysql.LotteryAPI;
import de.xenodev.mysql.TicketAPI;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LotteryBuilder {

    private static final HashMap<Player, Integer> currentPage = new HashMap<>();
    public static int getCurrentPage(Player player) {
        return currentPage.get(player);
    }
    public static HashMap<Player, Integer> getCurrentPageMap() {
        return currentPage;
    }

    private static final HashMap<Player, Integer> currentChest = new HashMap<>();
    public static int getCurrentChest(Player player) {
        return currentChest.get(player);
    }
    public static HashMap<Player, Integer> getCurrentChestMap() {
        return currentChest;
    }

    private static final HashMap<Player, Integer> currentCoins = new HashMap<>();
    public static int getCurrentCoins(Player player) {
        return currentCoins.get(player);
    }
    public static HashMap<Player, Integer> getCurrentCoinsMap() {
        return currentCoins;
    }

    public static void openNewPage(Player player){
        Inventory inventory = Bukkit.createInventory(player, 9*6, "§8» §6Lottery Page §c" + getCurrentPage(player) + " §8«");
        int totalCoins = CoinAPI.getCoins(player.getUniqueId());
        int totalTickets = TicketAPI.getTickets(player.getUniqueId());
        int maxTicket = getCurrentPage(player) * 35;
        int minTicket = (getCurrentPage(player) - 1) * 35;

        for(int i = 0; i < 9; i++){
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        }

        for(int i = 9; i < 36; i++){
            inventory.setItem(i, new ItemBuilder(Material.AIR).build());
        }

        for(int i = 45; i < 54; i++){
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        }

        for (int i = 45; i < 54; i++)
        {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        }

        for(int i = minTicket; i < totalTickets; i++){
            inventory.addItem(new ItemBuilder(Material.PAPER).setEnchantment(Enchantment.CHANNELING, i).setName("§2Lottery Ticket").setFlag(ItemFlag.HIDE_ENCHANTS).build());
        }

        inventory.setItem(49, new ItemBuilder(Material.PAPER).setName("§aBuy a Ticket §8(§6" + getTicketCost(player) + "§8)").setLore("§7» §7Coins: §6" + totalCoins + " §8| §7Tickets: §6" + totalTickets + " §7«").build());
        if(totalTickets > maxTicket) {
            inventory.setItem(53, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("291ac432aa40d7e7a687aa85041de636712d4f022632dd5356c880521af2723a").setName("§2Weiter").build());
        }else{
            inventory.setItem(53, new ItemBuilder(Material.BARRIER).setName("§4Close").build());
        }

        if(getCurrentPage(player) == 1){
            inventory.setItem(45, new ItemBuilder(Material.BARRIER).setName("§4Close").build());
        }else{
            inventory.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setHeadByURL("7a2c12cb22918384e0a81c82a1ed99aebdce94b2ec2754800972319b57900afb").setName("§4Zurück").build());
        }

        player.openInventory(inventory);
    }

    public static void closeInOpen(Player player){
        if(CooldownBuilder.lotteryCooldown.contains(player)){
            CooldownBuilder.lotteryCooldown.remove(player);
            TicketAPI.addTickets(player.getUniqueId(), 1);
            LotteryBuilder.getCurrentPageMap().remove(player);
            getCurrentChestMap().remove(player);
            getCurrentCoinsMap().remove(player);
            getCurrentPageMap().remove(player);
        }
    }

    public static void closeOnFinish(Player player){
        getCurrentChestMap().remove(player);
        player.getOpenInventory().setTitle("§8» §eLottery Finish §8«");
        for (int i = 0; i < 27; i++) {
            if(player.getOpenInventory().getItem(i).getType().equals(Material.ENDER_CHEST)) {
                player.getOpenInventory().setItem(i, new ItemBuilder(Material.CHEST).setName("§cFertig mit Hebeln").build());
            }
        }
        CooldownBuilder.lotteryCooldown.remove(player);
        CoinAPI.addCoins(player.getUniqueId(), getCurrentCoins(player));
        player.sendMessage(xLobby.getPrefix() + "§7Du hast §e§l" + getCurrentCoins(player) + " §7Coins erhalten");
        LotteryAPI.addUses("open", 1);
        getCurrentCoinsMap().remove(player);
        player.playSound(player.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0, 1f, 1f);
    }

    public static void loadRandomLottery(Player player){
        CooldownBuilder.lotteryCooldown.add(player);
        TicketAPI.removeTickets(player.getUniqueId(), 1);
        getCurrentChestMap().put(player, 0);
        getCurrentCoinsMap().put(player, 0);
        Inventory inventory = Bukkit.createInventory(player, 9*3, "§8» §9Lottery Opening §8«");
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, new ItemBuilder(Material.ENDER_CHEST).setName("§dWähle eine Kiste").build());
        }
        player.openInventory(inventory);
    }

    public static ArrayList loadRandomCoins(){
        ArrayList<Object> arrayList = new ArrayList<>();
        Random random = new Random();
        double checkPercent = Math.random() * (13983816 + 1);
        String winName = "";
        Integer winCoins = 0;
        double checkHyper = LotteryAPI.getPercent("hyper") * 13983816;
        double checkBig = LotteryAPI.getPercent("big") * 13983816;
        double checkGood = LotteryAPI.getPercent("good") * 13983816;
        double checkMedium = LotteryAPI.getPercent("medium") * 13983816;
        double checkSmall = LotteryAPI.getPercent("small") * 13983816;
        if(checkPercent <= checkHyper){
            winCoins = 1000000;
            winName = "Hyper";
            LotteryAPI.setPercent("hyper", 0.0001);
            LotteryAPI.addUses("hyper", 1);
            LotteryAPI.addPercent("small", 1.0);
            LotteryAPI.addPercent("medium", 0.5);
            LotteryAPI.addPercent("good", 0.1);
            LotteryAPI.addPercent("big", 0.01);
        }else if(checkPercent <= checkBig && checkPercent > checkHyper){
            winCoins = random.nextInt(30000, 45000);
            winName = "Big";
            LotteryAPI.setPercent("big", 0.001);
            LotteryAPI.addUses("big", 1);
            LotteryAPI.addPercent("small", 0.01);
            LotteryAPI.addPercent("medium", 0.005);
            LotteryAPI.addPercent("good", 0.001);
            LotteryAPI.addPercent("hyper", 0.00001);
        }else if(checkPercent <= checkGood && checkPercent > checkBig){
            winCoins = random.nextInt(12000, 14500);
            winName = "Good";
            LotteryAPI.setPercent("good", 0.01);
            LotteryAPI.addUses("good", 1);
            LotteryAPI.addPercent("small", 0.001);
            LotteryAPI.addPercent("medium", 0.0005);
            LotteryAPI.addPercent("big", 0.00001);
            LotteryAPI.addPercent("hyper", 0.000001);
        }else if(checkPercent <= checkMedium && checkPercent > checkGood){
            winCoins = random.nextInt(1500, 3500);
            winName = "Medium";
            LotteryAPI.setPercent("medium", 0.05);
            LotteryAPI.addUses("medium", 1);
            LotteryAPI.addPercent("small", 0.0001);
            LotteryAPI.addPercent("good", 0.00001);
            LotteryAPI.addPercent("big", 0.000001);
            LotteryAPI.addPercent("hyper", 0.0000001);
        }else if(checkPercent <= checkSmall && checkPercent > checkMedium){
            winCoins = random.nextInt(600, 1100);
            winName = "Small";
            LotteryAPI.setPercent("small", 0.1);
            LotteryAPI.addUses("small", 1);
            LotteryAPI.addPercent("medium", 0.000005);
            LotteryAPI.addPercent("good", 0.000001);
            LotteryAPI.addPercent("big", 0.0000001);
            LotteryAPI.addPercent("hyper", 0.00000001);
        }else if(checkPercent > checkSmall){
            winCoins = random.nextInt(50, 450);
            winName = "Tiny";
            LotteryAPI.addUses("tiny", 1);
            LotteryAPI.addPercent("small", 0.000001);
            LotteryAPI.addPercent("medium", 0.0000005);
            LotteryAPI.addPercent("good", 0.0000001);
            LotteryAPI.addPercent("big", 0.00000001);
            LotteryAPI.addPercent("hyper", 0.000000001);
        }
        arrayList.add(winCoins);
        arrayList.add(winName);
        return arrayList;
    }

    public static int getTicketCost(Player player){
        return xLobby.getInstance().getConfig().getInt("Settings.Lotterycost");
    }
}
