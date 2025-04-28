package net.gundul.interkom_server.Impl;

import Utils.Time;
import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Token;
import net.gundul.interkom_server.Exceptions.ResourceNotFoundException;
import net.gundul.interkom_server.Repositories.AuthRepository;
import net.gundul.interkom_server.Services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService
{
	private AuthRepository authRepository;

	public AuthServiceImpl(AuthRepository authRepository)
	{
		super();
		this.authRepository = authRepository;
	}

	@Override
	public Token saveToken(InterkomServer server)
	{
		return new Token();
	}

	@Override
	public Token updateToken(Token token, Long id) {
		Token existingToken = authRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Token", "ID", id));
		existingToken.setTimestamp(Time.getTimestamp());
		authRepository.save(existingToken);
		return existingToken;
	}

	@Override
	public void deleteToken(Long id)
	{
		Token existingToken = authRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Token", "ID", id));
		authRepository.deleteById(id);
	}
}
