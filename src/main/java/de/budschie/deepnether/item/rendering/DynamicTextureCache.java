package de.budschie.deepnether.item.rendering;

import java.util.HashMap;

import de.budschie.deepnether.item.toolModifiers.IToolUsableItem;
import de.budschie.deepnether.item.toolModifiers.IToolUsableItem.Part;
import de.budschie.deepnether.main.DeepnetherMain;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.NativeImage.PixelFormat;
import net.minecraftforge.common.ToolType;

public class DynamicTextureCache
{
	private HashMap<String, DynamicTexture> cache = new HashMap<>();
	
	public DynamicTexture add(IToolUsableItem stick, IToolUsableItem head, ToolType toolType)
	{
		if(!cache.containsKey(toolType.getName() + "_" + stick.getBoundItem() + "_" + head.getBoundItem()))
		{
			try
			{
				NativeImage tex = NativeImage.read(PixelFormat.RGBA, DeepnetherMain.class.getResourceAsStream("/assets/deepnether/textures/item/toolparts/" + stick.getImage(Part.STICK, toolType) + ".png"));
				NativeImage img = NativeImage.read(PixelFormat.RGBA, DeepnetherMain.class.getResourceAsStream("/assets/deepnether/textures/item/toolparts/" + head.getImage(Part.HEAD, toolType) + ".png"));
								
				for(int x = 0; x < img.getWidth(); x++)
				{
					for(int y = 0; y < img.getHeight(); y++)
					{
						if(NativeImage.getAlpha(img.getPixelRGBA(x, y)) > 1)
						{
							//System.out.println("SET PIXEL");
							tex.setPixelRGBA(x, y, img.getPixelRGBA(x, y));
						}
					}
				}
				
				DynamicTexture dyntex = new DynamicTexture(16, 16, true);
				dyntex.setTextureData(tex);
				dyntex.updateDynamicTexture();
				
				cache.put(stick.getBoundItem() + "_" + head.getBoundItem(), dyntex);
				
				return dyntex;
			} catch (Exception e)
			{
				System.out.println("An error occured: ");
				System.out.println("Images that should be loaded, but are not:");
				System.out.println("/assets/deepnether/textures/item/toolparts/" + stick.getImage(Part.STICK, toolType) + ".png");
				System.out.println("/assets/deepnether/textures/item/toolparts/" + head.getImage(Part.HEAD, toolType) + ".png");
				e.printStackTrace();
				return new DynamicTexture(16, 16, true);
			}
		}
		else
			return cache.get(stick.getBoundItem() + "_" + head.getBoundItem());
	}
}
