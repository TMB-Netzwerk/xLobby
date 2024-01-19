package de.xenodev.utils;

import de.xenodev.mysql.SettingAPI;
import de.xenodev.mysql.TimeAPI;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class CooldownBuilder {

    public static ArrayList<Player> lotteryCooldown = new ArrayList<>();
    public static HashMap<Player, Integer> eggbombCooldown = new HashMap<>();
    public static HashMap<Player, Integer> switchbowCooldown = new HashMap<>();
    public static HashMap<Player, Integer> enderperlCooldown = new HashMap<>();

    public static void handlePlayerCooldown(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(xLobby.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player players : Bukkit.getOnlinePlayers()){
                    if(eggbombCooldown.containsKey(players)){
                        eggbombCooldown.replace(players, eggbombCooldown.get(players), eggbombCooldown.get(players) - 1);
                        if(eggbombCooldown.get(players) == 0) eggbombCooldown.remove(players);
                    }
                    if(switchbowCooldown.containsKey(players)){
                        switchbowCooldown.replace(players, switchbowCooldown.get(players), switchbowCooldown.get(players) - 1);
                        if(switchbowCooldown.get(players) == 0) switchbowCooldown.remove(players);
                    }
                    if(enderperlCooldown.containsKey(players)){
                        enderperlCooldown.replace(players, enderperlCooldown.get(players), enderperlCooldown.get(players) - 1);
                        if(enderperlCooldown.get(players) == 0) enderperlCooldown.remove(players);
                    }
                }
            }
        }, 0, 20L);
    }

    public static void handleTrailCooldown(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(xLobby.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player players : Bukkit.getOnlinePlayers()){
                    if(SettingAPI.getSetting(players.getUniqueId(), "Traileffect").equals("true")) {
                        if (SettingAPI.getSetting(players.getUniqueId(), "Colortrail").equals("true")) {
                            xLobby.list113.DUST_COLOR_TRANSITION.color(255,0,0,0,255,0, 2D).packet(true, players.getLocation().add(0, 0.5, 0)).sendTo(players);
                            xLobby.list113.DUST_COLOR_TRANSITION.color(0,255,0,0,0,255, 2D).packet(true, players.getLocation().add(0, 0.5, 0)).sendTo(players);
                            xLobby.list113.DUST_COLOR_TRANSITION.color(0,0,255,255,0,0, 2D).packet(true, players.getLocation().add(0, 0.5, 0)).sendTo(players);
                        }
                        if (SettingAPI.getSetting(players.getUniqueId(), "Flametrail").equals("true")) {
                            xLobby.list113.FLAME.packet(true, players.getLocation().add(0, 0.5, 0)).sendTo(players);
                        }
                        if (SettingAPI.getSetting(players.getUniqueId(), "Ghosttrail").equals("true")) {
                            xLobby.list113.SOUL_FIRE_FLAME.packet(true, players.getLocation().add(0, 0.5, 0)).sendTo(players);
                            xLobby.list113.DUST.color(Color.ORANGE, 2D).packet(true, players.getLocation().add(0, 0.5, 0)).sendTo(players);
                            xLobby.list113.DUST.color(Color.BLACK, 2D).packet(true, players.getLocation().add(0, 0.5, 0)).sendTo(players);
                        }
                        if (SettingAPI.getSetting(players.getUniqueId(), "Hearttrail").equals("true")) {
                            xLobby.list113.HEART.packet(true, players.getLocation().add(0, 0.5, 0)).sendTo(players);
                        }
                        if (SettingAPI.getSetting(players.getUniqueId(), "Notetrail").equals("true")) {
                            xLobby.list113.NOTE.packet(true, players.getLocation().add(0, 0.5, 0)).sendTo(players);
                        }
                        if (SettingAPI.getSetting(players.getUniqueId(), "Christmastrail").equals("true")) {
                            xLobby.list113.ITEM_SNOWBALL.packet(true, players.getLocation().add(0, 0.5, 0)).sendTo(players);
                            xLobby.list18.VILLAGER_HAPPY.packet(true, players.getLocation().add(0, 0.5, 0)).sendTo(players);
                            xLobby.list113.SNOWFLAKE.packet(true, players.getLocation().add(0, 0.5, 0)).sendTo(players);
                        }
                    }
                }
            }
        }, 0, 1L);
    }

}
