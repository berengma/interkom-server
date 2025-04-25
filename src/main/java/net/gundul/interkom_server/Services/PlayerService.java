package net.gundul.interkom_server.Services;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Player;

import java.util.List;

public interface PlayerService
{
	Player			savePlayer(Player player);
	void			deletePlayer(Long id);
	Player			findByName(String namei, Long serverId);
	List<Player>	findByServerId(Long serverId);
}
