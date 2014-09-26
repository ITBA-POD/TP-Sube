package ar.edu.itba.pod.mmxivii.sube.server;

import ar.edu.itba.pod.mmxivii.sube.common.CardRegistry;
import ar.edu.itba.pod.mmxivii.sube.common.model.Card;

import javax.annotation.Nonnull;
import java.rmi.server.UID;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.assertText;

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
			System.out.println(String.format("NEW CARD: %s:%s%s", cardHolder, label, id));

			balances.put(id, 0d);
			return cards.put(id, new Card(id, cardHolder, label));
		}
	}

	@Override
	public double getCardBalance(@Nonnull UID cardId)
	{

		return 0;
	}

	@Override
	public double addCardOperation(@Nonnull UID cardId, @Nonnull String description, double amount)
	{
		return 0;
	}

}
