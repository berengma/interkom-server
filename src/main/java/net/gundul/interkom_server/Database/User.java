package net.gundul.interkom_server.Database;

import Utils.Security;
import Utils.Time;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="Users")
public class User
{
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

	@Getter
	@Column(name = "is_admin")
	private Boolean isAdmin;

	@Getter
	@Column(name = "created")
	private Timestamp created;

	public Boolean verifyPassword(String password)
	{
		if (this.password.equals(Security.hashPassword(password, this.salt)))
			return true;
		return false;
	}
}
