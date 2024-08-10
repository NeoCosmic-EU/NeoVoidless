package net.neocosmic.neovoidless.info;

import net.neocosmic.neovoidless.teleport.TeleportType;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.List;

public class Information {

    public static List<World> WORLDS;
    public static boolean MESSAGE_ENABLED;
    public static String MESSAGE;
    public static TeleportType TELEPORT_TYPE;
    public static TeleportType Y_TELEPORT_TYPE;
    public static HashMap<World, Location> SPECIFIC_LOCATIONS;
    public static HashMap<World, Integer> SPECIFIC_Y;
    public static String GLOBAL_LOCATION;
    public static int Y_GLOBAL_LOCATION;

    public Information(List<World> worlds, boolean messageEnabled, String message, TeleportType teleportType, TeleportType yTeleportType, HashMap<World, Location> specificLocations, HashMap<World, Integer> specificY, String globalLocation, int yGlobalLocation) {
        WORLDS = worlds;
        MESSAGE_ENABLED = messageEnabled;
        MESSAGE = message;
        TELEPORT_TYPE = teleportType;
        Y_TELEPORT_TYPE = yTeleportType;
        SPECIFIC_LOCATIONS = specificLocations;
        SPECIFIC_Y = specificY;
        GLOBAL_LOCATION = globalLocation;
        Y_GLOBAL_LOCATION = yGlobalLocation;
    }

    public static boolean isValid(World world) {
        return world != null && WORLDS.contains(world);
    }

    public static Location getLocation(World world) {
        if (!WORLDS.contains(world)) return null;

        switch (TELEPORT_TYPE) {
            case SPECIFIC:
                return SPECIFIC_LOCATIONS.get(world);
            case GLOBAL:
                return parseLocation(GLOBAL_LOCATION.replace("%world%", world.getName()));
        }
        return null;
    }

    private static Location parseLocation(String locationString) {
        String[] parts = locationString.split(";");

        if (parts.length != 6) return null;

        return new Location(
                WORLDS.get(0),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]),
                Float.parseFloat(parts[4]),
                Float.parseFloat(parts[5])
        );
    }
}
