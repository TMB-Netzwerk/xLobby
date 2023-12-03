package de.xenodev.mysql;

import de.xenodev.xLobby;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BuyAPI {

    private static boolean playerExists(UUID uuid) {

        try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Buy WHERE UUID= '" + uuid + "'");

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
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Buy(UUID,enterhaken,flugstab,eggbomb,enderperl,switchbow,notetrail,hearttrail,ghosttrail,flametrail,colortrail) VALUES ('" + uuid + "', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false');");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    public static String getBuy(UUID uuid, String buyName){
        if(playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Buy WHERE UUID= '" + uuid + "'");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (String.valueOf(rs.getString(buyName.toLowerCase())) == null));
                return rs.getString(buyName.toLowerCase());
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getBuy(uuid, buyName);
        }
        return null;
    }

    public static void setBuy(UUID uuid, String buyName, Boolean buyBool){
        if(playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Buy SET " + buyName.toLowerCase() + "= '" + buyBool + "' WHERE UUID= '" + uuid + "';");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            setBuy(uuid, buyName, buyBool);
        }
    }
}
