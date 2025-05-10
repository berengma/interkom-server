package net.gundul.interkom_server.Controllers;

import net.gundul.interkom_server.Database.User;
import net.gundul.interkom_server.Services.AuthService;
import net.gundul.interkom_server.Services.InterkomService;
import net.gundul.interkom_server.Services.UserService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/login")
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

	@PostMapping
	public ResponseEntity<String> getLogin(@RequestBody String user)
	{
		JSONObject newUser = new JSONObject(user);

		if (userService.getAllUsers().isEmpty())
		{
			User admin = new User(newUser.getString("name"), newUser.getString("password") );
			admin.setIsAdmin(true);
			userService.saveUser(admin);
			System.out.println(">>> New admin is: " + newUser.getString("name"));
			return new ResponseEntity<String>("SUCCESS: New admin is " + admin.getName(), HttpStatus.OK);
		}
		User tmpUser = userService.findByName(newUser.getString("name"));
		if (tmpUser == null)
		{
			return new ResponseEntity<String>("Error: " + newUser.getString("name") +
					" is unknown", HttpStatus.NOT_FOUND);
		}
		if (!tmpUser.verifyPassword(newUser.getString("password")))
		{
			return new ResponseEntity<String>("Error: " + newUser.getString("name") +
					" , wrong password", HttpStatus.FORBIDDEN);
		}
		if (tmpUser.getIsAdmin())
			return new ResponseEntity<String>("forward:/config.html", HttpStatus.OK);
		return new ResponseEntity<String>("forward:/servers.html", HttpStatus.OK);
	}
}
