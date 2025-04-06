package net.gundul.interkom_server.Repositories;

import net.gundul.interkom_server.Database.InterkomServer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterkomRepository extends JpaRepository<InterkomServer, Long>
{
}
