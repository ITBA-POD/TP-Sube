package ar.edu.itba.pod.mmxivii.sube.balancer;

import ar.edu.itba.pod.mmxivii.sube.common.BaseMain;
import ar.edu.itba.pod.mmxivii.sube.common.CardRegistry;
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
	private final CardRegistry cardRegistry;

	private Main(@Nonnull String[] args)
	{
		super(args, DEFAULT_CLIENT_OPTIONS);
		getRegistry();
		skipDelay();
		cardRegistry = lookupObject(CARD_REGISTRY_BIND);
		final CardServiceRegistryImpl cardServiceRegistry = new CardServiceRegistryImpl();
		bindObject(CARD_SERVICE_REGISTRY_BIND, cardServiceRegistry);

		final CardClientImpl cardClient = new CardClientImpl(cardRegistry, cardServiceRegistry);
		bindObject(CARD_CLIENT_BIND, cardClient);
	}

	public static void main(@Nonnull String[] args) throws Exception
	{
		main = new Main(args);
		main.run();
	}

	private void run()
	{
		System.out.println("Starting Balancer!");
		final Scanner scan = new Scanner(System.in);
		String line;
		do {
			line = scan.next();
			System.out.println("Balancer running");
		} while(!"x".equals(line));
		System.out.println("Balancer exit.");
		System.exit(0);

	}

	@SuppressWarnings("DuplicateStringLiteralInspection")
	public static void shutdown()
	{
		main.unbindObject(CARD_SERVICE_REGISTRY_BIND);
		main.unbindObject(CARD_CLIENT_BIND);
	}
}

