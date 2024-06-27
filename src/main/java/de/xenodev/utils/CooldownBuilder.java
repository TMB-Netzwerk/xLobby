package de.xenodev.utils;

import de.xenodev.mysql.SettingAPI;
import de.xenodev.mysql.TimeAPI;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
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

    public static void handleBorderCheck(){
        Bukkit.getScheduler().runTaskTimer(xLobby.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player players : Bukkit.getOnlinePlayers()){
                    if(players.getLocation().getY() >= 138){
                        if(players.isInsideVehicle()){
                            players.getVehicle().remove();
                        }
                        players.teleport(LocationBuilder.getLocation("Spawn"));
                    }
                }
            }
        }, 0, 1L);
    }

}
