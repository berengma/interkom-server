package net.gundul.interkom_server.Impl;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Player;
import net.gundul.interkom_server.Repositories.PlayerRepository;
import net.gundul.interkom_server.Services.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService
{
	private PlayerRepository playerRepository;

	public PlayerServiceImpl(PlayerRepository playerRepository)
	{
		super();
		this.playerRepository = playerRepository;
	}

	@Override
	public Player savePlayer(Player player)
	{
		return playerRepository.save(player);
	}

	@Override
	public void deletePlayer(Long id)
	{
		playerRepository.deleteById(id);
	}

	@Override
	public Player findByName(String name, Long id)
	{
		return playerRepository.findByNameAndServerId(name, id);
	}

	@Override
	public List<Player> findByServerId(Long serverId)
	{
		return playerRepository.findByServerId(serverId);
	}

	@Override
	public void deleteInWhole(List<Player> players)
	{
		playerRepository.deleteAllInBatch(players);
	}
}
