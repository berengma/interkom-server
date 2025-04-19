package net.gundul.interkom_server.Services;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Player;

public interface PlayerService
{
	Player savePlayer(Player player);
	void	deletePlayer(Long id);
	Player findByNameAndServer(String name, Long serverId);
}
