package net.gundul.interkom_server.Database;

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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Setter
	@Getter
	@Column(name = "token")
	private String token;

	@Setter
	@Getter
	@Column(name = "timestamp")
	private Timestamp timestamp;

}
