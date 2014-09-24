package ar.edu.itba.pod.mmxivii.sube.common;

import org.apache.commons.cli.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Utils
{

	public static final String MAX_THREADS_JAVA_PROPERTY = "sun.rmi.transport.tcp.maxConnectionThreads";

	public static final int HELP_EXIT_CODE = -3;
	public static final int PARSE_FAILED_EXIT_CODE = -4;
	public static final int RMI_FAILED_EXIT_CODE = -5;

	public static final String PORT_O_S = "p";
	public static final String PORT_O_L = "port";
	public static final String PORT_O_D = "7242";
	public static final String HOST_O_S = "h";
	public static final String HOST_O_L = "host";
	public static final String HOST_O_D = "localhost";
	public static final String TRUE = "True";
	public static final String FALSE = "FALSE";
	public static final String MAX_THREADS_O_S = "t";
	public static final String MAX_THREADS_O_L = "max-threads";

	public static final String CARD_REGISTRY_BIND = "cardRegistry";

	private Utils() {}

	@Nonnull
	public static Options buildOptions(@Nonnull String[] ... options)
	{
		final Options result = new Options();
		result.addOption("help", false, "Help");
		for (String[] option : options) {
			if (option.length != 3) throw new IllegalArgumentException("invalid options");
			final String opt = option[0];
			final String longOpt = option[1];
			final boolean hasArg = Boolean.parseBoolean(option[2]);
			result.addOption(opt, longOpt, hasArg, "");
		}

		return result;
	}

	@Nonnull
	public static CommandLine parseArguments(@Nonnull final Options options, final String help, @Nonnull final String[] args)
	{
		try {
			// parse the command line arguments
			final CommandLine commandLine = new BasicParser().parse(options, args, false);

			if (commandLine.hasOption("help")) {
				new HelpFormatter().printHelp(help, options);
				System.exit(HELP_EXIT_CODE);
			}
			return commandLine;
		}
		catch (ParseException e) {
			// oops, something went wrong
			System.err.println("Parsing failed.  Reason: " + e.getMessage());
			System.exit(PARSE_FAILED_EXIT_CODE);
			throw new RuntimeException(e);
		}
	}

	@Nonnull
	public static Registry createRegistry(final int port)
	{
		try {
			final Registry registry = LocateRegistry.createRegistry(port);
			System.out.println(String.format("Created RMI Registry on %s", port));
			return registry;
		} catch (RemoteException e) {
			System.err.println("Failed to create RMI Registry. Reason: " + e.getMessage());
			System.exit(RMI_FAILED_EXIT_CODE);
			throw new RuntimeException(e);
		}
	}

	@Nonnull
	public static Registry getRegistry(@Nullable final String host, final int port)
	{
		try {
			final Registry registry = LocateRegistry.getRegistry(host, port);
			System.out.println(String.format("Connected to RMI Registry on %s:%s", host, port));
			return registry;
		} catch (RemoteException e) {
			System.err.println("Failed to get RMI Registry. Reason: " + e.getMessage());
			System.exit(RMI_FAILED_EXIT_CODE);
			throw new RuntimeException(e);
		}
	}

	public static void bindObject(@Nonnull Registry registry, @Nonnull final String name, @Nonnull final Remote remote) throws AlreadyBoundException
	{
		try {
			registry.bind(name, UnicastRemoteObject.exportObject(remote, 0));
		} catch (RemoteException e) {
			System.err.println("Failed to bind Remote Object in Registry. Reason: " + e.getMessage());
			System.exit(RMI_FAILED_EXIT_CODE);
			throw new RuntimeException(e);
		}
	}

	@Nonnull
	public static <T extends Remote> T lookupObject(@Nonnull Registry registry, @Nonnull final String name) throws NotBoundException
	{
		try {
			//noinspection unchecked
			return (T) registry.lookup(name);
		} catch (RemoteException e) {
			System.err.println("Failed to lookup Remote Object in Registry. Reason: " + e.getMessage());
			System.exit(RMI_FAILED_EXIT_CODE);
			throw new RuntimeException(e);
		}
	}
}
