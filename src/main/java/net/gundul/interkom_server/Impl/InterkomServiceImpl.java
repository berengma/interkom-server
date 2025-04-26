package net.gundul.interkom_server.Impl;

import lombok.Data;
import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Player;
import net.gundul.interkom_server.Database.Token;
import net.gundul.interkom_server.Exceptions.ResourceNotFoundException;
import net.gundul.interkom_server.Repositories.AuthRepository;
import net.gundul.interkom_server.Repositories.InterkomRepository;
import net.gundul.interkom_server.Repositories.PlayerRepository;
import net.gundul.interkom_server.Services.InterkomService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Data
@Service
public class InterkomServiceImpl implements InterkomService
{
	private	InterkomRepository		interkomRepository;
	private AuthRepository			authRepository;
	private PlayerRepository		playerRepository;

	public InterkomServiceImpl(InterkomRepository interkomRepository,
							   AuthRepository authRepository,
							   PlayerRepository playerRepository)
	{
		super();
		this.interkomRepository = interkomRepository;
		this.authRepository = authRepository;
	}

	@Override
	public InterkomServer saveServer(InterkomServer server)
	{
		return interkomRepository.save(server);
	}

	@Override
	public List<InterkomServer> getAllServers()
	{
		return interkomRepository.findAll();
	}

	@Override
	public InterkomServer getServerById(Long id)
	{
		return interkomRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Server", "Id", id));
	}

	@Override
	public InterkomServer updateServer(InterkomServer server, Long id)
	{
		InterkomServer existingServer = interkomRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Server", "ID", id));
		existingServer.setServerName(server.getServerName());
		existingServer.setEmail(server.getEmail());
		existingServer.setApiKey(server.getApiKey());
		existingServer.setToken(server.getToken());
		existingServer.setTimestamp(new Timestamp(System.currentTimeMillis()));
		interkomRepository.save(existingServer);
		return existingServer;
	}

	@Override
	public void deleteServer(Long id)
	{
		interkomRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Server", "Id", id));
		interkomRepository.deleteById(id);
	}

	@Override
	public InterkomServer findServerByKey(String key)
	{
		return interkomRepository.findByapiKey(key);
	}

	@Override
	public InterkomServer findServerByToken(String token)
	{
		Token tok =  authRepository.findByToken(token);

		if (tok == null)
			return null;
		return interkomRepository.findByToken(tok);
	}

	@Override
	public List<InterkomServer>	getAllServersOnline()
	{
		return interkomRepository.findByTokenNotNull();
	}

	@Override
	public void forceServerOffline(InterkomServer server)
	{
		Token		token = server.getToken();
		Set<Player>	players = server.getPlayers();

		playerRepository.deleteAllInBatch(players);
		authRepository.deleteById(token.getId());
		updateServer(server, server.getId());
	}

	@Override
	public List<InterkomServer>	getAllServersOffline()
	{
		return interkomRepository.findByTokenIsNull();
	}

	@Override
	public InterkomServer getOnlineServerByName(String name)
	{
		return interkomRepository.findByServerNameAndTokenNotNull(name);
	}
}
