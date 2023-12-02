package de.xenodev.mysql;

import de.xenodev.xLobby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RewardAPI {

    private static boolean playerExists(UUID uuid){

        try{
            ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Reward WHERE UUID= '" + uuid + "'");
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
            xLobby.getMySQL().update("INSERT INTO Reward(UUID, TIME, STREAK) VALUES ('" + uuid + "', '0', '0');");
        }
    }


    public static Long getTime(UUID uuid){
        long i = 0;

        if(playerExists(uuid)){
            try{
                ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Reward WHERE UUID= '" + uuid + "'");
                if((!rs.next()) || (Long.valueOf(rs.getLong("TIME")) == null));
                i = rs.getLong("TIME");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getTime(uuid);
        }

        return i;
    }

    public static void setTime(UUID uuid){
        long time = System.currentTimeMillis()+86400000;
        if(playerExists(uuid)){
            xLobby.getMySQL().update("UPDATE Reward SET TIME= '" + time + "' WHERE UUID= '" + uuid + "';");
        }else{
            createPlayer(uuid);
            setTime(uuid);
        }
    }

    public static Integer getStreak(UUID uuid){
        Integer i = 0;

        if(playerExists(uuid)){
            try{
                ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Reward WHERE UUID= '" + uuid + "'");
                if((!rs.next()) || (Integer.valueOf(rs.getInt("STREAK")) == null));
                i = rs.getInt("STREAK");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getStreak(uuid);
        }

        return i;
    }

    public static void setStreak(UUID uuid, Integer streak){
        if(playerExists(uuid)){
            xLobby.getMySQL().update("UPDATE Reward SET STREAK= '" + streak + "' WHERE UUID= '" + uuid + "';");
        }else{
            createPlayer(uuid);
            setStreak(uuid, streak);
        }
    }

    public static void addStreak(UUID uuid, Integer streak){
        if(playerExists(uuid)){
            setStreak(uuid, Integer.valueOf(getStreak(uuid).intValue() + streak.intValue()));
        }else{
            createPlayer(uuid);
            addStreak(uuid, streak);
        }
    }

    public static String remainingTime(long time){
        long seconds = time/1000;
        long minutes = 0;
        while(seconds > 60) {
            seconds-=60;
            minutes++;
        }
        long hours = 0;
        while(minutes > 60) {
            minutes-=60;
            hours++;
        }
        return "§2" + hours + ":" + minutes + ":" + seconds;
    }

    public static Boolean allowReward(UUID uuid){
        long current = System.currentTimeMillis();
        long time = getTime(uuid);
        return current > time;
    }
}
