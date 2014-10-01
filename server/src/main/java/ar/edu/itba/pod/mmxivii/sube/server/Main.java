package ar.edu.itba.pod.mmxivii.sube.server;

import ar.edu.itba.pod.mmxivii.sube.common.BaseMain;
import ar.edu.itba.pod.mmxivii.sube.common.Card;

import javax.annotation.Nonnull;
import java.io.*;
import java.rmi.RemoteException;
import java.util.Scanner;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.*;

public class Main extends BaseMain
{
	private static Main main = null;
	private final CardRegistryImpl cardRegistry;
	private final String storeFile;

	private Main(@Nonnull String[] args) throws RemoteException
	{
		super(args, OPTIONS_CONFIG);
		getRegistry();
		setDelay();

		storeFile = cmdLine.getOptionValue(STORE_FILE_O_L, STORE_FILE_O_D);
		cardRegistry = getOrCreateCardRegistry();
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

	private CardRegistryImpl getOrCreateCardRegistry() throws RemoteException
	{
		final File file = new File(storeFile);
		if (file.exists()) {
			try (
					final FileInputStream fout = new FileInputStream(storeFile);
					final ObjectInputStream oos = new ObjectInputStream(fout)
			) {
				return (CardRegistryImpl) oos.readObject();
			} catch(IOException | ClassNotFoundException e) {
				//noinspection CallToPrintStackTrace
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		} else {
			return new CardRegistryImpl();
		}
	}

	private void writeFile()
	{
		try (
				final FileOutputStream fout = new FileOutputStream(storeFile);
				final ObjectOutputStream oos = new ObjectOutputStream(fout)
		) {
			oos.writeObject(cardRegistry);
		} catch(IOException e) {
			//noinspection CallToPrintStackTrace
			e.printStackTrace();
		}
	}

	public static void shutdown()
	{
		main.unbindObject(CARD_REGISTRY_BIND);
		main.printBalances();
		main.writeFile();
	}

	private static final String STORE_FILE_O_S = "f";
	private static final String STORE_FILE_O_L = "store-file";
	private static final String STORE_FILE_O_D = "cards.data";
	private static final String[][] OPTIONS_CONFIG = {
			new String[]{HOST_O_S, HOST_O_L, TRUE},
			new String[]{PORT_O_S, PORT_O_L, TRUE},
			new String[]{STORE_FILE_O_S, STORE_FILE_O_L, TRUE},
			new String[]{MAX_THREADS_O_S, MAX_THREADS_O_L, TRUE
			}};
}

