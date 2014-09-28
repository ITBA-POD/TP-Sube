package ar.edu.itba.pod.mmxivii.sube.server;

import ar.edu.itba.pod.mmxivii.sube.common.CardRegistry;
import ar.edu.itba.pod.mmxivii.sube.common.Card;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.*;

public class CardRegistryImpl extends UnicastRemoteObject implements CardRegistry
{
	private static final long serialVersionUID = 2990708481011754484L;
	private final ConcurrentHashMap<UID, Card> cards = new ConcurrentHashMap<UID, Card>();
	private final ConcurrentHashMap<UID, Double> balances = new ConcurrentHashMap<UID, Double>();

	public CardRegistryImpl() throws RemoteException
	{
		super();
	}

	@Nonnull
	@Override
	public Card newCard(@Nonnull String cardHolder, @Nonnull String label)
	{
		assertText(cardHolder);
		assertText(label);
		final UID id = new UID();
		balances.put(id, 0d);
		final Card card = new Card(id, cardHolder, label);
		cards.put(id, card);
		delay();
		return card;
	}

	@Override
	@Nullable
	public Card getCard(@Nonnull UID id)
	{
		delay();
		return cards.get(checkNotNull(id));
	}

	@Override
	public double getCardBalance(@Nonnull UID id)
	{
		delay();
		final Double result = balances.get(checkNotNull(id));
		return result == null ? CARD_NOT_FOUND : result;
	}

	@Override
	public double addCardOperation(@Nonnull UID id, @Nonnull String description, double amount)
	{
		assertAmount(amount);
		assertText(description);
		delay();
		synchronized (balances) {
			Double result = balances.get(checkNotNull(id));
			if (result == null) return CARD_NOT_FOUND;

			result = result + amount;
			if (result < 0 || result > MAX_BALANCE) return OPERATION_NOT_PERMITTED_BY_BALANCE;
			balances.put(id, result);
			return result;
		}
	}

	Collection<Card> getCards()
	{
		return cards.values();
	}
}
