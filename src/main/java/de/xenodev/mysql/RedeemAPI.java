package de.xenodev.mysql;

import de.xenodev.xLobby;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RedeemAPI {

    private static boolean eventRedeem(String code){

        try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Redeem WHERE `CODE` = ?");
            preparedStatement.setString(1, code);

            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void createEvent(Player player, String code, String type, Integer amount){
        if(!eventRedeem(code)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Redeem(CODE, TYPE, AMOUNT) VALUES ('" + code + "', '" + type + "', '" + amount + "');");
                preparedStatement.execute();
                preparedStatement.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            player.sendMessage(xLobby.getPrefix() + "§7Dieser Code existiert bereits");
        }
    }


    public static void getRedeem(Player player, String code){
        if(eventRedeem(code)){
            try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Redeem WHERE CODE = '" + code + "'");

                ResultSet rs = preparedStatement.executeQuery();
                String type = null;
                Integer amount = null;
                if(rs.next()) {
                    type = String.valueOf(rs.getString("TYPE"));
                    amount = Integer.valueOf(rs.getInt("AMOUNT"));
                }
                preparedStatement.close();
                player.sendMessage(xLobby.getPrefix() + "§7Du hast den Code eingelöst!");
                if(type.equalsIgnoreCase("bytes")){
                    PlayersAPI.addBytes(player.getUniqueId(), amount);
                    player.sendMessage(xLobby.getPrefix() + "§6" + amount + " §7Bytes erhalten");
                }else if(type.equalsIgnoreCase("coins")){
                    PlayersAPI.addCoins(player.getUniqueId(), amount);
                    player.sendMessage(xLobby.getPrefix() + "§6" + amount + " §7Coins erhalten");
                }else if(type.equalsIgnoreCase("tickets")){
                    PlayersAPI.addTickets(player.getUniqueId(), amount);
                    player.sendMessage(xLobby.getPrefix() + "§6" + amount + " §7Tickets erhalten");
                }
                PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM Redeem WHERE CODE = '" + code + "'");
                preparedStatement1.execute();
                preparedStatement1.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }else{
            player.sendMessage(xLobby.getPrefix() + "§7Dieser Code existiert nicht");
        }
    }
}
