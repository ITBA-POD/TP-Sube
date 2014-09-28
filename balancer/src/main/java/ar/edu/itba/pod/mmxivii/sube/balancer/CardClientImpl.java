package ar.edu.itba.pod.mmxivii.sube.balancer;

import ar.edu.itba.pod.mmxivii.sube.common.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.checkNotNull;
import static ar.edu.itba.pod.mmxivii.sube.common.Utils.delay;

public class CardClientImpl extends UnicastRemoteObject implements CardClient
{
	private static final long serialVersionUID = 3498345765116694167L;
	private final CardRegistry cardRegistry;
	private final CardServiceRegistryImpl cardServiceRegistry;

	public CardClientImpl(@Nonnull CardRegistry cardRegistry, @Nonnull CardServiceRegistryImpl cardServiceRegistry) throws RemoteException
	{
		super();
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
	public double getCardBalance(@Nonnull UID id) throws RemoteException
	{
		return getCardService().getCardBalance(id);
	}

	@Override
	public double travel(@Nonnull UID id, @Nonnull String description, double amount) throws RemoteException
	{
		return getCardService().travel(id, description, amount);
	}

	@Override
	public double recharge(@Nonnull UID id, @Nonnull String description, double amount) throws RemoteException
	{
		// @ToDo catch de excepciones
		return getCardService().recharge(id, description, amount);
	}

	private CardService getCardService()
	{
		return cardServiceRegistry.getCardService();
	}
}
