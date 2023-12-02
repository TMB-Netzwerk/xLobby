package de.xenodev.mysql;

import de.xenodev.xLobby;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventAPI {

    private static boolean eventExists(){

        try{
            ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Event");
            if(rs.next()){
                return rs.getString("NAME") != null;
            }
            return false;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public static void createEvent(){
        if(!(eventExists())){
            xLobby.getMySQL().update("INSERT INTO Event(NAME) VALUES ('Nothing');");
        }
    }


    public static String getEvent(){
        String i = null;

        if(eventExists()){
            try{
                ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Event");
                if((!rs.next()) || (String.valueOf(rs.getString("NAME")) == null));
                i = rs.getString("NAME");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createEvent();
            getEvent();
        }

        return i;
    }

    public static void setEvent(String name){
        if(eventExists()){
            xLobby.getMySQL().update("UPDATE Event SET NAME= '" + name + "';");
        }else{
            createEvent();
            setEvent(name);
        }
    }
}
