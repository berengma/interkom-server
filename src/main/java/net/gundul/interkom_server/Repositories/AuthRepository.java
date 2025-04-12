package net.gundul.interkom_server.Repositories;

import net.gundul.interkom_server.Database.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Token, Long>
{
}
