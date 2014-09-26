package ar.edu.itba.pod.mmxivii.sube.common;

import javax.annotation.Nonnull;
import java.rmi.server.UID;
import java.util.Random;

public class TestUtils
{
	public static final String TEST_LABEL = "label";
	public static final String TEST_CARD_HOLDER = "cardHolder";
	private static final Random RANDOM = new Random();
	public static final int DEFAULT_LENGTH = 20;

	private TestUtils() {}

	public static String randomInvalidString()
	{
		return randomInvalidString(DEFAULT_LENGTH);
	}

	public static String randomInvalidString(final int length)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) sb.append(Character.toChars(RANDOM.nextInt()));

		return sb.toString();
	}

	public static String randomString()
	{
		return new UID().toString();
	}

	public static String randomString(@Nonnull String prefix)
	{
		return prefix + new UID().toString().replaceAll(":", "").replaceAll("-", "");
	}
}
