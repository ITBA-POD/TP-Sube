package ar.edu.itba.pod.mmxivii.sube.service;

import ar.edu.itba.pod.mmxivii.sube.common.CardRegistry;
import ar.edu.itba.pod.mmxivii.sube.common.CardService;

import javax.annotation.Nonnull;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;

public class CardServiceImpl extends UnicastRemoteObject implements CardService
{
	private static final long serialVersionUID = 2919260533266908792L;
	@Nonnull
	private final CardRegistry cardRegistry;

	public CardServiceImpl(@Nonnull CardRegistry cardRegistry) throws RemoteException
	{
		super(0);
		this.cardRegistry = cardRegistry;
	}

	@Override
	public double getCardBalance(@Nonnull UID id) throws RemoteException
	{
		return cardRegistry.getCardBalance(id);
	}

	@Override
	public double travel(@Nonnull UID id, @Nonnull String description, double amount) throws RemoteException
	{
		return cardRegistry.addCardOperation(id, description, amount * -1);
	}

	@Override
	public double recharge(@Nonnull UID id, @Nonnull String description, double amount) throws RemoteException
	{
		return cardRegistry.addCardOperation(id, description, amount);
	}
}
