package net.gundul.interkom_server.Database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name="servers")
public class InterkomServer
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToMany(mappedBy = "originServer")
	private Set<InterkomStuff> stuff;

	@Column(name = "server_name", nullable = false)
	private String serverName;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "api_key")
	private String apiKey;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "token", referencedColumnName = "id")
	private Token token;

	@Column(name = "timestamp")
	private Timestamp timestamp;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getApiKey()
	{
		return this.apiKey;
	}

	public void setApiKey(String apikey)
	{
		this.apiKey = apikey;
	}

	public Token getToken()
	{
		return this.token;
	}

	public void setToken(Token token)
	{
		this.token = token;
	}

	public Timestamp getTimestamp()
	{
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp)
	{
		this.timestamp = timestamp;
	}
}