package me.stealth62.MagicLift;

import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class MagicLift extends JavaPlugin
{
	Server server;
	Logger log = Logger.getLogger("Minecraft");
	private Elevator elevator;
	private final MagicLiftRedstoneListener redstoneListener = new MagicLiftRedstoneListener(this);
	private final MagicLiftTeleportListener teleportListener = new MagicLiftTeleportListener(this);
	private final MagicLiftPlayerListener playerListener = new MagicLiftPlayerListener(this);

	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(redstoneListener, this);
		getServer().getPluginManager().registerEvents(teleportListener, this);
		getServer().getPluginManager().registerEvents(playerListener, this);

		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	@Override
	public void onDisable()
	{
		getServer().getScheduler().cancelTasks(this);
		System.out.println("MagicLift has been disabled!");
	}
}