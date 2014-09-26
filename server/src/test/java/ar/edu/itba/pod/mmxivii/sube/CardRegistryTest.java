package ar.edu.itba.pod.mmxivii.sube;

import ar.edu.itba.pod.mmxivii.sube.common.CardRegistry;
import ar.edu.itba.pod.mmxivii.sube.common.model.Card;
import ar.edu.itba.pod.mmxivii.sube.server.CardRegistryImpl;
import com.googlecode.junittoolbox.MultithreadingTester;
import com.googlecode.junittoolbox.RunnableAssert;
import org.junit.Test;

import static ar.edu.itba.pod.mmxivii.sube.common.TestUtils.*;
import static org.assertj.core.api.Assertions.*;

public class CardRegistryTest
{

	public CardRegistryTest() {}

	@Test public void baseTest()
	{
		final CardRegistry cardRegistry = new CardRegistryImpl();
		final Card card = cardRegistry.newCard(TEST_CARD_HOLDER, TEST_LABEL);
		assertThat(card).isNotNull();
		assertThat(card.getCardHolder()).isEqualTo(TEST_CARD_HOLDER);

		final double balance = cardRegistry.getCardBalance(card.getId());
		assertThat(balance).isEqualTo(0d);

		final Card other = cardRegistry.getCard(card.getId());
		assertThat(other).isEqualTo(card);

	}

	@Test public void concurrencyBaseTest()
	{
		final CardRegistry cardRegistry = new CardRegistryImpl();
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
			}
		}).numThreads(20).run();
	}
}
