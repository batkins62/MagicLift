package me.stealth62.MagicLift;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class MagicLiftRedstoneListener implements Listener
{
	private final MagicLift plugin;

	public MagicLiftRedstoneListener(MagicLift instance)
	{
		plugin = instance;
	}

	@EventHandler
    public void onBlockRedstoneChange(BlockRedstoneEvent event)
    {
        Block block = event.getBlock();
        if(block.getType() == Material.STONE_BUTTON && !block.isBlockIndirectlyPowered())
            new Elevator(plugin, block);
    }
}