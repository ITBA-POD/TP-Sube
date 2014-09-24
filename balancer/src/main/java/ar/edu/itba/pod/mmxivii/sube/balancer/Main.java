package ar.edu.itba.pod.mmxivii.sube.balancer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.rmi.registry.Registry;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.*;

public class Main
{
	private static Registry registry = null;
	public static final String HELP = "java -jar ";

	private Main() {}

	public static void main( String[] args )
	{
		final Options options = buildOptions(
				new String[]{HOST_O_S, HOST_O_L, TRUE},
				new String[]{PORT_O_S, PORT_O_L, TRUE},
				new String[]{MAX_THREADS_O_S, MAX_THREADS_O_L, TRUE}
		);

		try {
			final CommandLine cmdLine = parseArguments(options, HELP, args);
			final String host = cmdLine.getOptionValue(HOST_O_L, HOST_O_D);
			final int port = Integer.valueOf(cmdLine.getOptionValue(PORT_O_L, PORT_O_D));
			if (cmdLine.hasOption(MAX_THREADS_O_L)) {
				final String maxThreads = cmdLine.getOptionValue(MAX_THREADS_O_L);
				System.setProperty("sun.rmi.transport.tcp.maxConnectionThreads", maxThreads);
			}

			registry = getRegistry(host, port);

			System.out.println("Starting Balancer!");


		} catch (ParseException e) {
			System.err.println("App Error: " + e.getMessage());
			System.exit(-1);
		}

	}

}

