package ar.edu.itba.pod.mmxivii.sube.balancer;

import ar.edu.itba.pod.mmxivii.sube.common.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.rmi.RemoteException;
import java.rmi.server.UID;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.checkNotNull;
import static ar.edu.itba.pod.mmxivii.sube.common.Utils.delay;

public class CardClientImpl implements CardClient
{
	private final CardRegistry cardRegistry;
	private final CardServiceRegistryImpl cardServiceRegistry;

	public CardClientImpl(@Nonnull CardRegistry cardRegistry, @Nonnull CardServiceRegistryImpl cardServiceRegistry)
	{
		this.cardRegistry = cardRegistry;
		this.cardServiceRegistry = cardServiceRegistry;
	}

	@Nonnull
	@Override
	public Card newCard(@Nonnull String cardHolder, @Nonnull String label) throws RemoteException
	{
		delay();
		return cardRegistry.newCard(cardHolder,label);
	}

	@Nullable
	@Override
	public Card getCard(@Nonnull UID id) throws RemoteException
	{
		delay();
		return cardRegistry.getCard(checkNotNull(id));
	}

	@Override
	public double getCardBalance(@Nonnull UID cardId) throws RemoteException
	{
		return getCardService().getCardBalance(cardId);
	}

	@Override
	public double travel(@Nonnull UID cardId, @Nonnull String description, double amount) throws RemoteException
	{
		return getCardService().travel(cardId, description, amount);
	}

	@Override
	public double recharge(@Nonnull UID cardId, @Nonnull String description, double amount) throws RemoteException
	{
		// @ToDo catch de excepciones
		return getCardService().recharge(cardId, description, amount);
	}

	private CardService getCardService()
	{
		return cardServiceRegistry.getCardService();
	}
}
