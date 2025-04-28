package net.gundul.interkom_server.Database;

import Utils.Security;
import Utils.Time;
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
	public InterkomServer()
	{}

	public InterkomServer(String name, String email) throws Exception
	{
		this.serverName = name.replaceAll(" ", "_");
		this.email = email;
		this.timestamp = Time.getTimestamp();
		this.created = Time.getTimestamp();
		this.apiKey = Security.getApiKey(name, email);
		this.chatEnabled = false;
	}

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Getter
	@Setter
	@Column(name = "server_name", nullable = false, unique = true)
	private String serverName;

	@Getter
	@Setter
	@Column(name = "email", nullable = false)
	private String email;

	@Getter
	@Setter
	@Column(name = "api_key")
	private String apiKey;

	@OneToOne(cascade = CascadeType.ALL)
	@Getter
	@Setter
	@JoinColumn(name = "token", referencedColumnName = "id")
	private Token token;

	@Getter
	@Setter
	@Column(name = "timestamp")
	private Timestamp timestamp;

	@Getter
	@Column(name = "created")
	private Timestamp created;

	@Getter
	@Setter
	@Column(name = "chat_enabled")
	private Boolean chatEnabled;

	@Getter
	@OneToMany(cascade = CascadeType.ALL)
	private  Set<Player> players;

	@Getter
	@OneToMany(cascade = CascadeType.ALL)
	private  Set<InterkomStuff> stuff;
}