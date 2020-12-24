package de.budschie.deepnether.item;

import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class ItemEvents
{
	@SubscribeEvent
	public static void onHitEntity(LivingHurtEvent event)
	{
		if(event.getEntityLiving() instanceof PlayerEntity)
		{
			byte hotCount = 0;
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();
			
			armorLoop:
			for(ItemStack iStack : player.getArmorInventoryList())
			{
				if(iStack.getItem() instanceof ArmorItem)
				{
					ArmorItem armorItem = (ArmorItem) iStack.getItem();
					if(armorItem.getArmorMaterial() instanceof ModArmorMaterial)
					{
						ModArmorMaterial armorMat = (ModArmorMaterial) armorItem.getArmorMaterial();
						
						if(armorMat.getDescription().contains(ToolMaterialDescriptionElement.HOT))
						{
							hotCount++;
						}
						else break armorLoop;
					}
					else break armorLoop;
				}
				else break armorLoop;
			}
			
			if(hotCount >= 4 && event.getSource().isFireDamage())
			{
				event.setCanceled(true);
			}
		}
		if(!event.getSource().isFireDamage() && event.getSource().getTrueSource() != null)
		{
			// System.out.println(event.getSource().getTrueSource().getClass().getName() + " has attacked " + event.getEntity().getClass().getName());
			if(event.getSource().getTrueSource() instanceof PlayerEntity)
			{
				PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
				
				ItemStack attackedWithIStack = player.getHeldItem(player.getActiveHand());
				
				Item itemAttackedWith = attackedWithIStack.getItem();
				
				if(itemAttackedWith instanceof SwordItem && ((SwordItem)itemAttackedWith).getTier() instanceof ModItemTier)
				{
					ModItemTier attackedTier = (ModItemTier) ((SwordItem)itemAttackedWith).getTier();
					
					if(attackedTier.getDescription().contains(ToolMaterialDescriptionElement.HOT))
					{
						if(event.getEntityLiving().getClass() == CreeperEntity.class)
						{
						}
						else
						{
							event.getEntityLiving().setFire(2);
						}
					}
				}
			}
		}
	}
}
