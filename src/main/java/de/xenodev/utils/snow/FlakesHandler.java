package de.xenodev.utils.snow;

import de.xenodev.mysql.EventAPI;
import de.xenodev.mysql.SettingAPI;
import de.xenodev.xLobby;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class FlakesHandler {
    private int version = Integer.valueOf(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].split("_")[1]);
    private ConfigHandler config;
    float radius;
    int amount;
    boolean realistic;
    Random random;

    public FlakesHandler(ConfigHandler config) {
        this.config = config;
        this.radius = config.getRadius();
        this.amount = config.getAmount() / 4;
        this.realistic = config.isRealistic();
        this.createFlakes();
    }

    private void createFlakes() {
        this.random = new Random();
        new BukkitRunnable(){
            public void run() {
                if(EventAPI.getEvent().equalsIgnoreCase("Christmas")) {
                    for (int i = 0; i < FlakesHandler.this.amount; ++i) {
                        float xAdditive = (FlakesHandler.this.random.nextFloat() - 0.5f) * FlakesHandler.this.radius * 2.0f;
                        float max = (float) Math.sqrt(FlakesHandler.this.radius * FlakesHandler.this.radius - xAdditive * xAdditive) * 2.0f;
                        float yAdditive = (FlakesHandler.this.random.nextFloat() - 0.5f) * max;
                        float zAdditive = (FlakesHandler.this.random.nextFloat() - 0.5f) * max;
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            Location playerLoc = player.getLocation();
                            Location loc = new Location(player.getWorld(), playerLoc.getX() + (double) xAdditive, playerLoc.getY() + (double) yAdditive, playerLoc.getZ() + (double) zAdditive);
                            if (!FlakesHandler.this.realistic || (double) loc.getWorld().getHighestBlockYAt(loc) < loc.getY()) {
                                try {
                                    if (SettingAPI.getSetting(player.getUniqueId(), "Snowfall").equals("true")) {
                                        if (13 <= FlakesHandler.this.version) {
                                            FlakesHandler.this.sendParticles((Player) player, loc.getX(), loc.getY(), loc.getZ(), 0.0, 10);
                                        } else {
                                            FlakesHandler.this.sendParticles((Player) player, "FIREWORKS_SPARK", (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), new int[]{1});
                                        }
                                    }
                                } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException |
                                         InstantiationException | NoSuchFieldException | NoSuchMethodException |
                                         SecurityException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        }.runTaskTimerAsynchronously(xLobby.getInstance(), 0L, 10L);
    }

    private Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
        String version = String.valueOf(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]) + ".";
        String name = "net.minecraft.server." + version + nmsClassString;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }

    private Class<?> getCraftPlayerClass() throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        String name = "org.bukkit.craftbukkit." + version + ".entity.CraftPlayer";
        Class<?> nmsClass = Class.forName(name.replace("/", "."));
        return nmsClass;
    }

    private Object getConnection(Player player) throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method getHandle = player.getClass().getMethod("getHandle", new Class[0]);
        Object nmsPlayer = getHandle.invoke((Object)player, new Object[0]);
        Field conField = nmsPlayer.getClass().getField("playerConnection");
        Object con = conField.get(nmsPlayer);
        return con;
    }

    public void sendParticles(Player player, double x, double y, double z, double data, int amount) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Class<?> cpClass = this.getCraftPlayerClass();
        Class<?> particleClass = Class.forName("org.bukkit.Particle");
        Method valueOf = particleClass.getMethod("valueOf", String.class);
        Method spawnParticle = cpClass.getMethod("spawnParticle", particleClass, Double.TYPE, Double.TYPE, Double.TYPE, Integer.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE);
        spawnParticle.invoke(cpClass.cast((Object)player), valueOf.invoke(particleClass, "FIREWORKS_SPARK"), x, y, z, amount, 0.0, 0.0, 0.0, data);
    }

    public void sendParticles(Player player, String particle, float x, float y, float z, int[] amount) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Class<?> packetClass = this.getNMSClass("PacketPlayOutWorldParticles");
        Class<?> particleClass = this.getNMSClass("EnumParticle");
        Constructor<?> packetConstructor = packetClass.getConstructors()[1];
        Method valueOf = particleClass.getMethod("valueOf", String.class);
        Object packet = packetConstructor.newInstance(valueOf.invoke(particleClass, "FIREWORKS_SPARK"), true, Float.valueOf(x), Float.valueOf(y), Float.valueOf(z), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), 0, amount);
        Method sendPacket = this.getNMSClass("PlayerConnection").getMethod("sendPacket", this.getNMSClass("Packet"));
        sendPacket.invoke(this.getConnection(player), packet);
    }
}
