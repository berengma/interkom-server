package net.gundul.interkom_server.Controllers;

import Utils.Time;
import lombok.Data;
import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.InterkomStuff;
import net.gundul.interkom_server.Database.Player;
import net.gundul.interkom_server.Services.AuthService;
import net.gundul.interkom_server.Services.InterkomService;
import net.gundul.interkom_server.Services.PlayerService;
import net.gundul.interkom_server.Services.StuffService;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

@Data
@RestController
@RequestMapping("/api/game")
public class GameController
{
	private AuthService			authService;
	private InterkomService 	interkomservice;
	private PlayerService		playerService;
	private StuffService		stuffService;
	private final int			expired = 30;

	public GameController(AuthService authService,
						  InterkomService interkomService,
						  PlayerService playerService,
						  StuffService stuffService)
	{
		super();
		this.interkomservice = interkomService;
		this.authService = authService;
		this.playerService = playerService;
		this.stuffService = stuffService;
	}

	@GetMapping("/getplayers")
	public ResponseEntity<String> getAllPlayers(@RequestHeader(name = "token") String token)
	{
		InterkomServer			secure = interkomservice.findServerByToken(token);
		List<InterkomServer>	online = interkomservice.getAllServersOnline();

		if (secure == null)
			return new ResponseEntity<String>("ERROR forbidden", HttpStatus.FORBIDDEN);
		JSONArray result = new JSONArray();
		Iterator<InterkomServer>	it = online.iterator();
		while (it.hasNext())
		{
			InterkomServer server = it.next();
			JSONObject obj = new JSONObject();
			obj.put("server", server.getServerName());
			List<Player> players = playerService.findByServerId(server.getId());
			Iterator<Player> jt = players.iterator();
			JSONArray names = new JSONArray();
			while (jt.hasNext())
				names.put(jt.next().getName());
			obj.put("players", names);
			result.put(obj);
		}

		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}

	@PostMapping("/players")
	public ResponseEntity<String> updatePlayers(@RequestHeader(name = "token") String token,
												@RequestBody String player) throws ParseException
	{
		InterkomServer server = interkomservice.findServerByToken(token);

		if (server == null)
			return new ResponseEntity<String>("NO ACCESS!", HttpStatus.FORBIDDEN);
		JSONObject obj = new JSONObject(player);
		if (!obj.has("name") || !obj.has("server"))
			return new ResponseEntity<String>("INVALID REQUEST", HttpStatus.NOT_FOUND);
		if (!obj.getString("server").equals(server.getServerName()))
			return new ResponseEntity<String>("Evil!", HttpStatus.I_AM_A_TEAPOT);

		Player newPlayer = new Player(obj.getString("name"), server);
		playerService.savePlayer(newPlayer);

		System.out.println(">>>> " + obj.get("name") + " <<<<\n");
		return new ResponseEntity<String>(player, HttpStatus.OK);
	}

	@PostMapping("/dropstuff")
	public ResponseEntity<String> addStuff(@RequestHeader(name = "token") String token,
										   @RequestBody String stuff) throws ParseException
	{
		JSONObject		obj = new JSONObject(stuff);
		InterkomServer	receivingServer = null;
		InterkomServer	secure = interkomservice.findServerByToken(token);

		if (secure == null)
			return new ResponseEntity<String>("NO ACCESS!", HttpStatus.FORBIDDEN);
		System.out.println(obj.toString());
		if (!obj.has("sender") ||
				!obj.has("receiver") ||
				!obj.has("originServer") ||
				!obj.has("receivingServer") ||
				!obj.has("itemStack") ||
				!obj.has("amount"))
			return new ResponseEntity<String>(stuff, HttpStatus.NOT_FOUND);
		System.out.println("obj is valid");
		receivingServer = interkomservice.getOnlineServerByName(obj.getString("receivingServer"));
		if (receivingServer == null)
			return new ResponseEntity<String>(stuff, HttpStatus.NOT_FOUND);
		System.out.println("Receiving server is valid");
		if (playerService.findByName(obj.getString("receiver"), receivingServer.getId()) == null)
			return new ResponseEntity<String>(stuff, HttpStatus.NOT_FOUND);
		System.out.println("Receiving player is online");
		InterkomStuff newStuff = new InterkomStuff(
				obj.getString("originServer"),
				obj.getString("sender"),
				obj.getString("receiver"),
				receivingServer,
				obj.getString("itemStack"),
				obj.getInt("amount"));
		stuffService.saveStuff(newStuff);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping("/pullstuff")
	public ResponseEntity<String> getStuffForServer(@RequestHeader(name = "token") String token)
	{
		InterkomServer secure = interkomservice.findServerByToken(token);

		if (secure == null)
			return new ResponseEntity<String>("ERROR forbidden", HttpStatus.FORBIDDEN);
		List<InterkomStuff> allStuff = stuffService.getAllStuffForServer(secure.getId());
		Iterator<InterkomStuff> it = allStuff.iterator();
		JSONArray result = new JSONArray();
		while (it.hasNext())
		{
			JSONObject obj = new JSONObject();
			InterkomStuff newStuff = it.next();
			obj.put("originServer", newStuff.getOriginServer());
			obj.put("sender", newStuff.getSender());
			obj.put("receiver", newStuff.getReceiver());
			obj.put("receivingServer", secure.getServerName());
			obj.put("itemStack", newStuff.getItemStack());
			obj.put("amount", newStuff.getAmount());
			result.put(obj);
		}
		stuffService.deleteInWhole(allStuff);
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}


	/* currently not available, due to Luanti bug processing DELETE requests

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
	 */

	@PostMapping("/players/remove")
	public ResponseEntity<String> removePlayer(@RequestHeader(name = "token") String token,
												@RequestBody String player) throws ParseException
	{
		InterkomServer server = interkomservice.findServerByToken(token);

		if (server == null)
			return new ResponseEntity<String>("ERROR forbidden", HttpStatus.FORBIDDEN);
		JSONObject obj = new JSONObject(player);
		if (!obj.has("name"))
			return new ResponseEntity<String>("ERROR not_found", HttpStatus.NOT_FOUND);
		Player newPlayer = playerService.findByName(obj.getString("name"), server.getId());
		if (newPlayer == null)
			return new ResponseEntity<String>("ERROR not_found", HttpStatus.NOT_FOUND);
		playerService.deletePlayer(newPlayer.getId());

		return new ResponseEntity<String>(player, HttpStatus.OK);
	}

	@Scheduled(cron = "30 * * * * ?")
	public void cleanupOrphans()
	{
		List<InterkomServer> 		online = interkomservice.getAllServersOnline();
		List<InterkomServer>		offline = interkomservice.getAllServersOffline();
		Iterator<InterkomServer>	it = online.iterator();
		Iterator<InterkomServer>	jt = offline.iterator();
		Timestamp					now = Time.getTimestamp();

		while (it.hasNext())
		{
			InterkomServer server = it.next();
			if (Time.getDifference(now, server.getToken().getTimestamp()) < expired )
				continue;
			System.out.println(Time.getTimestamp().toString() + ") Server expired: " + server.getServerName());
			interkomservice.forceServerOffline(server);
		}
		while (jt.hasNext())
		{
			InterkomServer server = jt.next();
			List<Player> players = playerService.findByServerId(server.getId());
			System.out.println(server.getId() + ") " + server.getServerName() + " - " + players.size());
			playerService.deleteInWhole(players);
		}
	}
}