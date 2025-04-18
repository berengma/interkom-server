package net.gundul.interkom_server.Controllers;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Player;
import net.gundul.interkom_server.Services.AuthService;
import net.gundul.interkom_server.Services.InterkomService;
import net.gundul.interkom_server.Services.PlayerService;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/game")
public class GameController {
	private AuthService			authService;
	private InterkomService 	interkomservice;
	private PlayerService		playerService;

	public GameController(AuthService authService,
						  InterkomService interkomService,
						  PlayerService playerService)
	{
		super();
		this.interkomservice = interkomService;
		this.authService = authService;
		this.playerService = playerService;
	}

	@PostMapping("/players")
	public ResponseEntity<String> updatePlayers(@RequestHeader(name = "token") String token,
												@RequestBody String player) throws ParseException {
		InterkomServer server = interkomservice.findServerByToken(token);

		if (server == null)
			return new ResponseEntity<String>("ERROR forbidden", HttpStatus.FORBIDDEN);
		JSONObject obj = new JSONObject(player);
		if (!obj.has("name") || !obj.has("server"))
			return new ResponseEntity<String>("ERROR not_found", HttpStatus.NOT_FOUND);
		if (!obj.getString("server").equals(server.getServerName()))
			return new ResponseEntity<String>("Evil!", HttpStatus.I_AM_A_TEAPOT);

		Player newPlayer = new Player(obj.getString("name"), server);
		playerService.savePlayer(newPlayer);

		System.out.println(">>>> " + obj.get("name") + " <<<<\n");
		return new ResponseEntity<String>(player, HttpStatus.OK);
	}

	@DeleteMapping("/players/{name}")
	public ResponseEntity<String> deletePlayer(@RequestHeader(name = "token") String token,
											   @PathVariable(name = "name") String name)
	{
		InterkomServer server = interkomservice.findServerByToken(token);

		System.out.println("----\n"+name+"\n----\n");
		if (server == null)
			return new ResponseEntity<String>("ERROR", HttpStatus.FORBIDDEN);
		Player player = playerService.findByName(name);
		if (player == null)
			return new ResponseEntity<String>("Unknown player", HttpStatus.NOT_FOUND);
		System.out.println(">>>Player found: " + player.getName() + " -> " + player.getServer().getServerName() + "\n" +
				"\n");
		playerService.deletePlayer(player.getId());
		return new ResponseEntity<String>(name + " deleted!", HttpStatus.OK);
	}
}