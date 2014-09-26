package ar.edu.itba.pod.mmxivii.sube;

import ar.edu.itba.pod.mmxivii.sube.server.Main;
import org.junit.Test;

public class MainTest
{
	public MainTest() {}

	@Test
	public void testMain() throws Exception
	{
		new Thread() {
			@Override
			public void run()
			{
				try {
					Main.main(new String[]{});
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}.start();

		Thread.sleep(1000);
		Main.shutdown();
	}
}
