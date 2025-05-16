package net.gundul.interkom_server.Controllers;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Token;
import net.gundul.interkom_server.Database.User;
import net.gundul.interkom_server.Services.AuthService;
import net.gundul.interkom_server.Services.InterkomService;
import net.gundul.interkom_server.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class ServerController
{
	private InterkomService interkomService;
	private AuthService		authService;
	private UserService		userService;

	public ServerController(InterkomService interkomService,
							AuthService authservice,
							UserService userService)
	{
		super();
		this.interkomService = interkomService;
		this.authService = authservice;
		this.userService = userService;
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<InterkomServer> saveServer(@RequestBody InterkomServer server) throws Exception
	{
		InterkomServer nserv = new InterkomServer(server.getServerName(), server.getEmail());

		return new ResponseEntity<InterkomServer>(interkomService.saveServer(nserv), HttpStatus.CREATED);
	}

	@PostMapping(consumes = "application/x-www-form-urlencoded")
	public ModelAndView createServer(@RequestParam String serverName,
									 @RequestParam String email,
									 ModelAndView mview) throws Exception
	{
		InterkomServer nserv = new InterkomServer(serverName, email);
		interkomService.saveServer(nserv);

		mview.setViewName("config");
		mview.addObject("users", userService.getAllUsers());
		mview.addObject("servers", interkomService.getAllServers());
		mview.setStatus(HttpStatus.OK);
		return mview;
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

	@DeleteMapping("{name}")
	public ModelAndView deleteServer(@PathVariable("name") String serverName,
									 @RequestHeader (name = "Authorization") String key,
									ModelAndView mview)
	{
		InterkomServer		server = interkomService.findServerByName(serverName);
		User				admin = userService.findByToken(key);
		System.out.println(">>>" + serverName + " must be deleted! key is: " + key);

		if (admin == null || !admin.getIsAdmin())
			mview.setStatus(HttpStatus.FORBIDDEN);
		if (server == null)
			mview.setStatus(HttpStatus.NOT_FOUND);
		if (admin != null && admin.getIsAdmin() && server != null)
		{
			interkomService.forceServerOffline(server);
			interkomService.deleteServer(server.getId());
			mview.addObject("users", userService.getAllUsers());
			mview.addObject("servers", interkomService.getAllServers());
			mview.setStatus(HttpStatus.OK);
		}
		mview.setViewName("config");
		return mview;
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
