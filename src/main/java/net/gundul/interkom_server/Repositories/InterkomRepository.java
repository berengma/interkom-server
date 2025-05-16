package net.gundul.interkom_server.Repositories;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterkomRepository extends JpaRepository<InterkomServer, Long>
{
	InterkomServer			findByapiKey(String apiKey);
	InterkomServer			findByToken(Token token);
	List<InterkomServer>	findByTokenNotNull();
	List<InterkomServer>	findByTokenIsNull();
	InterkomServer			findByServerNameAndTokenNotNull(String name);
	InterkomServer			findByServerName(String name);
}
