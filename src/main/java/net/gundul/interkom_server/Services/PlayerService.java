package net.gundul.interkom_server.Services;

import net.gundul.interkom_server.Database.Player;

public interface PlayerService
{
	Player savePlayer(Player player);
	void	deletePlayer(Long id);
}
