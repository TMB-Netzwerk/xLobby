package de.xenodev.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage("");
    }

}
