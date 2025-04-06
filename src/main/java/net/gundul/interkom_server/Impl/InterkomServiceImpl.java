package net.gundul.interkom_server.Impl;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Exceptions.ResourceNotFoundException;
import net.gundul.interkom_server.InterkomServerApplication;
import net.gundul.interkom_server.Repositories.InterkomRepository;
import net.gundul.interkom_server.Services.InterkomService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class InterkomServiceImpl implements InterkomService
{
	private	InterkomRepository		interkomRepository;

	public InterkomServiceImpl(InterkomRepository interkomRepository)
	{
		super();
		this.interkomRepository = interkomRepository;
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
}
