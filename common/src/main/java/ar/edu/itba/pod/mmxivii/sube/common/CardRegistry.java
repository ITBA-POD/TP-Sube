package ar.edu.itba.pod.mmxivii.sube.common;

import ar.edu.itba.pod.mmxivii.sube.common.model.Card;

import javax.annotation.Nonnull;
import java.rmi.Remote;
import java.rmi.server.UID;

public interface CardRegistry extends Remote
{
	/**
	 *
	 * @param cardHolder el usuario dueño de la tarjeta, tiene que ser el username del alumno, así distinguimos quien está usando el sistema
	 * @param label un texto libre para distinguir a la tarjeta
	 * @return
	 */
	@Nonnull Card newCard(@Nonnull String cardHolder, @Nonnull String label);

	double getCardBalance(@Nonnull UID cardId);

	double addCardOperation(@Nonnull UID cardId, @Nonnull String description, double amount);
}
