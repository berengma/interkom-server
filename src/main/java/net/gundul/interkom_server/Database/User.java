package net.gundul.interkom_server.Database;

import Utils.Security;
import Utils.Time;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="Users")
public class User
{
	@Transient
	private final int tokenExpiration = 24 * 60 * 60;

	public User()
	{}

	public User(String name, String password)
	{
		this.name = name;
		this.salt = Security.getSalt();
		this.password = Security.hashPassword(password, this.salt);
		this.created = Time.getTimestamp();
	}

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Getter
	@Column(name = "name")
	private String name;

	@Getter
	@Column(name = "salt")
	private String salt;

	@Column(name = "password")
	private String password;

	@Setter
	@Getter
	@Column(name = "is_admin")
	private Boolean isAdmin;

	@Getter
	@Setter
	@Column(name = "token")
	private String token;

	@Getter
	@Setter
	@Column(name = "lastLogin")
	private Timestamp lastLogin;

	@Getter
	@Column(name = "created")
	private Timestamp created;

	public Boolean verifyPassword(String password)
	{
		if (this.password.equals(Security.hashPassword(password, this.salt)))
			return true;
		return false;
	}

	private void logoffUser()
	{
		this.token = null;
		this.lastLogin = null;
	}

	public void autoLogoff()
	{
		if (Time.getDifference(this.lastLogin, Time.getTimestamp()) > this.tokenExpiration)
			logoffUser();
	}
}
