package it.crescentsun.api.crescentcore;

import org.bukkit.NamespacedKey;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.management.ServiceNotFoundException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Any plugin which depends on CrescentCore should extend this class instead of JavaPlugin.
 */
public abstract class CrescentPlugin extends JavaPlugin {

    protected ServicesManager serviceManager = getServer().getServicesManager();
    protected final Random random = new Random();

    protected CrescentCoreAPI crescentCoreAPI;
    private static Logger logger;
    private static String pluginName;

    public CrescentPlugin() {
        logger = getLogger();
        pluginName = getName();
    }

    /**
     * Gets the specified service provider.
     * Should be called in the initServices method.
     *
     * @param serviceClass The class of the service provider.
     * @return The service provider.
     * @param <T> The type of the service provider.
     * @throws ServiceNotFoundException If the service provider is not found.
     */
    protected <T> T getServiceProvider(Class<T> serviceClass) throws ServiceNotFoundException {
        RegisteredServiceProvider<T> rsp = serviceManager.getRegistration(serviceClass);
        if (rsp == null) {
            throw new ServiceNotFoundException(serviceClass.getSimpleName() + " not found");
        }
        return rsp.getProvider();
    }

    /**
     * Gets the instance of the Random object defined above.
     * @return the Random object.
     */
    public Random random() {
        if (random == null) {
            throw new IllegalStateException("Random object not initialized");
        }
        return random;
    }

    public CrescentCoreAPI getCrescentCoreAPI() {
        if (crescentCoreAPI == null) {
            throw new IllegalStateException("CrescentCoreAPI service not initialized");
        }
        return crescentCoreAPI;
    }

    /**
     * Creates a namespaced key with the given key.
     * The namespace will always be the plugin's name (as defined in the plugin.yml).
     *
     * @param key The key to create the namespaced key with.
     * @return The namespaced key.
     */
    public static NamespacedKey id(String key) {
        return new NamespacedKey(name(), key);
    }

    /**
     * Returns the logger for the implementing plugin.
     * @return the logger for the plugin
     */
    @NotNull
    public static Logger logger() {
        return logger;
    }

    /**
     * @return  the name of the plugin, as defined in the plugin.yml.
     */
    @NotNull
    public static String name() {
        return pluginName;
    }
}
