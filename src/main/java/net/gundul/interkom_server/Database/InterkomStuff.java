package net.gundul.interkom_server.Database;

import Utils.Time;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="InterkomStuff")
public class InterkomStuff
{
	public InterkomStuff()
	{}

	public InterkomStuff(String originServer,
						 String sender,
						 String receiver,
						 InterkomServer receivingServer,
						 String itemstack,
						 int amount)
	{
		this.originServer = originServer;
		this.sender = sender;
		this.receiver = receiver;
		this.receivingServer = receivingServer;
		this.itemstack = itemstack;
		this.amount = amount;
		this.timestamp = Time.getTimestamp();
	}

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Getter
	@Column(name = "origin_server", nullable = false)
	private String originServer;

	@Getter
	@Column(name = "sender", nullable = false)
	private String sender;

	@Getter
	@Column(name = "receiver", nullable = false)
	private String receiver;

	@Getter
	@ManyToOne
	@JoinColumn(name="receiving_server", nullable = false)
	private InterkomServer receivingServer;

	@Getter
	@Column(name = "itemstack", nullable = false)
	private String itemstack;

	@Getter
	@Column(name = "amount", nullable = false)
	private int amount;

	@Getter
	@Column(name = "timestamp")
	private Timestamp timestamp;
}
