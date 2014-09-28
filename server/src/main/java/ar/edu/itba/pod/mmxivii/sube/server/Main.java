package ar.edu.itba.pod.mmxivii.sube.server;

import ar.edu.itba.pod.mmxivii.sube.common.BaseMain;
import ar.edu.itba.pod.mmxivii.sube.common.Card;

import javax.annotation.Nonnull;
import java.rmi.RemoteException;
import java.util.Scanner;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.*;

public class Main extends BaseMain
{
	private static Main main = null;
	private final CardRegistryImpl cardRegistry;

	private Main(@Nonnull String[] args) throws RemoteException
	{
		super(args, OPTIONS_CONFIG);
		createRegistry();
		setDelay();
		cardRegistry = new CardRegistryImpl();
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
			printBalances();
		} while(!"x".equals(line));
		shutdown();
		System.out.println("Server exit.");
		System.exit(0);
	}

	private void printBalances()
	{
		for (Card card : cardRegistry.getCards()) {
			System.out.println(String.format("C:%s:%s:%.2f", card.getCardHolder(),card.getLabel(), cardRegistry.getCardBalance(card.getId())));
		}
	}

	public static void shutdown()
	{
		main.unbindObject(CARD_REGISTRY_BIND);
		main.printBalances();
	}

	private static final String[][] OPTIONS_CONFIG = {
			new String[]{PORT_O_S, PORT_O_L, TRUE},
			new String[]{DELAY_O_S, DELAY_O_L, TRUE},
			new String[]{MAX_THREADS_O_S, MAX_THREADS_O_L, TRUE
			}};
}

