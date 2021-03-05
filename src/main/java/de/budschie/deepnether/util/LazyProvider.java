package de.budschie.deepnether.util;

import java.util.Optional;
import java.util.function.Supplier;

public class LazyProvider<A>
{
	private Optional<A> cached = Optional.empty();
	private Supplier<A> aSupplier;
	
	public LazyProvider(Supplier<A> aSupplier)
	{
		this.aSupplier = aSupplier;
	}
	
	public A get()
	{
		if(cached.isEmpty())
			cached = Optional.of(aSupplier.get());
		
		return cached.get();
	}
}
