package net.gundul.interkom_server.Services;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.InterkomStuff;

import java.util.List;

public interface StuffService
{
	InterkomStuff		saveStuff(InterkomStuff stuff);
	List<InterkomStuff>	getAllStuffForServer(Long id);
	void				deleteStuffById(Long id);
	void				deleteInWhole(List<InterkomStuff> stuff);
}
