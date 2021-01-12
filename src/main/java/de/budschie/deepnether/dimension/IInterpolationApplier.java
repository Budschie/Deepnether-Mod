package de.budschie.deepnether.dimension;

public interface IInterpolationApplier<E>
{
	E apply(E upperLeft, E upperRight, E bottomLeft, E bottomRight);
}
