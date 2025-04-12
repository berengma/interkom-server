package net.gundul.interkom_server.Services;

import net.gundul.interkom_server.Database.InterkomServer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterkomService
{
	InterkomServer 			saveServer(InterkomServer server);
	List<InterkomServer>	getAllServers();
	InterkomServer			getServerById(Long id);
	InterkomServer			updateServer(InterkomServer server, Long id);
	void					deleteServer(Long id);
	InterkomServer			findServerByKey(String key);
}
