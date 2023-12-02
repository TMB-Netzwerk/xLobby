package de.xenodev.events;

import de.xenodev.mysql.*;
import de.xenodev.utils.BoardBuilder;
import de.xenodev.utils.ItemBuilder;
import de.xenodev.utils.LocationBuilder;
import de.xenodev.utils.MusicBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class JoinEvent implements Listener {

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        event.setJoinMessage("");
        player.sendMessage("§8§l―――――――――― §5§lT§deam§5§lM§dega§5§lB§dyte §8§l――――――――――");
        player.sendMessage(" §e§lWillkommen auf unserem Netzwerk");
        player.sendMessage(" §8§l― §7Teamspeak: §aclan-tmb.de/teamspeak");
        player.sendMessage(" §8§l― §7Discord: §bclan-tmb.de/discord");
        player.sendMessage(" §8§l― §7Youtube: §cclan-tmb.de/youtube");
        player.sendMessage("§8§l―――――――――― §5§lT§deam§5§lM§dega§5§lB§dyte §8§l――――――――――");

        if(LocationBuilder.existsLocation("Spawn")){
            player.teleport(LocationBuilder.getLocation("Spawn"));
        }else{
            if(player.hasPermission("tmb.function.errorspawn")){
                player.sendTitle("§cDer Spawn wurde nicht gesetzt!", "§eSetze in mit /location", 20*1, 20*3, 20*1);
            }else{
                player.kickPlayer("§c§lDie Lobby ist nicht eingerichtet, bitte wende dich an einen Admin!");
            }
        }

        if(EventAPI.getEvent().equalsIgnoreCase("Christmas")){
            player.sendTitle("§bFrohe Weihnachten,", "§e§l" + player.getName() + "§7!", 30, 90, 30);
            player.setPlayerTime(18000, true);
            new MusicBuilder(player).getSongPlayer(MusicBuilder.song1).addPlayer().setPlaying(true);
        }else if(EventAPI.getEvent().equalsIgnoreCase("Halloween")){
            player.sendTitle("§6Happy Halloween,", "§e§l" + player.getName() + "§7!", 30, 90, 30);
            player.setPlayerTime(18000, true);
        }else if(EventAPI.getEvent().equalsIgnoreCase("Easter")){
            player.sendTitle("§aFrohe Ostern,", "§e§l" + player.getName() + "§7!", 30, 90, 30);
            player.setPlayerTime(1000, true);
        }else if(EventAPI.getEvent().equalsIgnoreCase("Newyear")){
            player.sendTitle("§cFrohes neues Jahr,", "§e§l" + player.getName() + "§7!", 30, 90, 30);
            player.setPlayerTime(18000, true);
        }else{
            player.sendTitle("§7Guten Tag,", "§e§l" + player.getName() + "§7!", 30, 90, 30);
            player.setPlayerTime(1000, true);
        }

        BoardBuilder.createBoard(player);
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setExp(0);
        player.setLevel(0);

        BytesAPI.createPlayer(player.getUniqueId());
        CoinAPI.createPlayer(player.getUniqueId());
        TimeAPI.createPlayer(player.getUniqueId());
        SettingAPI.createPlayer(player.getUniqueId());
        BuyAPI.createPlayer(player.getUniqueId());
        TicketAPI.createPlayer(player.getUniqueId());
        RewardAPI.createPlayer(player.getUniqueId());

        long current = System.currentTimeMillis();
        long time = RewardAPI.getTime(player.getUniqueId());
        long countdown = (24*60*60*1000)*2;

        if(Math.abs(current - time) >= countdown){
            RewardAPI.setStreak(player.getUniqueId(), 0);
        }

        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§7» §6Navigator §7«").build());

        if(SettingAPI.getSetting(player.getUniqueId(), "enterhaken").equals("true")){
            player.getInventory().setItem(1, new ItemBuilder(Material.FISHING_ROD).setName("§7» §9Enterhaken §7«").setUnbreakable().build());
        }else if(SettingAPI.getSetting(player.getUniqueId(), "flugstab").equals("true")){
            player.getInventory().setItem(1, new ItemBuilder(Material.BLAZE_ROD).setName("§7» §9Flugstab §7«").build());
        }else if(SettingAPI.getSetting(player.getUniqueId(), "eggbomb").equals("true")){
            player.getInventory().setItem(1, new ItemBuilder(Material.EGG).setName("§7» §9Eggbomb §7«").build());
        }else if(SettingAPI.getSetting(player.getUniqueId(), "enderperl").equals("true")){
            player.getInventory().setItem(1, new ItemBuilder(Material.ENDER_PEARL).setName("§7» §9Enderperle §7«").build());
        }else if(SettingAPI.getSetting(player.getUniqueId(), "switchbow").equals("true")){
            player.getInventory().setItem(1, new ItemBuilder(Material.BOW).setName("§7» §9Switch Bow §7«").build());
        }else if(SettingAPI.getSetting(player.getUniqueId(), "switchbow").equals("false") && SettingAPI.getSetting(player.getUniqueId(), "enderperl").equals("false") && SettingAPI.getSetting(player.getUniqueId(), "eggbomb").equals("false") && SettingAPI.getSetting(player.getUniqueId(), "flugstab").equals("false") && SettingAPI.getSetting(player.getUniqueId(), "enterhaken").equals("false")){
            player.getInventory().setItem(1, new ItemBuilder(Material.BARRIER).setName("§7» §cKein Gadget ausgewählt §7«").build());
        }
        player.getInventory().setItem(8, new ItemBuilder(Material.PLAYER_HEAD).setOwner(player.getName()).setName("§7» §aProfil §7«").build());
    }

}
