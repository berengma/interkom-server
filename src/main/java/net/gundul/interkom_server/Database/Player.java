package net.gundul.interkom_server.Database;

import Utils.Security;
import Utils.Time;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="Players")
public class Player
{
	public Player()
	{}

	public Player(String name, InterkomServer server)
	{
		this.name = name;
		this.server = server;
		this.created = Time.getTimestamp();
	}

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "Server_id")
	InterkomServer server;

	@Getter
	@Column(name = "name")
	private String name;

	@Getter
	@Column(name = "created")
	private Timestamp created;
}
