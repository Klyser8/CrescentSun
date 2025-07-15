package it.crescentsun.api.crescentcore.sound;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Represents a sound that can be played for a player or at a location.
 */
public interface PlayableSound {

    void playForPlayerAtTheirLocation(Player player);
    default void playForPlayersAtTheirLocations(Player... players) {
        for (Player player : players) {
            playForPlayerAtTheirLocation(player);
        }
    }
    void playForPlayerAtLocation(Player player, Location location);
    default void playForPlayersAtLocation(Player[] players, Location location) {
        for (Player player : players) {
            playForPlayerAtLocation(player, location);
        }
    }

    void playAtLocation(Location location);

}
