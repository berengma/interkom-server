package net.gundul.interkom_server.Controllers;

import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Services.AuthService;
import net.gundul.interkom_server.Services.InterkomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
	private AuthService authService;
	//private InterkomService	interkomservice;

	public AuthController(AuthService authService)
	{
		super();
//		this.interkomservice = interkomService;
		this.authService = authService;
	}

	@GetMapping
	public ResponseEntity<String> auth(@RequestHeader (name = "apikey") String key)
	{
//		if (interkomservice.findServerByKey(key) == null)
//			return new ResponseEntity<String>("Invalid API-key", HttpStatus.FORBIDDEN);
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}

}
