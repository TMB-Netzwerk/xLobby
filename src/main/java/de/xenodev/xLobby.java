package de.xenodev;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.particle.ParticleList_1_13;
import com.github.fierioziy.particlenativeapi.api.particle.ParticleList_1_19_Part;
import com.github.fierioziy.particlenativeapi.api.particle.ParticleList_1_8;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCore;
import com.github.fierioziy.particlenativeapi.plugin.ParticleNativePlugin;
import de.xenodev.commands.*;
import de.xenodev.events.DefaultChangesEvent;
import de.xenodev.events.DoubleJumpEvent;
import de.xenodev.events.JoinEvent;
import de.xenodev.events.QuitEvent;
import de.xenodev.events.gadget.EnderperleEvent;
import de.xenodev.events.gadget.EnterhakenEvent;
import de.xenodev.events.gadget.FlugstabEvent;
import de.xenodev.events.gadget.special.EggBombEvent;
import de.xenodev.events.gadget.special.SwitchBow;
import de.xenodev.events.main.*;
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
import java.util.logging.Level;

public class xLobby extends JavaPlugin {

    private static xLobby instance;
    private static MySQL mySQL;

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

        init(Bukkit.getPluginManager());

        BoardBuilder.updateScoreboard();
        CooldownBuilder.handlePlayerCooldown();
        if(EventAPI.getEvent().equalsIgnoreCase("Christmas")) {
            new StarterpackHandler();
        }
    }

    @Override
    public void onDisable() {
        mySQL.close();
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

        getCommand("test").setExecutor(new TestCommand());
        getCommand("location").setExecutor(new LocationCommand());
        getCommand("sqladmin").setExecutor(new SQLAdminCommand());
        getCommand("event").setExecutor(new EventCommand());

        getCommand("location").setTabCompleter(new LocationCommand());
        getCommand("sqladmin").setTabCompleter(new SQLAdminCommand());
        getCommand("event").setTabCompleter(new EventCommand());

        LotteryAPI.createLottery("tiny");
        LotteryAPI.createLottery("small");
        LotteryAPI.createLottery("medium");
        LotteryAPI.createLottery("good");
        LotteryAPI.createLottery("big");
        LotteryAPI.createLottery("hyper");
        LotteryAPI.createLottery("open");
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
        mySQL.update("CREATE TABLE IF NOT EXISTS Time(UUID VARCHAR(100),HOURS BIGINT,MINUTES INT,SECONDS INT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS Coins(UUID VARCHAR(100),COINS BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS Lottery(NAME VARCHAR(100),USES BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS Bytes(UUID VARCHAR(100),BYTES BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS Tickets(UUID VARCHAR(100),TICKETS BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS Reward(UUID VARCHAR(100),TIME BIGINT, STREAK BIGINT)");
        mySQL.update("CREATE TABLE IF NOT EXISTS Event(NAME VARCHAR(100))");

        mySQL.update("CREATE TABLE IF NOT EXISTS Setting(UUID VARCHAR(100),enterhaken VARCHAR(5),flugstab VARCHAR(5),eggbomb VARCHAR(5),enderperl VARCHAR(5),switchbow VARCHAR(5),notetrail VARCHAR(5),hearttrail VARCHAR(5),ghosttrail VARCHAR(5),flametrail VARCHAR(5),colortrail VARCHAR(5),eggboost_self VARCHAR(5),eggboost_other VARCHAR(5),hide VARCHAR(5),snowfall VARCHAR(5), traileffect VARCHAR(5))");
        mySQL.update("CREATE TABLE IF NOT EXISTS Buy(UUID VARCHAR(100),enterhaken VARCHAR(5),flugstab VARCHAR(5),eggbomb VARCHAR(5),enderperl VARCHAR(5),switchbow VARCHAR(5),notetrail VARCHAR(5),hearttrail VARCHAR(5),ghosttrail VARCHAR(5),flametrail VARCHAR(5),colortrail VARCHAR(5))");
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
