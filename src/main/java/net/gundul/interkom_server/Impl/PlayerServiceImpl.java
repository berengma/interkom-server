package net.gundul.interkom_server.Impl;

import net.gundul.interkom_server.Database.Player;
import net.gundul.interkom_server.Repositories.PlayerRepository;
import net.gundul.interkom_server.Services.PlayerService;
import org.springframework.stereotype.Service;

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
}
