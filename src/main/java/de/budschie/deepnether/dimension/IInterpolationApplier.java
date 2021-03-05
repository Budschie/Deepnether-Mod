package de.budschie.deepnether.dimension;

import de.budschie.deepnether.biomes.biome_data_handler.worldgen.IStandardInterpolationApplier;

public interface IInterpolationApplier<I, O>
{
	void apply(I[][] inputArea, O[][] outputArea);
	
	public static <I, O> IInterpolationApplier<I, O> getSimpleApplier(IStandardInterpolationApplier<I, O> standardInterpolationApplier)
	{
		return new IInterpolationApplier<I, O>()
		{
			@Override
			public void apply(I[][] inputArea, O[][] outputArea)
			{
				for(int x = 0; x < outputArea.length; x++)
				{
					for(int z = 0; z < outputArea[0].length; z++)
					{
						outputArea[x][z] = standardInterpolationApplier.apply(inputArea, x, z);
					}
				}
			}
		};
	}
}

/*
 * 

{
  "scale": 0.25,
  "effects": {
    "mood_sound": {
      "sound": "minecraft:ambient.basalt_deltas.mood",
      "tick_delay": 6000,
      "block_search_extent": 8,
      "offset": 2
    },
    "ambient_sound": "minecraft:ambient.basalt_deltas.loop",
    "sky_color": 803333,
    "fog_color": 803333,
    "water_color": 14869218,
    "water_fog_color": 11711154,
	"particle": {
      "options": {
        "type": "minecraft:portal"
      },
      "probability": 0.005
    }
  },
  "surface_builder": "minecraft:warped_forest",
  "carvers": {
    "air": [],
    "liquid": []
  },
  "features": [
    [],
    [],
    [],
    [],
    [],
    [],
    [],
    [],
    []
  ],
  "starts": [],
  "spawners": {
    "monster": [],
    "creature": [],
    "ambient": [],
    "water_creature": [],
    "water_ambient": [],
    "misc": []
  },
  "spawn_costs": {},
  "player_spawn_friendly": false,
  "precipitation": "none",
  "temperature": 2,
  "downfall": 0,
  "category": "nether",
  "depth": 0.3,
  "creature_spawn_probability": 0
}
 */
