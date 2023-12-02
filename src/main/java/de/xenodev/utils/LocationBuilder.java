package de.xenodev.utils;

import de.xenodev.xLobby;
import org.bukkit.Location;

public class LocationBuilder {

    public static Boolean existsLocation(String locationName){
        return xLobby.getInstance().getConfig().get("Locations." + locationName) != null;
    }

    public static void setLocation(String locationName, Location location){
        xLobby.getInstance().getConfig().set("Locations." + locationName, location);
        xLobby.getInstance().saveConfig();
    }

    public static Location getLocation(String locationName){
        if(existsLocation(locationName)){
            return xLobby.getInstance().getConfig().getLocation("Locations." + locationName);
        }else{
            return null;
        }
    }

}
