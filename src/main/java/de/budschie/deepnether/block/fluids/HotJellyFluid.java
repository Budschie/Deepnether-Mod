package de.budschie.deepnether.block.fluids;

import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.main.References;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class HotJellyFluid extends ForgeFlowingFluid
{

	protected HotJellyFluid()
	{
		super(new Properties(() -> FluidInit.HOT_JELLY_FLUID.get(), () -> FluidInit.HOT_JELLY_FLOWING_FLUID.get(), FluidAttributes.builder(new ResourceLocation(References.MODID, "block/jellies/hot_jelly_still"), new ResourceLocation(References.MODID, "block/jellies/hot_jelly_flowing")).density(200).temperature(500).viscosity(100)).tickRate(5).block(() -> BlockInit.HOT_JELLY_FLUID_BLOCK));
	}

	@Override
	public boolean isSource(FluidState state)
	{
		return false;
	}
	
	@Override
	protected int getLevelDecreasePerBlock(IWorldReader worldIn)
	{
		return 1;
	}

	@Override
	public int getLevel(FluidState p_207192_1_)
	{
		return 0;
	}
	
	@Override
	public int getTickRate(IWorldReader world)
	{
		return super.getTickRate(world);
	}
	
	public static class Source extends HotJellyFluid
	{
		public Source()
		{
			super();
		}


        public int getLevel(FluidState state) 
        {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
	}
	
	public static class Flowing extends HotJellyFluid
	{
		public Flowing()
		{
			super();
            setDefaultState(getStateContainer().getBaseState().with(LEVEL_1_8, 7));
		}


        protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        public int getLevel(FluidState state) {
            return state.get(LEVEL_1_8);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
	}
}
