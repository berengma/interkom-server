package net.gundul.interkom_server.Services;

import net.gundul.interkom_server.Database.InterkomServer;

import java.util.List;

public interface InterkomService
{
	InterkomServer 			saveServer(InterkomServer server);
	List<InterkomServer>	getAllServers();
	InterkomServer			getServerById(Long id);
	InterkomServer			updateServer(InterkomServer server, Long id);
	void					deleteServer(Long id);
	InterkomServer			findServerByKey(String key);
	InterkomServer			findServerByToken(String token);
	List<InterkomServer>	getAllServersOnline();
	void					forceServerOffline(InterkomServer server);
	List<InterkomServer>	getAllServersOffline();
	InterkomServer			getOnlineServerByName(String name);
	Boolean					isPlayerOnline(InterkomServer server, String playerName);
}
