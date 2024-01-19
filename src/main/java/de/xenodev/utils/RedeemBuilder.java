package de.xenodev.utils;

import de.xenodev.xLobby;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RedeemBuilder {

    private File file;
    private YamlConfiguration config;

    public RedeemBuilder(Player player){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        String fileName = player.getName() + "_" + date.format(now) + ".yml";
        file = new File("plugins/" + xLobby.getInstance().getName() + "/redeem", fileName);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveCodes(ArrayList<String> arrayList){
        config.set("codes", arrayList);
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPath(){
        return file.getPath();
    }
}
