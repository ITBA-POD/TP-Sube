package ar.edu.itba.pod.mmxivii.sube.server;

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
				new String[]{PORT_O_S, PORT_O_L, TRUE},
				new String[]{MAX_THREADS_O_S, MAX_THREADS_O_L, TRUE}
		);

		try {
			final CommandLine cmdLine = parseArguments(options, HELP, args);
			final int port = Integer.valueOf(cmdLine.getOptionValue(PORT_O_L, PORT_O_D));
			if (cmdLine.hasOption(MAX_THREADS_O_L)) {
				final String maxThreads = cmdLine.getOptionValue(MAX_THREADS_O_L);
				System.setProperty(MAX_THREADS_JAVA_PROPERTY, maxThreads);
			}

			registry = createRegistry(port);

			System.out.println("Starting Server!");


		} catch (ParseException e) {
			System.err.println("App2 Error: " + e.getMessage());
			System.exit(-1);
		}

	}

}

