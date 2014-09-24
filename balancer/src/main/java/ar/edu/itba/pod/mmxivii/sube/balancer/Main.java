package ar.edu.itba.pod.mmxivii.sube.balancer;

import ar.edu.itba.pod.mmxivii.sube.common.CardRegistry;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.rmi.registry.Registry;
import java.util.Scanner;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.*;

public class Main
{
	public static final String HELP = "java -jar ";

	private Main() {}

	public static void main( String[] args ) throws Exception
	{
		final Options options = buildOptions(
				new String[]{HOST_O_S, HOST_O_L, TRUE},
				new String[]{PORT_O_S, PORT_O_L, TRUE},
				new String[]{MAX_THREADS_O_S, MAX_THREADS_O_L, TRUE}
		);

		final CommandLine cmdLine = parseArguments(options, HELP, args);
		final String host = cmdLine.getOptionValue(HOST_O_L, HOST_O_D);
		final int port = Integer.valueOf(cmdLine.getOptionValue(PORT_O_L, PORT_O_D));
		if (cmdLine.hasOption(MAX_THREADS_O_L)) {
			final String maxThreads = cmdLine.getOptionValue(MAX_THREADS_O_L);
			System.setProperty(MAX_THREADS_JAVA_PROPERTY, maxThreads);
		}

		final Registry registry = getRegistry(host, port);

		System.out.println("Starting Balancer!");

		final CardRegistry cardRegistry = lookupObject(registry, CARD_REGISTRY_BIND);

		final Scanner scan = new Scanner(System.in);
		String line;
		do {
			line = scan.next();
			System.out.println("Balancer running");
		} while(!"x".equals(line));
		System.out.println("Balancer exit.");
		System.exit(0);

	}

}

