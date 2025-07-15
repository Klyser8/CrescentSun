package it.crescentsun.crescentcore.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import it.crescentsun.api.crescentcore.util.BungeeUtils;
import it.crescentsun.crescentcore.CrescentCore;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//@SuppressWarnings("ALL")
@Command(value = "crescent", alias = "cs")
public class CrescentCoreCommands extends BaseCommand {

    private final CrescentCore crescentCore;

    public CrescentCoreCommands(CrescentCore crescentCore) {
        this.crescentCore = crescentCore;
    }

/*    @Default
    @Permission("crescent.crescentcore")
    public void defaultCommand(final CommandSender sender) {
        if (sender instanceof Player player) {
            sender.sendMessage(CrescentCoreLocalization.GENERIC_INCORRECT_COMMAND.getFormattedMessage(player.locale(), "/crescent help"));
        } else {
            sender.sendMessage(CrescentCoreLocalization.GENERIC_INCORRECT_COMMAND.getFormattedMessage(null, "/crescent help"));
        }
    }*/

    @SubCommand("switch")
    @Permission("crescent.crescentcore.switch")
    public void switchCommand(final CommandSender sender, String serverName) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Command can only be executed by a player.");
            return;
        }
        if (serverName == null || serverName.isEmpty()) {
            sender.sendMessage("Please specify a server name.");
            return;
        }
/*        if (serverName.equalsIgnoreCase(crescentCore.getServerName())) {
            sender.sendMessage("You are already connected to the server: " + serverName);
            return;
        }*/
        sender.sendMessage("Sending you to server: " + serverName);
        BungeeUtils.sendPlayerToServer(
                crescentCore, player, serverName);
        /*crescentCore.getPlayerDataManager().saveDataAsync(player.getUniqueId()).thenApplyAsync(playerData -> {
            if (playerData != null) {
                BungeeUtils.sendPlayerToServer(
                        crescentCore, player, serverName);
                return playerData;
            } else {
                sender.sendMessage(CrescentCoreLocalization.GENERIC_TELEPORTATION_FAILURE.getFormattedMessage(player.locale(), serverName));
                return null;
            }
        });

        sender.sendMessage(CrescentCoreLocalization.GENERIC_AWAIT_TELEPORTATION.getFormattedMessage(player.locale(), serverName));*/
    }
}
