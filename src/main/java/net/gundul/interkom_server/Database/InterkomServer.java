package net.gundul.interkom_server.Database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="servers")
public class InterkomServer
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Getter
	@Setter
	@Column(name = "server_name", nullable = false)
	private String serverName;

	@Setter
	@Getter
	@Column(name = "email", nullable = false)
	private String email;

	@Setter
	@Getter
	@Column(name = "api_key")
	private String apiKey;

	@Setter
	@Getter
	@Column(name = "token")
	private String token;

	@Setter
	@Getter
	@Column(name = "timestamp")
	private Timestamp timestamp;

}
