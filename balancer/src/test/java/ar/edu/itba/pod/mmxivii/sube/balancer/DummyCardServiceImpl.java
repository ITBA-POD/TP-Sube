package ar.edu.itba.pod.mmxivii.sube.balancer;

import ar.edu.itba.pod.mmxivii.sube.common.CardService;

import javax.annotation.Nonnull;
import java.rmi.RemoteException;
import java.rmi.server.UID;

public class DummyCardServiceImpl implements CardService
{
	@Override
	public double getCardBalance(@Nonnull UID cardId) throws RemoteException
	{
		return 0;
	}

	@Override
	public double travel(@Nonnull UID cardId, @Nonnull String description, double amount) throws RemoteException
	{
		return 0;
	}

	@Override
	public double recharge(@Nonnull UID cardId, @Nonnull String description, double amount) throws RemoteException
	{
		return 0;
	}
}
