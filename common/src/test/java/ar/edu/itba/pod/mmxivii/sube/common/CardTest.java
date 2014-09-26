package ar.edu.itba.pod.mmxivii.sube.common;

import ar.edu.itba.pod.mmxivii.sube.common.model.Card;
import org.junit.Test;

import java.rmi.server.UID;

import static ar.edu.itba.pod.mmxivii.sube.common.TestUtils.assertInvalidAmount;
import static ar.edu.itba.pod.mmxivii.sube.common.TestUtils.assertValidAmount;
import static org.assertj.core.api.Assertions.*;

public class CardTest
{

	public CardTest() {}

	@Test public void baseTest()
	{
		final UID id = new UID();
		final String cardHolder = TestUtils.TEST_CARD_HOLDER;
		final String label = TestUtils.TEST_LABEL;
		final Card card = new Card(id, cardHolder, label);
		assertThat(card.getId()).isEqualTo(id);
		assertThat(card.getCardHolder()).isEqualTo(cardHolder);
		assertThat(card.getLabel()).isEqualTo(label);

		final Card other = new Card(id, cardHolder, label);
		assertThat(other).isEqualTo(card);
		assertThat(other.getId()).isEqualTo(id);
	}

	@SuppressWarnings("ConstantConditions")
	@Test public void nullTest()
	{
		final UID id = new UID();
		final String cardHolder = TestUtils.TEST_CARD_HOLDER;
		final String label = TestUtils.TEST_LABEL;
		try {
			new Card(null, cardHolder, label);
			failBecauseExceptionWasNotThrown(NullPointerException.class);
		} catch (Exception ignore) {}
		try {
			new Card(id, null, label);
			failBecauseExceptionWasNotThrown(NullPointerException.class);
		} catch (Exception ignore) {}
		try {
			new Card(id, cardHolder, null);
			failBecauseExceptionWasNotThrown(NullPointerException.class);
		} catch (Exception ignore) {}
	}

	@Test public void invalidValuesTest()
	{
		final UID id = new UID();
		try {
			final String label = TestUtils.TEST_LABEL;
			new Card(id, TestUtils.randomInvalidString(), label);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException ignore) {}
		try {
			final String cardHolder = TestUtils.TEST_CARD_HOLDER;
			new Card(id, cardHolder, TestUtils.randomInvalidString());
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException ignore) {}
	}

	@Test public void amountTest()
	{
		assertValidAmount(0d);
		assertValidAmount(1d);
		assertValidAmount(2d);
		assertValidAmount(0.1d);
		assertValidAmount(1.22d);

		assertInvalidAmount(-1.001d);
		assertInvalidAmount(0.001d);
		assertInvalidAmount(100.001d);
		assertInvalidAmount(-100.001d);
		assertInvalidAmount(-100.000000001d);
		assertInvalidAmount(100.000000001d);
	}
}