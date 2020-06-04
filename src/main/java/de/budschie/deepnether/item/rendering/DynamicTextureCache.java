package de.budschie.deepnether.item.rendering;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import de.budschie.deepnether.item.toolModifiers.IToolUsableItem;
import de.budschie.deepnether.item.toolModifiers.IToolUsableItem.Part;
import de.budschie.deepnether.main.DeepnetherMain;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.SimpleTexture.TextureData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraftforge.common.ToolType;

public class DynamicTextureCache
{
	private HashMap<String, HashMap<String, DynamicTexture>> cache = new HashMap<>();
	
	public DynamicTexture add(IToolUsableItem stick, IToolUsableItem head, ToolType toolType)
	{
		if(!cache.containsKey(stick.getBoundItem()) || !cache.get(stick.getBoundItem()).containsKey(head.getBoundItem()))
		{
			try
			{
				NativeImage tex = NativeImage.read(DeepnetherMain.class.getResourceAsStream("/assets/deepnether/textures/item/toolparts/" + stick.getImage(Part.STICK, toolType) + ".png"));
				NativeImage img = NativeImage.read(DeepnetherMain.class.getResourceAsStream("/assets/deepnether/textures/item/toolparts/" + head.getImage(Part.HEAD, toolType) + ".png"));
								
				for(int x = 0; x < img.getWidth(); x++)
				{
					for(int y = 0; y < img.getHeight(); y++)
					{
						if(NativeImage.getAlpha(img.getPixelRGBA(x, y)) > 1)
						{
							System.out.println("SET PIXEL");
							tex.setPixelRGBA(x, y, img.getPixelRGBA(x, y));
						}
					}
				}
				
				DynamicTexture dyntex = new DynamicTexture(tex);
				
				if(cache.containsKey(stick.getBoundItem()))
				{
					cache.get(stick.getBoundItem()).put(head.getBoundItem(), dyntex);
				}
				else
				{
					cache.put(stick.getBoundItem(), new HashMap<>());
					cache.get(stick.getBoundItem()).put(head.getBoundItem(), dyntex);
				}
				return dyntex;
			} catch (Exception e)
			{
				System.out.println("An error occured: ");
				System.out.println("Images that should be loaded, but are not:");
				System.out.println("/assets/deepnether/textures/item/toolparts/" + stick.getImage(Part.STICK, toolType) + ".png");
				System.out.println("/assets/deepnether/textures/item/toolparts/" + head.getImage(Part.HEAD, toolType) + ".png");
				throw new RuntimeException(e);
			}
		}
		else
			return cache.get(stick.getBoundItem()).get(head.getBoundItem());
	}
}
