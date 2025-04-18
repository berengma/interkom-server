package net.gundul.interkom_server.Impl;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Player;
import net.gundul.interkom_server.Database.Token;
import net.gundul.interkom_server.Exceptions.ResourceNotFoundException;
import net.gundul.interkom_server.Repositories.AuthRepository;
import net.gundul.interkom_server.Repositories.InterkomRepository;
import net.gundul.interkom_server.Services.InterkomService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class InterkomServiceImpl implements InterkomService
{
	private	InterkomRepository		interkomRepository;
	private AuthRepository			authRepository;

	public InterkomServiceImpl(InterkomRepository interkomRepository, AuthRepository authRepository)
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
	public InterkomServer			findServerByKey(String key)
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

}
