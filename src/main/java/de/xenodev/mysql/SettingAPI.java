package de.xenodev.mysql;

import de.xenodev.xLobby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SettingAPI {

    private static boolean playerExists(UUID uuid){

        try{
            ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Setting WHERE UUID= '" + uuid + "'");
            if(rs.next()){
                return rs.getString("UUID") != null;
            }
            return false;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public static void createPlayer(UUID uuid){
        if(!(playerExists(uuid))){
            xLobby.getMySQL().update("INSERT INTO Setting(UUID,enterhaken,flugstab,eggbomb,enderperl,switchbow,notetrail,hearttrail,ghosttrail,flametrail,colortrail,eggboost_self,eggboost_other,hide,snowfall,traileffect) VALUES ('" + uuid + "', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false');");
        }
    }


    public static String getSetting(UUID uuid, String settingName){
        String i = "";

        if(playerExists(uuid)){
            try{
                ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Setting WHERE UUID= '" + uuid + "'");
                if((!rs.next()) || (String.valueOf(rs.getString(settingName.toLowerCase())) == null));
                i = rs.getString(settingName.toLowerCase());
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getSetting(uuid, settingName);
        }

        return i;
    }

    public static void setSetting(UUID uuid, String settingName, Boolean settingBool){
        if(playerExists(uuid)){
            xLobby.getMySQL().update("UPDATE Setting SET " + settingName.toLowerCase() + "= '" + settingBool + "' WHERE UUID= '" + uuid + "';");
        }else{
            createPlayer(uuid);
            setSetting(uuid, settingName, settingBool);
        }
    }
}
