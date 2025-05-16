package net.gundul.interkom_server.Services;

import net.gundul.interkom_server.Database.User;

import java.util.List;

public interface UserService
{
	User			saveUser(User user);
	void			deleteUser(Long id);
	void			deleteInWhole(List<User> users);
	List<User>		getAllUsers();
	User 			findByName(String name);
	User			findByToken(String token);
}
