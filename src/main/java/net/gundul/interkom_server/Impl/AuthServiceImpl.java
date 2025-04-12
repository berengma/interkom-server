package net.gundul.interkom_server.Impl;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Token;
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
}
