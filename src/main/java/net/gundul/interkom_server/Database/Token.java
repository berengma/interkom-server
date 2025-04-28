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
@Table(name="Token")
public class Token
{
	public Token ()
	{}

	public Token(String key)
	{
		this.token = Security.getToken(key);
		this.timestamp = Time.getTimestamp();
		this.created = Time.getTimestamp();
	}

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Getter
	@Column(name = "token")
	private String token;

	@Getter
	@Setter
	@Column(name = "timestamp")
	private Timestamp timestamp;

	@Getter
	@Column(name = "created")
	private Timestamp created;
}
