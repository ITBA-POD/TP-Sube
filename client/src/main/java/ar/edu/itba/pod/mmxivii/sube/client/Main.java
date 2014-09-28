package ar.edu.itba.pod.mmxivii.sube.client;

import ar.edu.itba.pod.mmxivii.sube.common.BaseMain;
import ar.edu.itba.pod.mmxivii.sube.common.CardClient;

import javax.annotation.Nonnull;

import java.rmi.NotBoundException;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.*;

public class Main extends BaseMain
{
	private CardClient cardClient = null;

	private Main(@Nonnull String[] args)
	{
		super(args, DEFAULT_CLIENT_OPTIONS);
		getRegistry();
		cardClient = lookupObject(CARD_CLIENT_BIND);
	}

	public static void main(@Nonnull String[] args ) throws Exception
	{
		final Main main = new Main(args);
		main.run();
	}

	private void run() throws NotBoundException
	{
		System.out.println("Main.run");
//		cardClient.newCard()
	}
}
