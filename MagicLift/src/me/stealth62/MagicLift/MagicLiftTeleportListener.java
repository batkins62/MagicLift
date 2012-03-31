package me.stealth62.MagicLift;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class MagicLiftTeleportListener implements Listener
{
	private final MagicLift plugin;

	public MagicLiftTeleportListener(MagicLift instance)
	{
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		Player p = event.getPlayer();
		if (elevator.isInDownElevator(p) || Elevator.isInUpElevator(p))
		{
			event.setCancelled(true);
		}
	}

}
