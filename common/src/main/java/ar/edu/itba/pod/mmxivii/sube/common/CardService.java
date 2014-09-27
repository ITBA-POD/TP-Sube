package ar.edu.itba.pod.mmxivii.sube.common;

import javax.annotation.Nonnull;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UID;

public interface CardService extends Remote
{
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
	 * @param amount el valor de la operación, debe ser positivo, tiene que ser un valor monetario válido
	 * @return el saldo de la tarjeta luego de realizar la operacion si es un valor mayor o igual a cero, un valor negativo indica un error
	 * @see #CARD_NOT_FOUND
	 * @see #CANNOT_PROCESS_REQUEST
	 * @see #COMMUNICATIONS_FAILURE
	 * @see #OPERATION_NOT_PERMITTED_BY_BALANCE
	 * @see #SERVICE_TIMEOUT
	 */
	double travel(@Nonnull UID cardId, @Nonnull String description, double amount) throws RemoteException;

	/**
	 *
	 * @param id el identificador de la tarjeta
	 * @param amount el valor de la operación, debe ser positivo, tiene que ser un valor monetario válido
	 * @return el saldo de la tarjeta luego de realizar la operacion si es un valor mayor o igual a cero, un valor negativo indica un error
	 * @see #CARD_NOT_FOUND
	 * @see #CANNOT_PROCESS_REQUEST
	 * @see #COMMUNICATIONS_FAILURE
	 * @see #OPERATION_NOT_PERMITTED_BY_BALANCE
	 * @see #SERVICE_TIMEOUT
	 */
	double recharge(@Nonnull UID cardId, @Nonnull String description, double amount) throws RemoteException;

}
