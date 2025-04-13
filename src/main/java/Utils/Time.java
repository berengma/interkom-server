package Utils;

import java.sql.Timestamp;
import java.time.ZoneOffset;

public class Time
{
	public static Timestamp getTimestamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}

	public static Long getDifference(Timestamp newer, Timestamp older)
	{
		 return newer.toLocalDateTime().toEpochSecond(ZoneOffset.UTC) -
				older.toLocalDateTime().toEpochSecond(ZoneOffset.UTC);
	}
}
