package net.gundul.interkom_server.Controllers;

import Utils.Security;
import Utils.Time;
import net.gundul.interkom_server.Database.User;
import net.gundul.interkom_server.Services.AuthService;
import net.gundul.interkom_server.Services.InterkomService;
import net.gundul.interkom_server.Services.UserService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/user")
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

	@GetMapping("login")
	public String login()
	{
		return "login";
	}

	@PostMapping("login")
	public ModelAndView getLogin(@RequestBody String user, ModelAndView mview)
	{
		JSONObject		newUser = new JSONObject(user);

		if (userService.getAllUsers().isEmpty())
		{
			User admin = new User(newUser.getString("name"), newUser.getString("password") );
			admin.setIsAdmin(true);
			admin.setLastLogin(Time.getTimestamp());
			admin.setToken(Security.getToken(admin.getName() + admin.getSalt()));
			userService.saveUser(admin);
			System.out.println(">>> New admin is: " + newUser.getString("name"));
			mview.setViewName("config");
			mview.setStatus(HttpStatus.OK);
			return mview;
		}
		User tmpUser = userService.findByName(newUser.getString("name"));
		if (tmpUser == null)
		{
			mview.setViewName("login");
			mview.setStatus(HttpStatus.NOT_FOUND);
			return mview;
		}
		if (!tmpUser.verifyPassword(newUser.getString("password")))
		{
			mview.setViewName("login");
			mview.setStatus(HttpStatus.FORBIDDEN);
			return mview;
		}
		tmpUser.setLastLogin(Time.getTimestamp());
		tmpUser.setToken(Security.getToken(tmpUser.getName() + tmpUser.getSalt()));
		userService.saveUser(tmpUser);
		if (tmpUser.getIsAdmin())
		{
			mview.setViewName("config");
			mview.addObject("users", userService.getAllUsers());
			mview.addObject("servers", interkomservice.getAllServers());
			mview.addObject("token", tmpUser.getToken());
			System.out.println(">>>>" + tmpUser.getToken() + "<<<<");
			mview.setStatus(HttpStatus.OK);
			return mview;
		}
		mview.setViewName("servers");
		return mview;
	}
}
