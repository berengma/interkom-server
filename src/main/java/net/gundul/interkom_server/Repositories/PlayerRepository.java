package net.gundul.interkom_server.Repositories;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long>
{
	Player			findByNameAndServerId(String name, Long serverId);
	List<Player>	findByServerId(Long serverId);
}
