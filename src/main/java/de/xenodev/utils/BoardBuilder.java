package de.xenodev.utils;

import de.xenodev.mysql.BytesAPI;
import de.xenodev.mysql.CoinAPI;
import de.xenodev.mysql.TimeAPI;
import de.xenodev.xLobby;
import eu.cloudnetservice.driver.inject.InjectionLayer;
import eu.cloudnetservice.driver.permission.PermissionGroup;
import eu.cloudnetservice.driver.permission.PermissionManagement;
import eu.cloudnetservice.driver.permission.PermissionUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class BoardBuilder {

    private static Integer displayID = 0;

    private static PermissionManagement permissionManagement = InjectionLayer.ext().instance(PermissionManagement.class);

    public static void createBoard(Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("main", "main", "§3§lT§beamMegaByte");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        PermissionUser permissionUser = permissionManagement.user(player.getUniqueId());
        PermissionGroup permissionGroup = permissionManagement.highestPermissionGroup(permissionUser);

        objective.getScore("§5§o").setScore(15);
        objective.getScore("§7» §fDein Rang").setScore(14);
        objective.getScore(updateTeam(scoreboard, "rang", permissionGroup.display().replace("&", "§") + permissionGroup.name(), "", ChatColor.BLUE)).setScore(13);
        objective.getScore("§1§o").setScore(12);
        objective.getScore("§7» §fDeine Zeit").setScore(11);
        objective.getScore(updateTeam(scoreboard, "time", "§2§l" + TimeAPI.changeTime(player.getUniqueId()), "", ChatColor.GREEN)).setScore(10);
        objective.getScore("§9§o").setScore(9);
        objective.getScore("§7» §fDeine Bytes").setScore(8);
        objective.getScore(updateTeam(scoreboard, "bytes", "§6§l" + BytesAPI.getBytes(player.getUniqueId()), "", ChatColor.GOLD)).setScore(7);
        objective.getScore("§6§o").setScore(6);
        objective.getScore("§7» §fDeine Coins").setScore(5);
        objective.getScore(updateTeam(scoreboard, "coins", "§e§l" + CoinAPI.getCoins(player.getUniqueId()), "", ChatColor.YELLOW)).setScore(4);
        objective.getScore("§7§o").setScore(3);
        objective.getScore("§7» §fUnsere Website").setScore(2);
        objective.getScore("§d§lclan-tmb.de").setScore(1);

        player.setScoreboard(scoreboard);
    }

    public static void updateScoreboard() {
        new BukkitRunnable(){

            @Override
            public void run() {
                for(final Player players : Bukkit.getOnlinePlayers()){
                    Scoreboard scoreboard = players.getScoreboard();
                    Objective objective = scoreboard.getObjective("main");

                    PermissionUser permissionUser = permissionManagement.user(players.getUniqueId());
                    PermissionGroup permissionGroup = permissionManagement.highestPermissionGroup(permissionUser);

                    objective.getScore(updateTeam(scoreboard, "rang", permissionGroup.display().replace("&", "§") + permissionGroup.name(), "", ChatColor.BLUE)).setScore(13);
                    objective.getScore(updateTeam(scoreboard, "time", "§2§l" + TimeAPI.changeTime(players.getUniqueId()), "", ChatColor.GREEN)).setScore(10);
                    objective.getScore(updateTeam(scoreboard, "bytes", "§6§l" + BytesAPI.getBytes(players.getUniqueId()), "", ChatColor.GOLD)).setScore(7);
                    objective.getScore(updateTeam(scoreboard, "coins", "§e§l" + CoinAPI.getCoins(players.getUniqueId()), "", ChatColor.YELLOW)).setScore(4);

                }
            }
        }.runTaskTimerAsynchronously(xLobby.getInstance(), 0, 20L*10);
    }

    public static void updateScoreboardDisplay(){
        new BukkitRunnable(){

            @Override
            public void run() {
                displayID++;
                for(final Player players : Bukkit.getOnlinePlayers()){
                    Scoreboard scoreboard = players.getScoreboard();
                    Objective objective = scoreboard.getObjective("main");

                    switch (displayID){
                        case 1:
                            objective.setDisplayName("§bT§3§le§bamMegaByte");
                            break;
                        case 2:
                            objective.setDisplayName("§bTe§3§la§bmMegaByte");
                            break;
                        case 3:
                            objective.setDisplayName("§bTea§3§lm§bMegaByte");
                            break;
                        case 4:
                            objective.setDisplayName("§bTeam§3§lM§begaByte");
                            break;
                        case 5:
                            objective.setDisplayName("§bTeamM§3§le§bgaByte");
                            break;
                        case 6:
                            objective.setDisplayName("§bTeamMe§3§lg§baByte");
                            break;
                        case 7:
                            objective.setDisplayName("§bTeamMeg§3§la§bByte");
                            break;
                        case 8:
                            objective.setDisplayName("§bTeamMega§3§lB§byte");
                            break;
                        case 9:
                            objective.setDisplayName("§bTeamMegaB§3§ly§bte");
                            break;
                        case 10:
                            objective.setDisplayName("§bTeamMegaBy§3§lt§be");
                            break;
                        case 11:
                            objective.setDisplayName("§bTeamMegaByt§3§le");
                            break;
                        case 12:
                            objective.setDisplayName("§3§lT§beamMegaByte");
                            displayID = 0;
                            break;
                    }
                }
            }
        }.runTaskTimerAsynchronously(xLobby.getInstance(), 0, 20L);
    }

    public static Team getTeam(Scoreboard board, String Team, String prefix, String suffix) {
        Team team = board.getTeam(Team);
        if(team == null) {
            team = board.registerNewTeam(Team);
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.setAllowFriendlyFire(false);
        team.setCanSeeFriendlyInvisibles(true);

        return team;
    }

    public static String updateTeam(Scoreboard board, String Team, String prefix, String suffix, ChatColor entry) {
        Team team = board.getTeam(Team);
        if(team == null) {
            team = board.registerNewTeam(Team);
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.addEntry(entry.toString());

        return entry.toString();
    }
}
