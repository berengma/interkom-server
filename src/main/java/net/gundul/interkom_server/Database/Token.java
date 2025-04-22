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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Getter
	@Column(name = "token")
	private String token;

	@Getter
	@Column(name = "timestamp")
	private Timestamp timestamp;

	@Column(name = "created")
	private Timestamp created;

	public void setTimestamp()
	{
		this.timestamp = Time.getTimestamp();
	}

	public Long getId()
	{
		return this.id;
	}

	public String getToken()
	{
		return this.token;
	}

	public Timestamp getCreated()
	{
		return this.created;
	}

	public Timestamp getTimestamp()
	{
		return this.timestamp;
	}
}
