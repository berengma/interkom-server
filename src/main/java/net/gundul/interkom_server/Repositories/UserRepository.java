package net.gundul.interkom_server.Repositories;

import net.gundul.interkom_server.Database.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>
{
}
