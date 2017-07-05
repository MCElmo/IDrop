package me.MC_Elmo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class IDrop extends JavaPlugin implements CommandExecutor
{
    private IDrop instance;
    private FileConfiguration config;
    private PluginManager pm;

    @Override
    public void onEnable()
    {
        instance = this;
        pm = getServer().getPluginManager();
        this.config = getConfig();
        config.options().copyDefaults(true);
        saveDefaultConfig();
        getCommand("idrop").setExecutor(this);
        // Plugin startup logic

    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        return false;
    }

    private void addListener(Listener list)
    {
        pm.registerEvents(list,this);
    }
}
