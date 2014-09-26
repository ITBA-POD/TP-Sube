package ar.edu.itba.pod.mmxivii.sube;

import ar.edu.itba.pod.mmxivii.sube.common.CardRegistry;
import ar.edu.itba.pod.mmxivii.sube.common.Utils;
import ar.edu.itba.pod.mmxivii.sube.common.model.Card;
import ar.edu.itba.pod.mmxivii.sube.server.CardRegistryImpl;
import com.googlecode.junittoolbox.MultithreadingTester;
import com.googlecode.junittoolbox.RunnableAssert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.rmi.server.UID;

import static ar.edu.itba.pod.mmxivii.sube.common.TestUtils.*;
import static org.assertj.core.api.Assertions.*;

public class CardRegistryTest
{
	private CardRegistry cardRegistry = null;

	public CardRegistryTest() {}

	@Before
	public void setUp() throws Exception
	{
		cardRegistry = new CardRegistryImpl();
		Utils.skipDelay(true);
	}

	@Test public void baseTest()
	{
		try {
			final Card card = cardRegistry.newCard(TEST_CARD_HOLDER, TEST_LABEL);
			assertThat(card).isNotNull();
			assertThat(card.getCardHolder()).isEqualTo(TEST_CARD_HOLDER);

			final double balance = cardRegistry.getCardBalance(card.getId());
			assertThat(balance).isEqualTo(0d);

			final Card other = cardRegistry.getCard(card.getId());
			assertThat(other).isEqualTo(card);

			final double value = 44d;
			final double newValue = cardRegistry.addCardOperation(card.getId(), "test", value);
			assertThat(newValue).isEqualTo(value);

			final double newBalance = cardRegistry.getCardBalance(card.getId());
			assertThat(newBalance).isEqualTo(value);

			final UID invalidId = new UID();
			assertThat(cardRegistry.getCard(invalidId)).isNull();
			assertThat(cardRegistry.getCardBalance(invalidId)).isEqualTo(CardRegistry.CARD_NOT_FOUND);
		} catch (RemoteException ignored) {}
	}

	@Test public void concurrencyBaseTest()
	{
		new MultithreadingTester().add(new RunnableAssert("testing")
		{
			@Override
			public void run() throws Exception
			{
				final String cardHolder = randomString(TEST_CARD_HOLDER);
				final String label = randomString(TEST_LABEL);
				final Card card = cardRegistry.newCard(cardHolder, label);

				assertThat(card.getCardHolder()).isEqualTo(cardHolder);
				assertThat(card.getLabel()).isEqualTo(label);

				final Card other = cardRegistry.getCard(card.getId());
				assertThat(other).isEqualTo(card);

				double balance = cardRegistry.getCardBalance(card.getId());
				assertThat(balance).isEqualTo(0d);

				for (int i = 0; i < 10; i++) {
					final double newBalance = cardRegistry.addCardOperation(card.getId(), "nada", i);
					assertThat(newBalance).isEqualTo(balance + i);
					assertThat(cardRegistry.getCardBalance(card.getId())).isEqualTo(newBalance);
					balance = newBalance;
				}
			}
		}).run();
	}
}
