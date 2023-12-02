package de.xenodev.mysql;

import de.xenodev.xLobby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LotteryAPI {

    private static boolean lotteryExists(String name){

        try{
            ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Lottery WHERE NAME= '" + name + "'");
            if(rs.next()){
                return rs.getString("NAME") != null;
            }
            return false;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public static void createLottery(String name){
        if(!(lotteryExists(name))){
            xLobby.getMySQL().update("INSERT INTO Lottery(NAME, USES) VALUES ('" + name + "', '0');");
        }
    }


    public static Integer getLottery(String name){
        Integer i = 0;

        if(lotteryExists(name)){
            try{
                ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Lottery WHERE NAME= '" + name + "'");
                if((!rs.next()) || (Integer.valueOf(rs.getInt("USES")) == null));
                i = rs.getInt("USES");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createLottery(name);
            getLottery(name);
        }

        return i;
    }

    public static void setLottery(String name, Integer uses){
        if(lotteryExists(name)){
            xLobby.getMySQL().update("UPDATE Lottery SET USES= '" + uses + "' WHERE NAME= '" + name + "';");
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
