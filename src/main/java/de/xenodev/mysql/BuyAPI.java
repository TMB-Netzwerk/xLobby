package de.xenodev.mysql;

import de.xenodev.xLobby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BuyAPI {

    private static boolean playerExists(UUID uuid){

        try{
            ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Buy WHERE UUID= '" + uuid + "'");
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
            xLobby.getMySQL().update("INSERT INTO Buy(UUID,enterhaken,flugstab,eggbomb,enderperl,switchbow,notetrail,hearttrail,ghosttrail,flametrail,colortrail) VALUES ('" + uuid + "', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false');");
        }
    }


    public static String getBuy(UUID uuid, String buyName){
        String i = "";

        if(playerExists(uuid)){
            try{
                ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Buy WHERE UUID= '" + uuid + "'");
                if((!rs.next()) || (String.valueOf(rs.getString(buyName.toLowerCase())) == null));
                i = rs.getString(buyName.toLowerCase());
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getBuy(uuid, buyName);
        }

        return i;
    }

    public static void setBuy(UUID uuid, String buyName, Boolean buyBool){
        if(playerExists(uuid)){
            xLobby.getMySQL().update("UPDATE Buy SET " + buyName.toLowerCase() + "= '" + buyBool + "' WHERE UUID= '" + uuid + "';");
        }else{
            createPlayer(uuid);
            setBuy(uuid, buyName, buyBool);
        }
    }
}
