package net.gundul.interkom_server;

import Utils.Security;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
public class InterkomServerApplication
{
	public static void main(String[] args)
	{
		if (args.length == 0)
			SpringApplication.run(InterkomServerApplication.class, args);
	}
}
