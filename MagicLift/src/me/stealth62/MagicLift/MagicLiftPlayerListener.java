package me.stealth62.MagicLift;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MagicLiftPlayerListener implements Listener
{
	private MagicLift plugin;
	
	public MagicLiftPlayerListener(MagicLift instance)
	{
		plugin = null;
		plugin = instance;
	}
	
	
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.WALL_SIGN))
        {
            Sign sign = (Sign)event.getClickedBlock().getState();
            MultiElevator lift = new MultiElevator(plugin, event.getClickedBlock().getFace(BlockFace.DOWN), false);
            if(lift.isMulti)
            {
                int curFloor;
                try
                {
                    curFloor = Integer.parseInt(sign.getLine(1));
                }
                catch(Exception e)
                {
                    curFloor = 0;
                }
                int curDest;
                try
                {
                    curDest = Integer.parseInt(sign.getLine(3));
                }
                catch(Exception e)
                {
                    curDest = 1;
                }
                int nextDest = 1;
                if(lift.floors.containsKey(Integer.valueOf(curDest + 1)))
                    nextDest = curDest + 1;
                if(nextDest == curFloor)
                {
                    nextDest++;
                    if(!lift.floors.containsKey(Integer.valueOf(nextDest)))
                        nextDest = 1;
                }
                sign.setLine(3, Integer.toString(nextDest));
                sign.update();
            }
        }
    }
}
