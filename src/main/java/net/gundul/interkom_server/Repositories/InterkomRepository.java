package net.gundul.interkom_server.Repositories;

import net.gundul.interkom_server.Database.InterkomServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterkomRepository extends JpaRepository<InterkomServer, Long>
{
	//@Query("select api_key from servers where api_key = $1")
	//InterkomServer			findByApiKey(String key);
	InterkomServer		findByapiKey(String apiKey);
}
