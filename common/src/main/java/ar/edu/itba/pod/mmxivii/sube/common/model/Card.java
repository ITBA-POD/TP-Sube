package ar.edu.itba.pod.mmxivii.sube.common.model;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.rmi.server.UID;

import static ar.edu.itba.pod.mmxivii.sube.common.Utils.*;

public class Card implements Serializable
{
	@Nonnull UID id;
	@Nonnull String cardHolder;
	@Nonnull String label;

	public Card(@Nonnull UID id, @Nonnull String cardHolder, @Nonnull String label)
	{
		this.id = checkNotNull(id);
		this.cardHolder = assertText(checkNotNull(cardHolder));
		this.label = assertText(checkNotNull(label));
	}

	@Nonnull
	public UID getId()
	{
		return id;
	}

	@Nonnull
	public String getCardHolder()
	{
		return cardHolder;
	}

	@Nonnull
	public String getLabel()
	{
		return label;
	}

	@Override
	public boolean equals(Object o)
	{
		return this == o || !(o == null || getClass() != o.getClass()) && id.equals(((Card) o).id);
	}

	@Override
	public int hashCode()
	{
		return id.hashCode();
	}

	private static final long serialVersionUID = -3901673916831330741L;
}
