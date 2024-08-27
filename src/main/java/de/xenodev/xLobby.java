package de.xenodev;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.particle.ParticleList_1_13;
import com.github.fierioziy.particlenativeapi.api.particle.ParticleList_1_19_Part;
import com.github.fierioziy.particlenativeapi.api.particle.ParticleList_1_8;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCore;
import com.github.fierioziy.particlenativeapi.plugin.ParticleNativePlugin;
import de.xenodev.commands.*;
import de.xenodev.events.*;
import de.xenodev.events.gadget.EnderperleEvent;
import de.xenodev.events.gadget.EnterhakenEvent;
import de.xenodev.events.gadget.FlugstabEvent;
import de.xenodev.events.gadget.special.EggBombEvent;
import de.xenodev.events.gadget.special.SwitchBow;
import de.xenodev.events.main.*;
import de.xenodev.events.shop.ShopEvent;
import de.xenodev.mysql.EventAPI;
import de.xenodev.mysql.LotteryAPI;
import de.xenodev.mysql.MySQL;
import de.xenodev.utils.BoardBuilder;
import de.xenodev.utils.CooldownBuilder;
import de.xenodev.utils.snow.StarterpackHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class xLobby extends JavaPlugin {

    private static xLobby instance;
    private static MySQL mySQL;

    public static boolean xLobbyModule_Games = false;

    public static ParticleList_1_8 list18;
    public static ParticleList_1_13 list113;
    public static ParticleList_1_19_Part list119;

    @Override
    public void onEnable() {
        instance = this;

        if(!new File("plugins/" + getName(), "config.yml").exists()){
            saveDefaultConfig();
        }

        checkMySQL();
        checkParticleAPI();
        checkNoteBlockAPI();

        if(xLobby.getInstance().getServer().getPluginManager().getPlugin("xLobbyGames") != null){
            xLobbyModule_Games = true;
            getLogger().log(Level.INFO, "Das xLobbyGames-Module wurde gefunden und initialisiert.");
        }else{
            xLobbyModule_Games = false;
            getLogger().log(Level.SEVERE, "Das xLobbyGames-Module wurde nicht gefunden.");
        }

        init(Bukkit.getPluginManager());

        BoardBuilder.updateScoreboard();
        CooldownBuilder.handlePlayerCooldown();
        CooldownBuilder.handleBorderCheck();
        if(EventAPI.getEvent().equalsIgnoreCase("Christmas")) {
            new StarterpackHandler();
        }
    }

    @Override
    public void onDisable() {
        mySQL.closeDatabaseConnectionPool();
    }

    private void init(PluginManager pluginManager){
        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new QuitEvent(), this);
        pluginManager.registerEvents(new GadgetEvent(), this);
        pluginManager.registerEvents(new NavigatorEvent(), this);
        pluginManager.registerEvents(new ProfilEvent(), this);
        pluginManager.registerEvents(new SettingsEvent(), this);
        pluginManager.registerEvents(new ShopEvent(), this);
        pluginManager.registerEvents(new TrailEvent(), this);
        pluginManager.registerEvents(new DefaultChangesEvent(), this);
        pluginManager.registerEvents(new DoubleJumpEvent(), this);
        pluginManager.registerEvents(new EggBombEvent(), this);
        pluginManager.registerEvents(new SwitchBow(), this);
        pluginManager.registerEvents(new EnderperleEvent(), this);
        pluginManager.registerEvents(new EnterhakenEvent(), this);
        pluginManager.registerEvents(new FlugstabEvent(), this);
        pluginManager.registerEvents(new LotteryEvent(), this);
        pluginManager.registerEvents(new SQLAdminCommand(), this);
        pluginManager.registerEvents(new CalendarEvent(), this);

        getCommand("test").setExecutor(new TestCommand());
        getCommand("location").setExecutor(new LocationCommand());
        getCommand("sqladmin").setExecutor(new SQLAdminCommand());
        getCommand("event").setExecutor(new EventCommand());
        getCommand("redeem").setExecutor(new RedeemCommand());

        getCommand("location").setTabCompleter(new LocationCommand());
        getCommand("sqladmin").setTabCompleter(new SQLAdminCommand());
        getCommand("event").setTabCompleter(new EventCommand());
        getCommand("redeem").setTabCompleter(new RedeemCommand());

        LotteryAPI.createLottery("tiny", 0.0);
        LotteryAPI.createLottery("small", 0.1);
        LotteryAPI.createLottery("medium", 0.05);
        LotteryAPI.createLottery("good", 0.01);
        LotteryAPI.createLottery("big", 0.001);
        LotteryAPI.createLottery("hyper", 0.0001);
        LotteryAPI.createLottery("open", 0.0);
        EventAPI.createEvent();
    }

    public static String getPrefix() {
        return getInstance().getConfig().getString("Settings.Chatprefix").replace("&", "§");
    }

    public static xLobby getInstance(){
        return instance;
    }

    public static MySQL getMySQL() {
        return mySQL;
    }

    private void checkMySQL(){
        mySQL = new MySQL(getConfig().getString("MySQL.Host"), getConfig().getString("MySQL.Database"), getConfig().getString("MySQL.Username"), getConfig().getString("MySQL.Password"));
        try (Connection connection = xLobby.getMySQL().dataSource.getConnection()) {
            PreparedStatement preparedStatement1_1 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Players(UUID VARCHAR(100),NAME VARCHAR(100),TIME BIGINT,COINS BIGINT,BYTES BIGINT,TICKETS BIGINT,FIRST_JOIN VARCHAR(20),JOINS BIGINT,REWARD_TIME BIGINT,REWARD_STREAK BIGINT,COLOR VARCHAR(25))");
            PreparedStatement preparedStatement1_2 = connection.prepareStatement("ALTER TABLE Players ADD PRIMARY KEY (UUID)");

            PreparedStatement preparedStatement2_1 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Buy(UUID VARCHAR(100),enterhaken VARCHAR(5),flugstab VARCHAR(5),eggbomb VARCHAR(5),enderperl VARCHAR(5),switchbow VARCHAR(5),notetrail VARCHAR(5),hearttrail VARCHAR(5),ghosttrail VARCHAR(5),flametrail VARCHAR(5),colortrail VARCHAR(5),christmastrail VARCHAR(5))");
            PreparedStatement preparedStatement2_2 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Setting(UUID VARCHAR(100),enterhaken VARCHAR(5),flugstab VARCHAR(5),eggbomb VARCHAR(5),enderperl VARCHAR(5),switchbow VARCHAR(5),notetrail VARCHAR(5),hearttrail VARCHAR(5),ghosttrail VARCHAR(5),flametrail VARCHAR(5),colortrail VARCHAR(5),eggboost_self VARCHAR(5),eggboost_other VARCHAR(5),hide VARCHAR(5),snowfall VARCHAR(5), traileffect VARCHAR(5),christmastrail VARCHAR(5))");
            PreparedStatement preparedStatement2_3 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Redeem(CODE VARCHAR(25),TYPE VARCHAR(10), AMOUNT BIGINT)");
            PreparedStatement preparedStatement2_4 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Calendar(UUID VARCHAR(100),day1 VARCHAR(5),day2 VARCHAR(5),day3 VARCHAR(5),day4 VARCHAR(5),day5 VARCHAR(5),day6 VARCHAR(5),day7 VARCHAR(5),day8 VARCHAR(5),day9 VARCHAR(5),day10 VARCHAR(5),day11 VARCHAR(5),day12 VARCHAR(5),day13 VARCHAR(5),day14 VARCHAR(5),day15 VARCHAR(5),day16 VARCHAR(5),day17 VARCHAR(5),day18 VARCHAR(5),day19 VARCHAR(5),day20 VARCHAR(5),day21 VARCHAR(5),day22 VARCHAR(5),day23 VARCHAR(5),day24 VARCHAR(5))");

            PreparedStatement preparedStatement3_1 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Lottery(NAME VARCHAR(100),USES BIGINT, PERCENT DOUBLE)");
            PreparedStatement preparedStatement3_2 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Event(NAME VARCHAR(100))");

            preparedStatement1_1.execute();
            preparedStatement1_2.execute();
            preparedStatement2_1.execute();
            preparedStatement2_2.execute();
            preparedStatement2_3.execute();
            preparedStatement2_4.execute();
            preparedStatement3_1.execute();
            preparedStatement3_2.execute();

            preparedStatement1_1.close();
            preparedStatement1_2.close();
            preparedStatement2_1.close();
            preparedStatement2_2.close();
            preparedStatement2_3.close();
            preparedStatement2_4.close();
            preparedStatement3_1.close();
            preparedStatement3_2.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    private void checkParticleAPI(){
        Plugin plugin = this.getServer().getPluginManager().getPlugin("ParticleNativeAPI");
        if(plugin != null){
            if(!ParticleNativePlugin.isValid()){
                getLogger().log(Level.SEVERE, "Das Plugin ParticleNativeAPI konnte nicht geladen werden!");
                this.setEnabled(false);
                return;
            }

            ParticleNativeAPI particleAPI = ParticleNativeCore.loadAPI(this);
            list18 = particleAPI.LIST_1_8;
            list113 = particleAPI.LIST_1_13;
            list119 = particleAPI.LIST_1_19_PART;
        }else{
            getLogger().log(Level.SEVERE, "Das Plugin ParticleNativeAPI konnte nicht gefunden werden!");
            this.setEnabled(false);
        }
    }
    private void checkNoteBlockAPI(){
        Plugin plugin = this.getServer().getPluginManager().getPlugin("NoteBlockAPI");
        if(plugin == null){
            getLogger().log(Level.SEVERE, "Das Plugin NoteBlockAPI konnte nicht gefunden werden!");
            this.setEnabled(false);
        }
    }
}
