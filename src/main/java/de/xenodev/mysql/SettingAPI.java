package de.xenodev.mysql;

import de.xenodev.xLobby;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SettingAPI {

    private static boolean playerExists(UUID uuid){

        try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Setting WHERE UUID= '" + uuid + "'");

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void createPlayer(UUID uuid){
        if(!playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Setting(UUID,enterhaken,flugstab,eggbomb,enderperl,switchbow,notetrail,hearttrail,ghosttrail,flametrail,colortrail,eggboost_self,eggboost_other,hide,snowfall,traileffect) VALUES ('" + uuid + "', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false');");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    public static String getSetting(UUID uuid, String settingName){
        if(playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Setting WHERE UUID= '" + uuid + "'");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (String.valueOf(rs.getString(settingName.toLowerCase())) == null));
                return rs.getString(settingName.toLowerCase());
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getSetting(uuid, settingName);
        }
        return null;
    }

    public static void setSetting(UUID uuid, String settingName, Boolean settingBool){
        if(playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Setting SET " + settingName.toLowerCase() + "= '" + settingBool + "' WHERE UUID= '" + uuid + "';");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            setSetting(uuid, settingName, settingBool);
        }
    }
}
