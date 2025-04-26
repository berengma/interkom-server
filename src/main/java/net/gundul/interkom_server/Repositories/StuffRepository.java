package net.gundul.interkom_server.Repositories;

import net.gundul.interkom_server.Database.InterkomStuff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StuffRepository extends JpaRepository<InterkomStuff, Long>
{
	List<InterkomStuff> findByReceivingServerId(Long id);
}
