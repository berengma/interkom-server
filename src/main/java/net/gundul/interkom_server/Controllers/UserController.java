package net.gundul.interkom_server.Controllers;

import net.gundul.interkom_server.Services.AuthService;
import net.gundul.interkom_server.Services.InterkomService;
import net.gundul.interkom_server.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
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
	public String login()
	{
		return "forward:/login.html";
	}
}
