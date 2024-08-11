package de.xenodev.mysql;

import de.xenodev.xLobby;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayersAPI {

    private static boolean playerExists(UUID uuid){

        try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Players WHERE UUID= '" + uuid + "'");

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
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Players(UUID, COLOR) VALUES ('" + uuid + "', 'GRAY');");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    public static String getColor(UUID uuid){
        if(playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Players WHERE UUID= '" + uuid + "'");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (String.valueOf(rs.getString("COLOR")) == null));
                return rs.getString("COLOR");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getColor(uuid);
        }
        return null;
    }

    public static void setColor(UUID uuid, String color){
        if(playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `Players` SET `COLOR` = ? WHERE `UUID` = ?;");
                preparedStatement.setString(1, color);
                preparedStatement.setString(2, uuid.toString());
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            setColor(uuid, color);
        }
    }
}
