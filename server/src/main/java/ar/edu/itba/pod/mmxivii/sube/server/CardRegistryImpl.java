package ar.edu.itba.pod.mmxivii.sube.server;

import ar.edu.itba.pod.mmxivii.sube.common.CardRegistry;
import ar.edu.itba.pod.mmxivii.sube.common.model.Card;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.rmi.server.UID;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.assertText;
import static ar.edu.itba.pod.mmxivii.sube.common.Utils.checkNotNull;

public class CardRegistryImpl implements CardRegistry
{
	private final Map<UID, Card> cards = Collections.synchronizedMap(new HashMap<UID, Card>());
	private final Map<UID, Double> balances = Collections.synchronizedMap(new HashMap<UID, Double>());
	public CardRegistryImpl()
	{
		System.out.println("hola");
	}

	@Nonnull
	@Override
	public Card newCard(@Nonnull String cardHolder, @Nonnull String label)
	{
		assertText(cardHolder);
		assertText(label);
		synchronized (cards) {
			UID id = new UID();
			while (cards.containsKey(id)) id = new UID();
//			System.out.println(String.format("NEW CARD: %s:%s%s", cardHolder, label, id));

			balances.put(id, 0d);
			final Card card = new Card(id, cardHolder, label);
			cards.put(id, card);
			return card;
		}
	}

	@Override
	@Nullable
	public Card getCard(@Nonnull UID id)
	{
		return cards.get(checkNotNull(id));
	}

	@Override
	public double getCardBalance(@Nonnull UID id)
	{
		return balances.get(checkNotNull(id));
	}

	@Override
	public double addCardOperation(@Nonnull UID cardId, @Nonnull String description, double amount)
	{
		return 0;
	}

}
