package de.budschie.deepnether.container;

import de.budschie.deepnether.main.References;
import de.budschie.deepnether.tileentities.DeepNetherBlastFurnaceTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(bus = Bus.MOD)
public class ContainerRegistry
{
	@SubscribeEvent
	public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event)
	{
		IForgeRegistry<ContainerType<?>> registry = event.getRegistry();
		
		registry.register(new ContainerType<DeepNetherBlastFurnaceContainer>(new IContainerFactory<DeepNetherBlastFurnaceContainer>()
		{

			@Override
			public DeepNetherBlastFurnaceContainer create(int windowId, PlayerInventory inv, PacketBuffer data)
			{
				if(data != null)
				{
					BlockPos tePos = new BlockPos(data.readInt(), data.readInt(), data.readInt());
					TileEntity te = inv.player.getEntityWorld().getTileEntity(tePos);
					
					if(te != null && te instanceof DeepNetherBlastFurnaceTileEntity)
					{
						return new DeepNetherBlastFurnaceContainer(windowId, inv, ((DeepNetherBlastFurnaceTileEntity)te));
					}
				}
				
				return new DeepNetherBlastFurnaceContainer(windowId, inv);
			}
			
		}).setRegistryName(new ResourceLocation(References.MODID, "nether_blast_furnace_container")));
	}
}
