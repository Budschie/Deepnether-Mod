package de.budschie.deepnether.dimension;

public interface IInterpolationApplier<E>
{
	E apply(E[][] sampledArea, int currentX, int currentZ);
}
