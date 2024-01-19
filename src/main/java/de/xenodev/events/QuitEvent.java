package de.xenodev.events;

import de.xenodev.mysql.EventAPI;
import de.xenodev.utils.LotteryBuilder;
import de.xenodev.utils.MusicBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage("");
        if(EventAPI.getEvent().equalsIgnoreCase("Christmas")){
            new MusicBuilder(player).getSongPlayer(MusicBuilder.song1).removePlayer();
        }
        LotteryBuilder.closeInOpen(player);
    }

}
