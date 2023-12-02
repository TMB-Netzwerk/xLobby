package de.xenodev.mysql;

import de.xenodev.xLobby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TicketAPI {

    private static boolean playerExists(UUID uuid){

        try{
            ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Tickets WHERE UUID= '" + uuid + "'");
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
            xLobby.getMySQL().update("INSERT INTO Tickets(UUID, TICKETS) VALUES ('" + uuid + "', '0');");
        }
    }


    public static Integer getTickets(UUID uuid){
        Integer i = 0;

        if(playerExists(uuid)){
            try{
                ResultSet rs = xLobby.getMySQL().query("SELECT * FROM Tickets WHERE UUID= '" + uuid + "'");
                if((!rs.next()) || (Integer.valueOf(rs.getInt("TICKETS")) == null));
                i = rs.getInt("TICKETS");
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            createPlayer(uuid);
            getTickets(uuid);
        }

        return i;
    }

    public static void setTickets(UUID uuid, Integer tickets){
        if(playerExists(uuid)){
            xLobby.getMySQL().update("UPDATE Tickets SET TICKETS= '" + tickets + "' WHERE UUID= '" + uuid + "';");
        }else{
            createPlayer(uuid);
            setTickets(uuid, tickets);
        }
    }

    public static void addTickets(UUID uuid, Integer tickets){
        if(playerExists(uuid)){
            setTickets(uuid, Integer.valueOf(getTickets(uuid).intValue() + tickets.intValue()));
        }else{
            createPlayer(uuid);
            addTickets(uuid, tickets);
        }
    }

    public static void removeTickets(UUID uuid, Integer tickets){
        if(playerExists(uuid)){
            setTickets(uuid, Integer.valueOf(getTickets(uuid).intValue() - tickets.intValue()));
        }else{
            createPlayer(uuid);
            removeTickets(uuid, tickets);
        }
    }
}
