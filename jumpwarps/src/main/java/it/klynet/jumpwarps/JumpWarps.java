package it.klynet.jumpwarps;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import it.klynet.jumpwarps.warphandling.JumpWarpManager;
import it.klynet.klynetcore.KlyNetCore;
import it.klynet.klynetcore.api.registry.KlyNetNamespaceKeys;
import it.klynet.klynetcore.cmd.bukkit.BukkitCommandManager;
import it.klynet.klynetcore.plugindata.DataType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public final class JumpWarps extends JavaPlugin implements PluginMessageListener {

    private final JumpWarpManager jumpWarpManager = new JumpWarpManager(this);
    // Additional data keys

    @Override
    public void onEnable() {
        getLogger().info("[JumpWarps] JumpWarps has been enabled!");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        saveDefaultConfig();
        BukkitCommandManager<CommandSender> manager = BukkitCommandManager.create(this);
        manager.registerCommand(new JumpWarpCommands(this));
        Bukkit.getPluginManager().registerEvents(new JumpListener(this), this);

        jumpWarpManager.loadJumpWarps();
        KlyNetCore.PLAYER_DATA_REGISTRY.registerPluginData(KlyNetNamespaceKeys.JUMPWARPS_USED, DataType.INT, 0);
    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("JumpWarpPlayer")) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
        }
    }

    public JumpWarpManager getJumpWarpManager() {
        return jumpWarpManager;
    }
    public KlyNetCore getKlyNetCore() {
        return KlyNetCore.getInstance();
    }

    static class Key {
        public static String JUMPWARPS_USED = "jumpwarps_used";
    }
}
