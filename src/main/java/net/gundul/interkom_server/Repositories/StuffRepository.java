package net.gundul.interkom_server.Repositories;

import net.gundul.interkom_server.Database.InterkomStuff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StuffRepository extends JpaRepository<InterkomStuff, Long>
{
}
