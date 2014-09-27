package ar.edu.itba.pod.mmxivii.sube.common;

import javax.annotation.Nonnull;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface CardServiceRegistry extends Remote
{
	void registerService(@Nonnull CardService service) throws RemoteException;

	void unRegisterService(@Nonnull CardService service) throws RemoteException;

	Collection<CardService> getServices() throws RemoteException;
}
