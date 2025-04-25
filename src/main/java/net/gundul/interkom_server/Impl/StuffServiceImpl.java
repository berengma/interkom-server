package net.gundul.interkom_server.Impl;

import net.gundul.interkom_server.Database.InterkomStuff;
import net.gundul.interkom_server.Repositories.StuffRepository;
import net.gundul.interkom_server.Services.StuffService;
import org.springframework.stereotype.Service;

@Service
public class StuffServiceImpl implements StuffService
{
	private StuffRepository stuffRepository;

	public StuffServiceImpl(StuffRepository stuffRepository)
	{
		super();
		this.stuffRepository = stuffRepository;
	}

	@Override
	public InterkomStuff saveStuff(InterkomStuff stuff)
	{
		return stuffRepository.save(stuff);
	}
}
