package net.gundul.interkom_server.Controllers;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Services.InterkomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class ServerController
{
	private InterkomService interkomService;

	public ServerController(InterkomService interkomService)
	{
		super();
		this.interkomService = interkomService;
	}

	@PostMapping()
	public ResponseEntity<InterkomServer> saveServer(@RequestBody InterkomServer server) throws Exception
	{
		InterkomServer nserv = new InterkomServer(server.getServerName(), server.getEmail());

		return new ResponseEntity<InterkomServer>(interkomService.saveServer(nserv), HttpStatus.CREATED);
	}

	@GetMapping
	public List<InterkomServer> getAllServers()
	{
		return interkomService.getAllServers();
	}

	/*
	@GetMapping("/api/{key}")
	public ResponseEntity<String>	getServerByKey(@PathVariable("key") String key)
	{
		if (interkomService.findServerByKey(key) == null)
			return new ResponseEntity<String>("Error", HttpStatus.NO_CONTENT);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	 */

	@GetMapping("{id}")
	public ResponseEntity<InterkomServer> getServerById(@PathVariable("id") Long serverId)
	{
		return new ResponseEntity<InterkomServer>(interkomService.getServerById(serverId), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<InterkomServer> updateServer(@PathVariable("id") Long serverId)
	{
		return new ResponseEntity<InterkomServer>(interkomService.updateServer(
				interkomService.getServerById(serverId), serverId), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteServer(@PathVariable("id") Long serverId)
	{
		interkomService.deleteServer(serverId);
		return new ResponseEntity<String>("Server successfully deleted", HttpStatus.OK);
	}
}
