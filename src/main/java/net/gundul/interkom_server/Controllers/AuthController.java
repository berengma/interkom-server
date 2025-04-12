package net.gundul.interkom_server.Controllers;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Token;
import net.gundul.interkom_server.Services.AuthService;
import net.gundul.interkom_server.Services.InterkomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
	private AuthService authService;
	private InterkomService	interkomservice;

	public AuthController(AuthService authService, InterkomService interkomService)
	{
		super();
		this.interkomservice = interkomService;
		this.authService = authService;
	}

	@GetMapping
	public ResponseEntity<String> auth(@RequestHeader (name = "apikey") String key)
	{
		InterkomServer server = interkomservice.findServerByKey(key);

		if (server == null)
			return new ResponseEntity<String>("ERROR", HttpStatus.FORBIDDEN);
		if (server.getToken() != null)
			return new ResponseEntity<String>("ERROR: already registered", HttpStatus.CONFLICT);
		Token token = new Token(key);
		server.setToken(token);
		interkomservice.updateServer(server, server.getId());

		return new ResponseEntity<String>(server.getToken().getToken(), HttpStatus.OK);
	}

	@GetMapping("/ping")
	public ResponseEntity<String> ping(@RequestHeader (name = "token") String token)
	{
		InterkomServer server = interkomservice.findServerByToken(token);
		Token tok = null;

		if (server == null)
			return new ResponseEntity<String>("ERROR", HttpStatus.FORBIDDEN);
		tok = server.getToken();
		authService.updateToken(tok, tok.getId());

		return new ResponseEntity<String>("Pong", HttpStatus.OK);
	}

}
