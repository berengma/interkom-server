package Utils;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class Security
{
	public static String getApiKey(String name, String email)
	{
		try
		{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

			messageDigest.update((name + email).getBytes(StandardCharsets.UTF_8));
			byte[] 				key = messageDigest.digest();
			final BigInteger	number = new BigInteger(1, key);
			final String		hexKey = number.toString(16);

			return hexKey;
		}
		catch (Exception e)
		{
			System.out.println("Error: Hash algorithm not found!");
		}
		return null;
	}

	public static String getToken(String key)
	{
		final int	length = 32;

		String		code = getApiKey(key, Time.getTimestamp().toString());
		String		reverse = new StringBuilder(code).reverse().toString();
		String[]	token = reverse.split("(?<=\\G.{" + length + "})");

		return token[0];
	}

	public static String getSalt()
	{
		try
		{
			// Always use a SecureRandom generator
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");

			// Create array for salt
			byte[] salt = new byte[16];

			// Get a random salt
			sr.nextBytes(salt);

			// return salt
			return salt.toString();
		}
		catch(NoSuchAlgorithmException e)
		{
			System.out.println("ERROR[SALT]: No such algorithm");
			return "";
		}
		catch(NoSuchProviderException e)
		{
			System.out.println("ERROR[SALT]: No such provider");
			return "";
		}
	}

	public static String hashPassword(String password, String salt)
	{
		String generatedPassword = null;

		try
		{
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// Add password bytes to digest
			md.update(salt.getBytes());

			// Get the hash's bytes
			byte[] bytes = md.digest(password.getBytes());

			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			generatedPassword = sb.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return generatedPassword;
	}
}
