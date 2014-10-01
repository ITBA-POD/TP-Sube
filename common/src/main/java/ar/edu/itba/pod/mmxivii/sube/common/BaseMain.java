package ar.edu.itba.pod.mmxivii.sube.common;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import javax.annotation.Nonnull;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteObject;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.*;

public abstract class BaseMain
{
	public static final String[][] DEFAULT_CLIENT_OPTIONS = {
			new String[]{HOST_O_S, HOST_O_L, TRUE},
			new String[]{PORT_O_S, PORT_O_L, TRUE},
			new String[]{MAX_THREADS_O_S, MAX_THREADS_O_L, TRUE
			}};
	protected final CommandLine cmdLine;
	protected Registry rmiRegistry = null;
	protected final Options options;

	protected BaseMain(@Nonnull String[] args, @Nonnull String[][] optionsConfig)
	{
		options = buildOptions(optionsConfig);
		cmdLine = parseArguments(options, getHelpString(), args);
	}

	protected String getHelpString()
	{
		return "java -jar";
	}

	protected void getRegistry()
	{
		final String host = cmdLine.getOptionValue(HOST_O_L, HOST_O_D);
		final int port = Integer.valueOf(cmdLine.getOptionValue(PORT_O_L, PORT_O_D));
		final String maxThreads = cmdLine.getOptionValue(MAX_THREADS_O_L, MAX_THREADS_O_D);
		System.setProperty(MAX_THREADS_JAVA_PROPERTY, maxThreads);

		rmiRegistry = Utils.getRegistry(host, port);
	}

	protected void createRegistry()
	{
		final int port = Integer.valueOf(cmdLine.getOptionValue(PORT_O_L, PORT_O_D));
		if (cmdLine.hasOption(MAX_THREADS_O_L)) {
			final String maxThreads = cmdLine.getOptionValue(MAX_THREADS_O_L);
			System.setProperty(MAX_THREADS_JAVA_PROPERTY, maxThreads);
		}

		rmiRegistry = Utils.createRegistry(port);
	}

	protected void bindObject(@Nonnull final String name, @Nonnull final RemoteObject remote)
	{
		try {
			rmiRegistry.bind(name, remote);
		} catch (AlreadyBoundException | RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	protected void unbindObject(@Nonnull final String name)
	{
		try {
			rmiRegistry.unbind(name);
		} catch (RemoteException | NotBoundException e) {
			System.err.println("Unbind Error: " + e.getMessage());
			System.exit(-1);
		}
	}

	protected void setDelay()
	{
		final boolean skipDelay = Boolean.valueOf(cmdLine.getOptionValue(DELAY_O_L, FALSE));
		Utils.skipDelay(skipDelay);
	}

}
