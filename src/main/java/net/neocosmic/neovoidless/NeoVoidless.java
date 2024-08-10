package net.neocosmic.neovoidless;

import com.google.common.collect.Maps;
import net.neocosmic.neovoidless.commands.ReloadCMD;
import net.neocosmic.neovoidless.info.Information;
import net.neocosmic.neovoidless.listeners.GeneralListener;
import net.neocosmic.neovoidless.teleport.TeleportType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class NeoVoidless extends JavaPlugin {
    private static NeoVoidless instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        loadPlugin();

        getServer().getPluginManager().registerEvents(new GeneralListener(), this);

        getCommand("voidlessreload").setExecutor(new ReloadCMD());

    }

    public void loadPlugin() {
        List<World> worlds = new ArrayList<>();

        for (String worldName : getConfig().getStringList("voidless-worlds")) {
            World world = getServer().getWorld(worldName);

            if (world == null) {
                getLogger().warning("World " + worldName + " not found!");
                continue;
            }

            worlds.add(world);
        }

        if (getConfig().getConfigurationSection("voidless-teleport-location") == null) {
            getLogger().warning("No voidless-teleport-location section found in config!, creating it now");

            getConfig().set("voidless-teleport-location.type", "specific");

            getConfig().set("voidless-teleport-location.specific.spawn", "spawn;0;100;0;0;0");

            getConfig().set("voidless-teleport-location.global", "%world%;0;100;0;0;0");
            saveConfig();
            return;
        }

        if (getConfig().getConfigurationSection("y-voidless-teleport-location") == null) {
            getLogger().warning("No y-voidless-teleport-location section found in config!, creating it now");

            getConfig().set("y-voidless-teleport-location.type", "specific");

            getConfig().set("y-voidless-teleport-location.specific.spawn", "-100");

            getConfig().set("y-voidless-teleport-location.global", "-100");
            saveConfig();
            return;
        }

        TeleportType teleportType = TeleportType.valueOf(getConfig().getString("voidless-teleport-location.type").toUpperCase());
        TeleportType yType = TeleportType.valueOf(getConfig().getString("y-voidless-teleport-location.type").toUpperCase());

        HashMap<World, Location> specificLocations = Maps.newHashMap();
        HashMap<World, Integer> specificY = Maps.newHashMap();

        getConfig().getConfigurationSection("voidless-teleport-location.specific").getKeys(false)
                .forEach(worldName -> {
                    World world = getServer().getWorld(worldName);

                    if (world == null) {
                        getLogger().warning("World " + worldName + " not found!");
                        return;
                    }

                    String[] location = getConfig().getString("voidless-teleport-location.specific." + worldName).split(";");
                    specificLocations.put(world, new Location(Bukkit.getWorld(location[0]), Double.parseDouble(location[1]), Double.parseDouble(location[2]), Double.parseDouble(location[3]), Float.parseFloat(location[4]), Float.parseFloat(location[5])));
                });

        getConfig().getConfigurationSection("y-voidless-teleport-location.specific").getKeys(false)
                .forEach(worldName -> {
                    World world = getServer().getWorld(worldName);

                    if (world == null) {
                        getLogger().warning("World " + worldName + " not found!");
                        return;
                    }

                    specificY.put(world, Integer.parseInt(getConfig().getString("y-voidless-teleport-location.specific." + worldName)));
                });

        String globalLocation = getConfig().getString("voidless-teleport-location.global");
        int globalY = getConfig().getInt("y-voidless-teleport-location.global");

        boolean messageEnabled = getConfig().getBoolean("message.enabled");
        String message = getConfig().getString("message.content");

        new Information(
                worlds,
                messageEnabled,
                message,
                teleportType,
                yType,
                specificLocations,
                specificY,
                globalLocation,
                globalY
        );

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static NeoVoidless getInstance() {
        return instance;
    }

}
