package de.budschie.deepnether.entity.damagesource;

import java.util.Random;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DamageSourceLiveSucking extends DamageSource
{


	public DamageSourceLiveSucking() 
	{
		super("death.attack.block.soulblock.player");
	}
	
	@Override
	public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) 
	{
		int posX = entityLivingBaseIn.getPosition().getX();
		int posY = entityLivingBaseIn.getPosition().getY();
		int posZ = entityLivingBaseIn.getPosition().getZ();
		
		System.out.println("Seed was: " + Math.round((posX < 0 ? posX*-1 : posX) + (posZ < 0 ? posZ*-1 : posZ) * posY));
		System.out.println("Output should be: " + new Random(Math.round((posX < 0 ? posX*-1 : posX) + (posZ < 0 ? posZ*-1 : posZ) * posY)).nextInt(50));

		if(new Random(Math.round((posX < 0 ? posX*-1 : posX) + (posZ < 0 ? posZ*-1 : posZ) * posY)).nextInt(50) >= 25)
		{
			return new StringTextComponent(entityLivingBaseIn.getName() + " life was absorbed.");
		}
		else
		{
			return new StringTextComponent(entityLivingBaseIn.getName() + " is from now on an empty shell.");
		}
	}
}
