package net.gundul.interkom_server.Controllers;

import Utils.Time;
import net.gundul.interkom_server.Database.InterkomServer;
import net.gundul.interkom_server.Database.Token;
import net.gundul.interkom_server.Services.AuthService;
import net.gundul.interkom_server.Services.InterkomService;
import net.gundul.interkom_server.Services.PlayerService;
import net.gundul.interkom_server.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/user")
public class UserController
{
	private AuthService 	authService;
	private InterkomService	interkomservice;
	private UserService		userService;
	private final int		tokenTimeout = 300;

	public UserController(AuthService authService,
						  InterkomService interkomService,
						  UserService userService)
	{
		super();
		this.interkomservice = interkomService;
		this.authService = authService;
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<String> login()
	{
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
}
