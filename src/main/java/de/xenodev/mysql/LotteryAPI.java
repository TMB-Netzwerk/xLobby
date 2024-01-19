package de.xenodev.mysql;

import de.xenodev.xLobby;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LotteryAPI {

    private static boolean lotteryExists(String name){

        try (Connection connection = xLobby.getMySQL().dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Lottery WHERE NAME= '" + name + "'");

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getString("NAME") != null;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public static void createLottery(String name, Double percent){
        if(!lotteryExists(name)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Lottery(NAME, USES, PERCENT) VALUES ('" + name + "', '0', '" + percent + "');");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }


    public static Integer getUses(String name){
        if(lotteryExists(name)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Lottery WHERE NAME = '" + name + "'");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (Integer.valueOf(rs.getInt("USES")) == null));
                return rs.getInt("USES");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createLottery(name, 0.01);
            getUses(name);
        }
        return null;
    }

    public static void setUses(String name, Integer uses){
        if(lotteryExists(name)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `Lottery` SET `USES` = ? WHERE `NAME` = ?;");
                preparedStatement.setInt(1, uses);
                preparedStatement.setString(2, name);
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createLottery(name, 0.01);
            setUses(name, uses);
        }
    }

    public static void addUses(String name, Integer uses){
        if(lotteryExists(name)){
            setUses(name, Integer.valueOf(getUses(name).intValue() + uses.intValue()));
        }else{
            createLottery(name, 0.01);
            addUses(name, uses);
        }
    }

    public static Double getPercent(String name){
        if(lotteryExists(name)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Lottery WHERE NAME = '" + name + "'");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (Double.valueOf(rs.getDouble("PERCENT")) == null));
                return rs.getDouble("PERCENT");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createLottery(name, 0.01);
            getPercent(name);
        }
        return null;
    }

    public static void setPercent(String name, Double percent){
        if(lotteryExists(name)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `Lottery` SET `PERCENT` = ? WHERE `NAME` = ?;");
                preparedStatement.setDouble(1, percent);
                preparedStatement.setString(2, name);
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createLottery(name, 0.01);
            setPercent(name, percent);
        }
    }

    public static void addPercent(String name, Double percent){
        if(lotteryExists(name)){
            setPercent(name, Double.valueOf(getPercent(name).doubleValue() + percent.doubleValue()));
        }else{
            createLottery(name, 0.01);
            addPercent(name, percent);
        }
    }
}
