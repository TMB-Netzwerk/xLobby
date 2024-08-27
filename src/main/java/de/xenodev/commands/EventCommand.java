package de.xenodev.commands;

import de.xenodev.mysql.EventAPI;
import de.xenodev.utils.snow.StarterpackHandler;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EventCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player)sender;

            if(!player.hasPermission("tmb.command.event")){
                player.sendMessage(xLobby.getPrefix() + " §7Dir fehlt folgende Permission: §6" + "tmb.command.event");
                return true;
            }

            if(args.length == 1){
                player.sendMessage(xLobby.getPrefix() + "§7Du hast das Event auf §6" + args[0] + " §7gesetzt");
                EventAPI.setEvent(args[0]);
                for(Player players : Bukkit.getOnlinePlayers()){
                    if(args[0].equalsIgnoreCase("Christmas")){
                        players.sendTitle("§bSchau mal in den Himmel §e§l" + players.getName(), "§7§oDer erste Schnee fällt!", 30, 90, 30);
                        players.setPlayerTime(18000, true);
                        new StarterpackHandler();
                    }else if(args[0].equalsIgnoreCase("Halloween")){
                        players.sendTitle("§6Süßes sonst gibts Saures§7,", "§c§l" + players.getName() + "§4§l!!!", 30, 90, 30);
                        players.setPlayerTime(18000, true);
                    }else if(args[0].equalsIgnoreCase("Easter")){
                        players.sendTitle("§aDer Osterhase kommt vorbei§7,", "§e§l" + players.getName() + "§7!", 30, 90, 30);
                        players.setPlayerTime(1000, true);
                    }else if(args[0].equalsIgnoreCase("Newyear")){
                        players.sendTitle("§cEs leutert ein neues Jahr ein§7,", "§e§l" + players.getName() + "§7!", 30, 90, 30);
                        players.setPlayerTime(18000, true);
                    }else{
                        players.sendTitle("§7Der Alltag kommt zurück,", "§e§l" + players.getName() + "§7!", 30, 90, 30);
                        players.setPlayerTime(1000, true);
                    }
                }
            }else{
                player.sendMessage(xLobby.getPrefix() + "§7Folgendes Event ist zur Zeit aktiv: §6" + EventAPI.getEvent());
            }

        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();

        if(!sender.hasPermission("tmb.command.event")) return null;

        if(args.length == 1) {
            arrayList.add("Halloween");
            arrayList.add("Christmas");
            arrayList.add("Easter");
            arrayList.add("Newyear");
            arrayList.add("Nothing");
        }

        return arrayList;
    }
}
