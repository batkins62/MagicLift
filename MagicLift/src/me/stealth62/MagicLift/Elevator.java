package me.stealth62.MagicLift;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Elevator implements Runnable
{
	private final MagicLift plugin;
	Logger log = Logger.getLogger("Minecraft");
	Block gold, button, top, bottom;
	Block topGlass, bottomGlass;
	Player player;
	Boolean isUp = false, isDown = false;

	public Elevator(MagicLift instance, Block block)
	{
		plugin = instance;
		button = block;

		isUp = isUpElevator();
		isDown = isDownElevator();

		if(isUp)
			prepareUpElevator();
		else if(isDown)
			prepareDownElevator();
	}

	public void say(String msg)
	{
		log.warning(msg);
	}

	public void prepareUpElevator()
	{
		for(Player p : button.getWorld().getPlayers())
		{
			if(isInUpElevator(p))
			{
				player = p;

				bottomGlass.setType(Material.AIR);
				topGlass.setType(Material.AIR);

				Location loc = bottom.getLocation();
				double x = loc.getX() + 0.5;
				double y = loc.getY() + 1.5;
				double z = loc.getZ() + 0.5;
				float yaw = player.getLocation().getYaw();
				float pitch = player.getLocation().getPitch();

				player.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));
				player.setVelocity(new Vector(0,0,0));

				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1);
				return;
			}
			else
				say("Not inside elevator!");
		}
	}

	public void prepareDownElevator()
	{
		for(Player p : button.getWorld().getPlayers())
		{
			if(isInDownElevator(p))
			{
				player = p;

				bottomGlass.setType(Material.AIR);
				topGlass.setType(Material.AIR);

				Location loc = topGlass.getLocation();
				double x = loc.getX() + 0.5;
				double y = loc.getY() + 1.5;
				double z = loc.getZ() + 0.5;
				float yaw = player.getLocation().getYaw();
				float pitch = player.getLocation().getPitch();

				player.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));
				player.setVelocity(new Vector(0,0,0));

				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1);
				return;
			}
			else
				say("Not inside elevator!");
		}
	}

	public boolean isInUpElevator(Player p)
	{
		Location inside = new Location(
				bottom.getWorld(),
				bottom.getX(), bottom.getY()+1, bottom.getZ()
		);

		Location loc = p.getLocation();

		if(
				loc.getX() < inside.getX() + 1
				&& loc.getX() > inside.getX() - 1
				&& loc.getY() < inside.getY() + 1
				&& loc.getY() > inside.getY() - 1
				&& loc.getZ() < inside.getZ() + 1
				&& loc.getZ() > inside.getZ() - 1
		)
		{
			return true;
		}

		return false;
	}

	public boolean isInDownElevator(Player p)
	{
		Location inside = new Location(
				topGlass.getWorld(),
				topGlass.getX(), topGlass.getY()+1, topGlass.getZ()
		);

		Location loc = p.getLocation();

		if(
				loc.getX() < inside.getX() + 1
				&& loc.getX() > inside.getX() - 1
				&& loc.getY() < inside.getY() + 1
				&& loc.getY() > inside.getY() - 1
				&& loc.getZ() < inside.getZ() + 1
				&& loc.getZ() > inside.getZ() - 1
		)
		{
			return true;
		}

		return false;
	}

	public boolean isUpElevator()
	{
	Material mat = button.getType();
	if(mat == Material.STONE_BUTTON)
	{
		Block u = button.getRelative(BlockFace.UP);
		Block d = button.getRelative(BlockFace.DOWN);
		Block n = button.getRelative(BlockFace.NORTH);
		Block e = button.getRelative(BlockFace.EAST);
		Block s = button.getRelative(BlockFace.SOUTH);
		Block w = button.getRelative(BlockFace.WEST);

		List<Block> blocks = new ArrayList<Block>();
		blocks.add(u);
		blocks.add(d);
		blocks.add(n);
		blocks.add(e);
		blocks.add(s);
		blocks.add(w);

		for(Block b : blocks)
		{
			if(b.getType() == Material.GOLD_BLOCK)
			{
				if(
						button.getRelative(BlockFace.UP).getType() == Material.AIR
						&& button.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType() == Material.GLASS
						&& button.getRelative(BlockFace.DOWN).getType() == Material.AIR
						&& button.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN)
						.getType() == Material.IRON_BLOCK
				)
				{
					gold = b;
					bottomGlass = button.getRelative(BlockFace.UP).getRelative(BlockFace.UP);

					bottom = button.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
					for(int i = bottom.getY()+2; i < 130; i++)
					{
						Block t = button.getWorld().getBlockAt(button.getX(), i, button.getZ());

						if(t.getType() == Material.IRON_BLOCK)
						{
							top = t;
							topGlass = top.getRelative(BlockFace.DOWN)
							.getRelative(BlockFace.DOWN)
							.getRelative(BlockFace.DOWN)
							.getRelative(BlockFace.DOWN);

							if(topGlass.getType() != Material.GLASS)
							{
								say("Oops, the topGlass material is " + topGlass.getType().toString());
								return false;
							}

							break;
						}
						if(i > 128)
							return false;
					}

					return true;
				}
			}
		}
	}
	return false;
	}

	public boolean isDownElevator()
	{
		Material mat = button.getType();
		if(mat == Material.STONE_BUTTON)
		{
			Block u = button.getRelative(BlockFace.UP);
			Block d = button.getRelative(BlockFace.DOWN);
			Block n = button.getRelative(BlockFace.NORTH);
			Block e = button.getRelative(BlockFace.EAST);
			Block s = button.getRelative(BlockFace.SOUTH);
			Block w = button.getRelative(BlockFace.WEST);

			List<Block> blocks = new ArrayList<Block>();
			blocks.add(u);
			blocks.add(d);
			blocks.add(n);
			blocks.add(e);
			blocks.add(s);
			blocks.add(w);

			for(Block b : blocks)
			{
				if(b.getType() == Material.GOLD_BLOCK)
				{
					if(
							button.getRelative(BlockFace.UP).getType() == Material.AIR
							&& button.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType() == Material.IRON_BLOCK
							&& button.getRelative(BlockFace.DOWN).getType() == Material.AIR
							&& button.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN)
							.getType() == Material.GLASS
					)
					{
						gold = b;
						topGlass = button.getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);

						top = button.getRelative(BlockFace.UP).getRelative(BlockFace.UP);
						for(int i = top.getY()-2; i > -2; i--)
						{
							Block t = button.getWorld().getBlockAt(button.getX(), i, button.getZ());

							if(t.getType() == Material.IRON_BLOCK)
							{
								bottom = t;
								bottomGlass = bottom.getRelative(BlockFace.UP)
								.getRelative(BlockFace.UP)
								.getRelative(BlockFace.UP)
								.getRelative(BlockFace.UP);

								if(bottomGlass.getType() != Material.GLASS)
								{
									say("Oops, the bottomGlass material is " + topGlass.getType().toString());
									return false;
								}

								break;
							}
							if(i < 0)
								return false;
						}

						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void run()
	{
		if(isUp)
		{
			if(player.getLocation().getY() < topGlass.getLocation().getY() + 1)
			{
				player.setVelocity(new Vector(0,0.5,0));
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1);
			}
			else
			{
				Location loc = topGlass.getLocation();
				double x = loc.getX() + 0.5;
				double y = loc.getY() + 1.5;
				double z = loc.getZ() + 0.5;
				float yaw = player.getLocation().getYaw();
				float pitch = player.getLocation().getPitch();

				player.sendMessage("Ding! Top floor.");
				player.setVelocity(new Vector(0,0,0));
				player.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));
				player.setFallDistance(0);
				topGlass.setType(Material.GLASS);
				bottomGlass.setType(Material.GLASS);
			}
		}
		else if(isDown)
		{
			if(player.getLocation().getY() > bottomGlass.getLocation().getY() + 1)
			{
				player.setVelocity(new Vector(0,-0.5,0));
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, 1);
			}
			else
			{
				Location loc = bottomGlass.getLocation();
				double x = loc.getX() + 0.5;
				double y = loc.getY() - 1.5;
				double z = loc.getZ() + 0.5;
				float yaw = player.getLocation().getYaw();
				float pitch = player.getLocation().getPitch();

				player.sendMessage("Ding! Bottom floor.");
				player.setVelocity(new Vector(0,0,0));
				player.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));
				player.setFallDistance(0);
				topGlass.setType(Material.GLASS);
				bottomGlass.setType(Material.GLASS);
			}
		}
	}
}