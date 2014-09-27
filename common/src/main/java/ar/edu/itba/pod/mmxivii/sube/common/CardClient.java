package ar.edu.itba.pod.mmxivii.sube.common;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.rmi.RemoteException;
import java.rmi.server.UID;

public interface CardClient extends CardService
{
	/**
	 *
	 * @param cardHolder el usuario dueño de la tarjeta, tiene que ser el username del alumno, así distinguimos quien está usando el sistema
	 * @param label un texto libre para distinguir a la tarjeta
	 * @return
	 */
	@Nonnull
	Card newCard(@Nonnull String cardHolder, @Nonnull String label) throws RemoteException;

	/**
	 *
	 * @param id el identificador de la tarjeta
	 * @return los datos de la tarjeta o null si no se encuentra la tarjeta
	 */
	@Nullable
	Card getCard(@Nonnull UID id) throws RemoteException;
}
