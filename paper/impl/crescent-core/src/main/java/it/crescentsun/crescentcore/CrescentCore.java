package it.crescentsun.crescentcore;

import it.crescentsun.api.crescentcore.CrescentPlugin;
import it.crescentsun.crescentcore.db.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CrescentCore extends CrescentPlugin {

    private static CrescentCore instance;
    private DatabaseManager dbManager = null;

    @Override
    public void onEnable() {
        instance = this;
        registerEventListeners();
        establishDatabaseConnection();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getScheduler().cancelTasks(this);               //Cancels all tasks
        if (dbManager != null) {
            dbManager.disconnect();
        }
    }

    private static void registerEventListeners() {
        // Register event listeners here
    }


    private void establishDatabaseConnection() {
        if (dbManager != null) {
            throw new IllegalStateException("DatabaseManager has already been initialized.");
        }
        dbManager = new DatabaseManager(
                getConfig().getString("db.host"),
                getConfig().getInt("db.port"),
                getConfig().getString("db.db_name"),
                getConfig().getString("db.user"),
                getConfig().getString("db.password"),
                getConfig().getInt("db.max_pool_size")
        );
    }

    public DatabaseManager getDatabaseManager() {
        return dbManager;
    }

    public static CrescentCore getInstance() {
        return instance;
    }
}
