package net.gundul.interkom_server.Repositories;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterkomRepository extends JpaRepository<InterkomServer, Long>
{
	InterkomServer		findByapiKey(String apiKey);
	InterkomServer		findByToken(Token token);
}
