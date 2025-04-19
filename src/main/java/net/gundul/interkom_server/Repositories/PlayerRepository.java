package net.gundul.interkom_server.Repositories;

import net.gundul.interkom_server.Database.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long>
{
	Player findByNameAndServerId(String name, Long id);
}
