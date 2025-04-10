package net.gundul.interkom_server.Database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="InterkomStuff")
public class InterkomStuff
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Getter
	@Setter
	@Column(name = "origin_server", nullable = false)
	private String originServer;

	@Getter
	@Setter
	@Column(name = "sender", nullable = false)
	private String sender;

	@Getter
	@Setter
	@Column(name = "receiver", nullable = false)
	private String receiver;

	@ManyToOne
	@JoinColumn(name="receiving_server", nullable = false)
	private InterkomServer server;

	@Getter
	@Setter
	@Column(name = "itemstack", nullable = false)
	private String itemstack;

	@Getter
	@Setter
	@Column(name = "amount", nullable = false)
	private int amount;

	@Setter
	@Getter
	@Column(name = "timestamp")
	private Timestamp timestamp;
}
