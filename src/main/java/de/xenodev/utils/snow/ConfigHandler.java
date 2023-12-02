package de.xenodev.utils.snow;

import de.xenodev.xLobby;

import java.io.File;
import java.util.List;

public class ConfigHandler {
    public ConfigHandler() {
        this.load();
    }

    public void load() {
        if(!xLobby.getInstance().getDataFolder().exists()){
            xLobby.getInstance().getDataFolder().mkdir();
        }
        if(!new File(xLobby.getInstance().getDataFolder(), "config.yml").exists()){
            xLobby.getInstance().saveDefaultConfig();
        }
    }

    public Boolean isRealistic() {
        return xLobby.getInstance().getConfig().getBoolean("Settings.Snow.Realistic");
    }

    public int getAmount() {
        return xLobby.getInstance().getConfig().getInt("Settings.Snow.Amount");
    }

    public int getRadius() {
        return xLobby.getInstance().getConfig().getInt("Settings.Snow.Radius");
    }

}
