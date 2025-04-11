package Utils;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Security
{
	public static String getApiKey(String name, String email)
	{
		try
		{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

			messageDigest.update((name + email).getBytes(StandardCharsets.UTF_8));
			byte[] key = messageDigest.digest();
			final BigInteger number = new BigInteger(1, key);
			final String hexKey = number.toString(16);

			return hexKey;
		}
		catch (Exception e)
		{
			System.out.println("Error: Hash algorithm not found!");
		}
		return null;
	}
}
