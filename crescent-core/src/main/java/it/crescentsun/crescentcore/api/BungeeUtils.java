package it.crescentsun.crescentcore.api;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import it.crescentsun.crescentcore.api.data.player.PlayerData;
import it.crescentsun.crescentcore.CrescentCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnusedReturnValue")
public class BungeeUtils  {

    /**
     * Saves the player's data to the database, removes the data from the plugin's hashmap,
     * and sends the player to the specified target server.
     *
     * @param crescentCore       The CrescentCore instance.
     * @param sendingPlugin    The JavaPlugin instance responsible for sending the player.
     * @param player           The Player to be sent to the target server.
     * @param server           The target server's name.
     * @return A CompletableFuture<Boolean> that completes with true if the player's data is
     *         saved and the player is sent to the target server without any exceptions; false otherwise.
     */
    public static CompletableFuture<Boolean> saveDataAndSendPlayerToServer(
            CrescentCore crescentCore, JavaPlugin sendingPlugin, Player player, String server) {
        UUID uuid = player.getUniqueId();
        CompletableFuture<PlayerData> playerDataFut = crescentCore.getPlayerDataManager().asyncSaveData(uuid);
        return playerDataFut.thenApplyAsync(playerData -> {
            if (playerData == null) {
                return false;
            }
            crescentCore.getPlayerDataManager().removeData(uuid);
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                out.writeUTF("Connect");
                out.writeUTF(server);
                player.sendPluginMessage(sendingPlugin, "BungeeCord", b.toByteArray());
                b.close();
                out.close();
                return true;
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Error when trying to connect to " + server);
                e.printStackTrace();
                return false;
            }
        });
    }

    /**
     * Sends a message to the BungeeCord server to get the name of the server the player is on.
     *
     * @param player The player to get the server name from.
     */
    public static void sendGetServerMessage() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServer");
        Bukkit.getServer().sendPluginMessage(CrescentCore.getInstance(), "BungeeCord", out.toByteArray());
    }
}
