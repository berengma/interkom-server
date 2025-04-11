package Utils;

import java.sql.Timestamp;

public class Time
{
	public static Timestamp getTimestamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}
}
