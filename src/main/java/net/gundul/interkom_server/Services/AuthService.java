package net.gundul.interkom_server.Services;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Token;

public interface AuthService
{
	Token	saveToken(InterkomServer server);
	Token	updateToken(Token token, Long id);
	void	deleteToken(Long id);
}
