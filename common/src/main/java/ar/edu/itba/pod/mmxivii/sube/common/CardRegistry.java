package ar.edu.itba.pod.mmxivii.sube.common;

import ar.edu.itba.pod.mmxivii.sube.common.model.Card;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UID;

public interface CardRegistry extends Remote
{
	double CARD_NOT_FOUND = -1;
	double CANNOT_PROCESS_REQUEST = -2;
	double COMMUNICATIONS_FAILURE = -3;
	double OPERATION_NOT_PERMITTED_BY_BALANCE = -4;
	double SERVICE_TIMEOUT = -5;

	double MAX_BALANCE = 100d;

	/**
	 *
	 * @param cardHolder el usuario dueño de la tarjeta, tiene que ser el username del alumno, así distinguimos quien está usando el sistema
	 * @param label un texto libre para distinguir a la tarjeta
	 * @return
	 */
	@Nonnull Card newCard(@Nonnull String cardHolder, @Nonnull String label) throws RemoteException;

	/**
	 *
	 * @param id el identificador de la tarjeta
	 * @return los datos de la tarjeta o null si no se encuentra la tarjeta
	 */
	@Nullable
	Card getCard(@Nonnull UID id) throws RemoteException;

	/**
	 *
	 * @param id el identificador de la tarjeta
	 * @return el saldo de la tarjeta si es un valor mayor o igual a cero, un valor negativo indica un error
	 * @see #CARD_NOT_FOUND
	 * @see #CANNOT_PROCESS_REQUEST
	 * @see #COMMUNICATIONS_FAILURE
	 * @see #SERVICE_TIMEOUT
	 */
	double getCardBalance(@Nonnull UID cardId) throws RemoteException;

	/**
	 *
	 * @param id el identificador de la tarjeta
	 * @param amount el valor de la operación, puede ser positivo o negativo, tiene que ser un valor monetario válido
	 * @return el saldo de la tarjeta luego de realizar la operacion si es un valor mayor o igual a cero, un valor negativo indica un error
	 * @see #CARD_NOT_FOUND
	 * @see #CANNOT_PROCESS_REQUEST
	 * @see #COMMUNICATIONS_FAILURE
	 * @see #OPERATION_NOT_PERMITTED_BY_BALANCE
	 * @see #SERVICE_TIMEOUT
	 */
	double addCardOperation(@Nonnull UID cardId, @Nonnull String description, double amount) throws RemoteException;
}
