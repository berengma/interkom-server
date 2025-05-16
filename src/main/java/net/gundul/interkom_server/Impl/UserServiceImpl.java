package net.gundul.interkom_server.Impl;

import net.gundul.interkom_server.Database.User;
import net.gundul.interkom_server.Repositories.UserRepository;
import net.gundul.interkom_server.Services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository)
	{
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User saveUser(User user)
	{
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id)
	{
		userRepository.deleteById(id);
	}

	@Override
	public void deleteInWhole(List<User> users)
	{
		userRepository.deleteAllInBatch(users);
	}

	@Override
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}

	@Override
	public User findByName(String name)
	{
		return userRepository.findByName(name);
	}

	@Override
	public User findByToken(String token)
	{
		return userRepository.findByToken(token);
	}
}
