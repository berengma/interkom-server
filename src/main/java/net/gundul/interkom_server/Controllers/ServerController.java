package net.gundul.interkom_server.Controllers;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Token;
import net.gundul.interkom_server.Services.AuthService;
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
	private AuthService		authService;

	public ServerController(InterkomService interkomService, AuthService authservice)
	{
		super();
		this.interkomService = interkomService;
		this.authService = authservice;
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

	@GetMapping("/logoff")
	public ResponseEntity<String> ping(@RequestHeader (name = "token") String token) {
		InterkomServer server = interkomService.findServerByToken(token);
		Token tok = null;

		if (server == null)
			return new ResponseEntity<String>("ERROR", HttpStatus.FORBIDDEN);
		tok = server.getToken();
		server.setToken(null);
		interkomService.updateServer(server, server.getId());
		authService.deleteToken(tok.getId());
		return new ResponseEntity<String>("Logged off", HttpStatus.OK);
	}
}
