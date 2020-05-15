package de.budschie.deepnether.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class ModItemStackHandler extends ItemStackHandler
{
    public ModItemStackHandler()
    {
    	super();
    }

    public ModItemStackHandler(int size)
    {
        super(size);
    }

    public ModItemStackHandler(NonNullList<ItemStack> stacks)
    {
        super(stacks);
    }
    
	public NonNullList<ItemStack> getNonNullList()
	{
		return stacks;
	} 
	
	protected void onContentsChanged(int slot, boolean silent)
	{}
	
	public void setStackInSlot(int slot, ItemStack stack, boolean silent) 
	{
        validateSlotIndex(slot);
        this.stacks.set(slot, stack);
        onContentsChanged(slot, silent);
	};
}
