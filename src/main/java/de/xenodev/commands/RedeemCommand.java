package de.xenodev.commands;

import de.xenodev.mysql.RedeemAPI;
import de.xenodev.utils.LocationBuilder;
import de.xenodev.utils.RedeemBuilder;
import de.xenodev.xLobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class RedeemCommand implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player)sender;

            if(args.length == 1){
                String code = args[0];
                RedeemAPI.getRedeem(player, code);
            }else if(args.length == 3){
                if(!player.hasPermission("tmb.command.redeem")){
                    player.sendMessage(xLobby.getPrefix() + " §7Dir fehlt folgende Permission: §6" + "tmb.command.redeem");
                    return true;
                }
                int win = 0;
                try {
                    win = Integer.parseInt(args[0]);
                }catch (NumberFormatException e){
                    player.sendMessage(xLobby.getPrefix() + "§c" + args[0] + " §7ist keine Zahl");
                }
                int amount = 0;
                try {
                    amount = Integer.parseInt(args[2]);
                }catch (NumberFormatException e){
                    player.sendMessage(xLobby.getPrefix() + "§c" + args[2] + " §7ist keine Zahl");
                }
                if(args[1].equalsIgnoreCase("coins") || args[1].equalsIgnoreCase("bytes") || args[1].equalsIgnoreCase("tickets")){
                    RedeemBuilder redeemBuilder = new RedeemBuilder(player);
                    ArrayList<String> arrayList = new ArrayList<>();
                    String code = null;
                    for(int i = 0; i < amount; i++){
                        code = generatedCode();
                        RedeemAPI.createEvent(player, code, args[1], win);
                        arrayList.add(code);
                    }
                    redeemBuilder.saveCodes(arrayList);
                    player.sendMessage(xLobby.getPrefix() + "§7Du hast §6" + amount + " §7Codes generiert");
                    player.sendMessage(xLobby.getPrefix() + "§7Eine Liste der Codes findest du hier:");
                    player.sendMessage(xLobby.getPrefix() + "§7§oPath: " + redeemBuilder.getPath());
                }else{
                    player.sendMessage(xLobby.getPrefix() + "§c" + args[1] + " §7ist kein richtiger Typ");
                }
            }else{
                player.sendMessage("§cBitte Benutze: /redeem <code>");
                player.sendMessage("§cBitte Benutze: /redeem <win> <type> <amount> ");
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();

        if(!sender.hasPermission("tmb.command.redeem")) return null;

        if(args.length == 1) {
            arrayList.add("1000+");
        }else if(args.length == 2) {
            arrayList.add("coins");
            arrayList.add("bytes");
            arrayList.add("tickets");
        }else if(args.length == 3) {
            arrayList.add("5");
        }

        return arrayList;
    }

    private String generateRandomString(int length, SecureRandom random) {
        // Erlaubte Zeichen für den Code
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // Erzeuge den zufälligen Code
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            stringBuilder.append(allowedCharacters.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }

    private String generatedCode(){
        SecureRandom random = new SecureRandom();
        String code1 = generateRandomString(5, random);
        String code2 = generateRandomString(5, random);
        String code3 = generateRandomString(5, random);
        String code4 = generateRandomString(5, random);
        return code1 + "-" + code2 + "-" + code3 + "-" + code4;
    }
}
