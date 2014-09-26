package ar.edu.itba.pod.mmxivii.sube.common;

import java.util.Random;

public class TestUtils
{
	private static final Random RANDOM = new Random();
	public static final int DEFAULT_LENGTH = 20;

	private TestUtils() {}

	public static String randomString()
	{
		return randomString(DEFAULT_LENGTH);
	}

	public static String randomString(final int length)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) sb.append(Character.toChars(RANDOM.nextInt()));

		return sb.toString();
	}
}
