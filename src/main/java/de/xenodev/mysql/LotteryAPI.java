package de.xenodev.mysql;

import de.xenodev.xLobby;

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

    public static void createLottery(String name){
        if(!lotteryExists(name)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Lottery(NAME, USES) VALUES ('" + name + "', '0');");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createLottery(name);
        }
    }


    public static Integer getLottery(String name){
        if(lotteryExists(name)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Event");

                ResultSet rs = preparedStatement.executeQuery();
                if((!rs.next()) || (Integer.valueOf(rs.getInt("USES")) == null));
                return rs.getInt("USES");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createLottery(name);
            getLottery(name);
        }
        return null;
    }

    public static void setLottery(String name, Integer uses){
        if(lotteryExists(name)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Lottery SET USES= '" + uses + "' WHERE NAME= '" + name + "';");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createLottery(name);
            setLottery(name, uses);
        }
    }

    public static void addLottery(String name, Integer uses){
        if(lotteryExists(name)){
            setLottery(name, Integer.valueOf(getLottery(name).intValue() + uses.intValue()));
        }else{
            createLottery(name);
            addLottery(name, uses);
        }
    }
}
