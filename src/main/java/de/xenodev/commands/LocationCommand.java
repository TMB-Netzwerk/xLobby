package de.xenodev.commands;

import de.xenodev.utils.LocationBuilder;
import de.xenodev.xLobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LocationCommand implements CommandExecutor, TabCompleter {

    private ArrayList<String> locationNames = new ArrayList<>();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player)sender;
            if(!player.hasPermission("tmb.command.location")){
                player.sendMessage(xLobby.getPrefix() + " §7Dir fehlt folgende Permission: §6" + "tmb.command.locations");
                return true;
            }

            setLocationNames();

            if(locationNames.contains(args[0])){
                LocationBuilder.setLocation(args[0], player.getLocation());
                player.sendMessage(xLobby.getPrefix() + " §7Du hast die Location für §6" + args[0].toUpperCase() + " §7gesetzt");
            }else{
                player.sendMessage(xLobby.getPrefix() + " §cDiesen Locationnamen gibt es nicht!");
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if(!sender.hasPermission("tmb.command.location")) return null;

        if(args.length == 1) {
            setLocationNames();
        }

        return locationNames;
    }

    private void setLocationNames(){
        locationNames.add("Spawn");
        locationNames.add("TMBCraft");
        locationNames.add("Bedwars");
        locationNames.add("Bauserver");
    }
}
