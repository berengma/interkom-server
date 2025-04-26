package net.gundul.interkom_server.Impl;

import net.gundul.interkom_server.Database.InterkomStuff;
import net.gundul.interkom_server.Repositories.StuffRepository;
import net.gundul.interkom_server.Services.StuffService;
import org.springframework.stereotype.Service;

import java.util.List;

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

	@Override
	public List<InterkomStuff> getAllStuffForServer(Long id)
	{
		return stuffRepository.findByReceivingServerId(id);
	}

	@Override
	public void deleteStuffById(Long id)
	{
		stuffRepository.deleteById(id);
	}

	@Override
	public void deleteInWhole(List<InterkomStuff> stuff)
	{
		stuffRepository.deleteAllInBatch(stuff);
	}
}
