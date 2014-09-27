package ar.edu.itba.pod.mmxivii.sube.server;

import ar.edu.itba.pod.mmxivii.sube.common.Utils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.*;

public class Main
{
	public static final String HELP = "java -jar ";
	private static Registry registry = null;

	private Main() {}

	public static void main( String[] args ) throws Exception
	{
		final Options options = buildOptions(
				new String[]{PORT_O_S, PORT_O_L, TRUE},
				new String[]{DELAY_O_S, DELAY_O_L, TRUE},
				new String[]{MAX_THREADS_O_S, MAX_THREADS_O_L, TRUE}
		);
		final CommandLine cmdLine = parseArguments(options, HELP, args);

		final int port = Integer.valueOf(cmdLine.getOptionValue(PORT_O_L, PORT_O_D));
		final String maxThreads = cmdLine.getOptionValue(MAX_THREADS_O_L, MAX_THREADS_O_D);
		System.setProperty(MAX_THREADS_JAVA_PROPERTY, maxThreads);
		final boolean skipDelay = Boolean.valueOf(cmdLine.getOptionValue(DELAY_O_L, FALSE));
		Utils.skipDelay(skipDelay);

		registry = createRegistry(port);

		bindObject(registry, CARD_REGISTRY_BIND, new CardRegistryImpl());

		System.out.println("Starting Server!");
		final Scanner scan = new Scanner(System.in);
		String line;
		do {
			line = scan.next();
			System.out.println("Server running");
		} while(!"x".equals(line));
		System.out.println("Server exit.");
		System.exit(0);
	}

	@SuppressWarnings("DuplicateStringLiteralInspection")
	public static void shutdown()
	{
		try {
			registry.unbind(CARD_REGISTRY_BIND);
		} catch (RemoteException | NotBoundException e) {
			System.err.println("Shutdown Error: " + e.getMessage());
			System.exit(-1);
		}

	}
}

