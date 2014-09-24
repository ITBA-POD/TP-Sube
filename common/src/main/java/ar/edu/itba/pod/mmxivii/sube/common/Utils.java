package ar.edu.itba.pod.mmxivii.sube.common;

import org.apache.commons.cli.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Utils
{

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

	private Utils() {}

	public static Options buildOptions(String[] ... options)
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

	public static CommandLine parseArguments(final Options options, final String help, final String[] args) throws ParseException
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
		catch (ParseException exp) {
			// oops, something went wrong
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
			System.exit(PARSE_FAILED_EXIT_CODE);
			throw exp;
		}
	}

	public static Registry getRegistry(final String host, final int port)
	{
		try {
			final Registry registry = LocateRegistry.getRegistry(host, port);
			System.out.println(String.format("Connected to RMI Registry on %s:%s", host, port));
			return registry;
		} catch (RemoteException e) {
			System.err.println("Failed to get RMI Registry.  Reason: " + e.getMessage());
			System.exit(RMI_FAILED_EXIT_CODE);
			throw new RuntimeException(e);
		}

	}
}
