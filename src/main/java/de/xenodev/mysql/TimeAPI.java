package de.xenodev.mysql;

import de.xenodev.xLobby;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TimeAPI {

    private static boolean playerExists(UUID uuid){

        try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Time WHERE UUID= '" + uuid + "'");

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
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Time(UUID, HOURS, MINUTES, SECONDS) VALUES ('" + uuid + "', '0', '0', '0');");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
        }
    }

    public static Integer getHours(UUID uuid){
        if(playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Time WHERE UUID= '" + uuid + "'");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (Integer.valueOf(rs.getInt("HOURS")) == null));
                return rs.getInt("HOURS");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getHours(uuid);
        }
        return null;
    }

    public static Integer getMinutes(UUID uuid){
        if(playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Time WHERE UUID= '" + uuid + "'");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (Integer.valueOf(rs.getInt("MINUTES")) == null));
                return rs.getInt("MINUTES");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getMinutes(uuid);
        }
        return null;
    }

    public static Integer getSeconds(UUID uuid){
        if(playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Time WHERE UUID= '" + uuid + "'");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (Integer.valueOf(rs.getInt("SECONDS")) == null));
                return rs.getInt("SECONDS");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getSeconds(uuid);
        }
        return null;
    }

    public static void setHours(UUID uuid, Integer hours){
        if(playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Time SET HOURS= '" + hours + "' WHERE UUID= '" + uuid + "';");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            setHours(uuid, hours);
        }
    }

    public static void setMinutes(UUID uuid, Integer minutes){
        if(playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Time SET MINUTES= '" + minutes + "' WHERE UUID= '" + uuid + "';");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            setMinutes(uuid, minutes);
        }
    }

    public static void setSeconds(UUID uuid, Integer seconds){
        if(playerExists(uuid)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Time SET SECONDS= '" + seconds + "' WHERE UUID= '" + uuid + "';");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            setSeconds(uuid, seconds);
        }
    }

    public static void addHours(UUID uuid, Integer hours){
        if(playerExists(uuid)){
            setHours(uuid, Integer.valueOf(getHours(uuid).intValue() + hours.intValue()));
        }else{
            createPlayer(uuid);
            addHours(uuid, hours);
        }
    }

    public static void addMinutes(UUID uuid, Integer minutes){
        if(playerExists(uuid)){
            setMinutes(uuid, Integer.valueOf(getMinutes(uuid).intValue() + minutes.intValue()));
        }else{
            createPlayer(uuid);
            addMinutes(uuid, minutes);
        }
    }

    public static void addSeconds(UUID uuid, Integer seconds){
        if(playerExists(uuid)){
            setSeconds(uuid, Integer.valueOf(getSeconds(uuid).intValue() + seconds.intValue()));
        }else{
            createPlayer(uuid);
            addSeconds(uuid, seconds);
        }
    }

    public static String changeTime(UUID uuid){
        if(getHours(uuid) != 0){
            return getHours(uuid) + "h";
        }else if(getMinutes(uuid) >= 0 && getHours(uuid) == 0){
            return getMinutes(uuid) + " min";
        }
        return "kein Eintrag!";
    }
}
