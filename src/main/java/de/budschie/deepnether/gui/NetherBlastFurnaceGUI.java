package de.budschie.deepnether.gui;

import de.budschie.deepnether.container.DeepNetherBlastFurnaceContainer;
import de.budschie.deepnether.main.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class NetherBlastFurnaceGUI extends ContainerScreen<DeepNetherBlastFurnaceContainer>
{
	public static final ResourceLocation BLAST_FURNACE_DEEPNETHER_GUI_TEXTURES = new ResourceLocation(References.MODID, "textures/gui/container/deepnether_blast_furnace.png");
	
	public NetherBlastFurnaceGUI(DeepNetherBlastFurnaceContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		super(screenContainer, inv, titleIn);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		this.renderBackground();
        Minecraft.getInstance().getTextureManager().bindTexture(BLAST_FURNACE_DEEPNETHER_GUI_TEXTURES);
        this.blit(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        
        /*
        for(Slot slot : this.getContainer().inventorySlots)
        {
        	Minecraft.getInstance().fontRenderer.drawString(Integer.valueOf(slot.getSlotIndex()).toString(), slot.xPos + i, slot.yPos + j, 1399464);
        }
        */
	}
	
	public static final IScreenFactory<DeepNetherBlastFurnaceContainer, NetherBlastFurnaceGUI> FACTORY = new IScreenFactory<DeepNetherBlastFurnaceContainer, NetherBlastFurnaceGUI>()
	{
		
		@Override
		public NetherBlastFurnaceGUI create(DeepNetherBlastFurnaceContainer p_create_1_, PlayerInventory p_create_2_, ITextComponent p_create_3_)
		{
			return new NetherBlastFurnaceGUI(p_create_1_, p_create_2_, p_create_3_);
		}
	};
}
