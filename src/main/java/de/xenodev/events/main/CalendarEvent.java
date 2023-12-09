package de.xenodev.events.main;

import de.xenodev.mysql.*;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CalendarEvent implements Listener {

    @EventHandler
    public void handleProfilItemInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Boolean stopCalendar = true;
        if(stopCalendar.equals(true) && !player.getName().equals("godlessFloof")){
            player.sendMessage(xLobby.getPrefix() + "§cDer Adventskalender ist zur Zeit deaktiviert");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");

        Integer days = 0;

        if(event.getItem() == null) return;
        if(event.getItem().getType().equals(Material.PLAYER_HEAD)) {
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7» §cAdventskalender §7«")) {
                Inventory calendarInventory = Bukkit.createInventory(player, 9*6, "§7» §cAdventskalender §7«");

                for (int i = 0; i < 54; i++) {
                    calendarInventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
                }

                for(int i = 12; i < 15; i++){
                    days++;
                    String stringDay = "";
                    if(days < 10){
                        stringDay = "0" + days;
                    }else{
                        stringDay = "" + days;
                    }
                    if(day.format(now).equals(stringDay)){
                        if(CalendarAPI.getDay(player.getUniqueId(), "day" + days).equals("false")) {
                            calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("45368f5635ff6c3407f0f356c5b6e0947bcd5e38490c9aa8b8b582a4f21ae3cb").setName("§aTürchen " + days).build());
                        }else{
                            calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("bfe732b3ecb2fabc038fb06db8c53a7ffb030db92544e1b2256f01cb2eb822b7").setName("§9Türchen " + days).build());
                        }
                    }else {
                        if(CalendarAPI.getDay(player.getUniqueId(), "day" + days).equals("false")) {
                            if (Integer.valueOf(day.format(now)) > days) {
                                calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("143e0f73d8f447a573f33226fe4f9683b64dda42e7142c130b5b33c29f160183").setName("§cTürchen " + days).build());
                            } else {
                                calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("69b564a6f73283112a70b9ce7e15753eb86bd12e7659ec4d0dc0855c6bea76e").setName("§6Türchen " + days).build());
                            }
                        }else{
                            calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("bfe732b3ecb2fabc038fb06db8c53a7ffb030db92544e1b2256f01cb2eb822b7").setName("§9Türchen " + days).build());
                        }
                    }
                }

                for(int i = 19; i < 26; i++){
                    days++;
                    String stringDay = "";
                    if(days < 10){
                        stringDay = "0" + days;
                    }else{
                        stringDay = "" + days;
                    }
                    if(day.format(now).equals(stringDay)){
                        if(CalendarAPI.getDay(player.getUniqueId(), "day" + days).equals("false")) {
                            calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("45368f5635ff6c3407f0f356c5b6e0947bcd5e38490c9aa8b8b582a4f21ae3cb").setName("§aTürchen " + days).build());
                        }else{
                            calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("bfe732b3ecb2fabc038fb06db8c53a7ffb030db92544e1b2256f01cb2eb822b7").setName("§9Türchen " + days).build());
                        }
                    }else {
                        if(CalendarAPI.getDay(player.getUniqueId(), "day" + days).equals("false")) {
                            if (Integer.valueOf(day.format(now)) > days) {
                                calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("143e0f73d8f447a573f33226fe4f9683b64dda42e7142c130b5b33c29f160183").setName("§cTürchen " + days).build());
                            } else {
                                calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("69b564a6f73283112a70b9ce7e15753eb86bd12e7659ec4d0dc0855c6bea76e").setName("§6Türchen " + days).build());
                            }
                        }else{
                            calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("bfe732b3ecb2fabc038fb06db8c53a7ffb030db92544e1b2256f01cb2eb822b7").setName("§9Türchen " + days).build());
                        }
                    }
                }

                for(int i = 28; i < 35; i++){
                    days++;
                    String stringDay = "";
                    if(days < 10){
                        stringDay = "0" + days;
                    }else{
                        stringDay = "" + days;
                    }
                    if(day.format(now).equals(stringDay)){
                        if(CalendarAPI.getDay(player.getUniqueId(), "day" + days).equals("false")) {
                            calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("45368f5635ff6c3407f0f356c5b6e0947bcd5e38490c9aa8b8b582a4f21ae3cb").setName("§aTürchen " + days).build());
                        }else{
                            calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("bfe732b3ecb2fabc038fb06db8c53a7ffb030db92544e1b2256f01cb2eb822b7").setName("§9Türchen " + days).build());
                        }
                    }else {
                        if(CalendarAPI.getDay(player.getUniqueId(), "day" + days).equals("false")) {
                            if (Integer.valueOf(day.format(now)) > days) {
                                calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("143e0f73d8f447a573f33226fe4f9683b64dda42e7142c130b5b33c29f160183").setName("§cTürchen " + days).build());
                            } else {
                                calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("69b564a6f73283112a70b9ce7e15753eb86bd12e7659ec4d0dc0855c6bea76e").setName("§6Türchen " + days).build());
                            }
                        }else{
                            calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("bfe732b3ecb2fabc038fb06db8c53a7ffb030db92544e1b2256f01cb2eb822b7").setName("§9Türchen " + days).build());
                        }
                    }
                }

                for(int i = 37; i < 44; i++){
                    days++;
                    String stringDay = "";
                    if(days < 10){
                        stringDay = "0" + days;
                    }else{
                        stringDay = "" + days;
                    }
                    if(day.format(now).equals(stringDay)){
                        if(CalendarAPI.getDay(player.getUniqueId(), "day" + days).equals("false")) {
                            calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("45368f5635ff6c3407f0f356c5b6e0947bcd5e38490c9aa8b8b582a4f21ae3cb").setName("§aTürchen " + days).build());
                        }else{
                            calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("bfe732b3ecb2fabc038fb06db8c53a7ffb030db92544e1b2256f01cb2eb822b7").setName("§9Türchen " + days).build());
                        }
                    }else {
                        if(CalendarAPI.getDay(player.getUniqueId(), "day" + days).equals("false")) {
                            if (Integer.valueOf(day.format(now)) > days) {
                                calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("143e0f73d8f447a573f33226fe4f9683b64dda42e7142c130b5b33c29f160183").setName("§cTürchen " + days).build());
                            } else {
                                calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("69b564a6f73283112a70b9ce7e15753eb86bd12e7659ec4d0dc0855c6bea76e").setName("§6Türchen " + days).build());
                            }
                        }else{
                            calendarInventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setOwnerURL("bfe732b3ecb2fabc038fb06db8c53a7ffb030db92544e1b2256f01cb2eb822b7").setName("§9Türchen " + days).build());
                        }
                    }
                }

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1f);
                player.openInventory(calendarInventory);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase("§7» §cAdventskalender §7«")) {
            event.setCancelled(true);
            if(event.getCurrentItem().getItemMeta().getDisplayName().contains("§a")){
                String number = event.getCurrentItem().getItemMeta().getDisplayName().replace("§aTürchen ", "");
                player.sendMessage(xLobby.getPrefix() + "§aDu hast Türchen " + number + " geöffnet");
                CalendarAPI.setDay(player.getUniqueId(), "day" + number, "true");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1f);
                player.closeInventory();
                int los = new Random().nextInt(1, 5);
                switch (los){
                    case 1:
                        int randomCoins = new Random().nextInt(1, 10000);
                        CoinAPI.addCoins(player.getUniqueId(), randomCoins);
                        player.sendMessage(xLobby.getPrefix() + "§7Du hast §6" + randomCoins + " §7Coins aus dem Geschenk bekommen");
                        break;
                    case 2:
                        int randomBytes = new Random().nextInt(1, 10);
                        BytesAPI.addBytes(player.getUniqueId(), randomBytes);
                        player.sendMessage(xLobby.getPrefix() + "§7Du hast §6" + randomBytes + " §7Bytes aus dem Geschenk bekommen");
                        break;
                    case 3:
                        int randomTime = new Random().nextInt(1, 4);
                        int getCoins = ((new Random().nextInt(500, 1000)*6)*randomTime)+(2500*randomTime);
                        TimeAPI.addHours(player.getUniqueId(), randomTime);
                        CoinAPI.addCoins(player.getUniqueId(), getCoins);
                        player.sendMessage(xLobby.getPrefix() + "§7Du hast §6" + randomTime + " §7Stunden Spielzeit aus dem Geschenk bekommen");
                        player.sendMessage(xLobby.getPrefix() + "§7Du hast §e" + getCoins + " §7Coins fürs Spielen erhalten");
                        break;
                    case 4:
                        int randomTicket = new Random().nextInt(1, 20);
                        TicketAPI.addTickets(player.getUniqueId(), randomTicket);
                        player.sendMessage(xLobby.getPrefix() + "§7Du hast §6" + randomTicket + " §7Tickets aus dem Geschenk bekommen");
                        break;
                }
                if(CalendarAPI.getDay(player.getUniqueId(), "day1").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day2").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day3").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day4").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day5").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day6").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day7").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day8").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day9").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day10").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day11").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day12").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day13").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day14").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day15").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day16").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day17").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day18").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day19").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day20").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day21").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day22").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day23").equals("true") &&
                        CalendarAPI.getDay(player.getUniqueId(), "day24").equals("true")){
                    BuyAPI.setBuy(player.getUniqueId(), "christmastrail", "true");
                    CoinAPI.addCoins(player.getUniqueId(), 50000);
                    BytesAPI.addBytes(player.getUniqueId(), 10);
                    TicketAPI.addTickets(player.getUniqueId(), 30);
                    player.sendMessage(xLobby.getPrefix() + "§7Du hast alle 24 Türchen geöffnet! Folgende Belohnungen erhältst du:");
                    player.sendMessage(xLobby.getPrefix() + "§8- §5Christmastrail §7(1x)");
                    player.sendMessage(xLobby.getPrefix() + "§8- §eCoins §7(50000)");
                    player.sendMessage(xLobby.getPrefix() + "§8- §6Bytes §7(10)");
                    player.sendMessage(xLobby.getPrefix() + "§8- §aTickets §7(30)");
                }
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().contains("§c")){
                String number = event.getCurrentItem().getItemMeta().getDisplayName().replace("§cTürchen ", "");
                player.sendMessage(xLobby.getPrefix() + "§6Du hast Türchen " + number + " leider verpasst");
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
                player.closeInventory();
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().contains("§6")){
                String number = event.getCurrentItem().getItemMeta().getDisplayName().replace("§6Türchen ", "");
                player.sendMessage(xLobby.getPrefix() + "§6Du kannst Türchen " + number + " noch nicht öffnen");
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
                player.closeInventory();
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().contains("§9")){
                String number = event.getCurrentItem().getItemMeta().getDisplayName().replace("§9Türchen ", "");
                player.sendMessage(xLobby.getPrefix() + "§9Du hast Türchen " + number + " bereits geöffnet");
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 100, 1f);
                player.closeInventory();
            }
        }
    }

}
