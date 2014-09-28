package ar.edu.itba.pod.mmxivii.sube.server;

import ar.edu.itba.pod.mmxivii.sube.common.BaseMain;
import ar.edu.itba.pod.mmxivii.sube.common.Utils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import javax.annotation.Nonnull;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.*;

public class Main extends BaseMain
{
	private static Main main = null;

	private Main(@Nonnull String[] args)
	{
		super(args, OPTIONS_CONFIG);
		createRegistry();
		skipDelay();
		final CardRegistryImpl cardRegistry = new CardRegistryImpl();
		bindObject(CARD_REGISTRY_BIND, cardRegistry);
	}

	public static void main(@Nonnull String[] args) throws Exception
	{
		main = new Main(args);
		main.run();
	}

	private void run()
	{
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
		main.unbindObject(CARD_REGISTRY_BIND);
	}

	public static final String[][] OPTIONS_CONFIG = {
			new String[]{PORT_O_S, PORT_O_L, TRUE},
			new String[]{DELAY_O_S, DELAY_O_L, TRUE},
			new String[]{MAX_THREADS_O_S, MAX_THREADS_O_L, TRUE
			}};
}

