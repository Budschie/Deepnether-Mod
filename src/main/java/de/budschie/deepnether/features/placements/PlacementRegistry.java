package de.budschie.deepnether.features.placements;

import de.budschie.deepnether.main.References;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PlacementRegistry
{
	public static final DeferredRegister<Placement<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.DECORATORS, References.MODID);
	
	public static final RegistryObject<ScatteredHeightmapPlacement> SCATTERED_HEIGHTMAP_PLACEMENT = REGISTRY.register("scattered_heightmap_placement", ScatteredHeightmapPlacement::new);
}
