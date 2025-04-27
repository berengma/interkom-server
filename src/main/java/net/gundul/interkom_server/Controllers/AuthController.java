package net.gundul.interkom_server.Controllers;

import Utils.Time;
import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Token;
import net.gundul.interkom_server.Services.AuthService;
import net.gundul.interkom_server.Services.InterkomService;
import net.gundul.interkom_server.Services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
	private AuthService 	authService;
	private InterkomService	interkomservice;
	private PlayerService	playerService;
	private final int		tokenTimeout = 300;

	public AuthController(AuthService authService,
						  InterkomService interkomService,
						  PlayerService playerService)
	{
		super();
		this.interkomservice = interkomService;
		this.authService = authService;
		this.playerService = playerService;
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
		playerService.deleteInWhole(playerService.findByServerId(server.getId()));
		interkomservice.updateServer(server, server.getId());

		return new ResponseEntity<String>(
				server.getServerName() +" " + server.getToken().getToken(), HttpStatus.OK);
	}

	@GetMapping("/ping")
	public ResponseEntity<String> ping(@RequestHeader (name = "token") String token)
	{
		InterkomServer	server = interkomservice.findServerByToken(token);
		Token			tok = null;
		Timestamp 		now = new Timestamp(System.currentTimeMillis());

		if (server == null)
			return new ResponseEntity<String>("ERROR", HttpStatus.FORBIDDEN);
		tok = server.getToken();
		if (Time.getDifference(now, tok.getCreated()) > tokenTimeout)
		{
			Token newToken = new Token(server.getApiKey());
			server.setToken(newToken);
			interkomservice.updateServer(server, server.getId());
			authService.deleteToken(tok.getId());
			return new ResponseEntity<String>(server.getToken().getToken(), HttpStatus.OK);
		}
		authService.updateToken(tok, tok.getId());

		return new ResponseEntity<String>("Pong", HttpStatus.OK);
	}
}
