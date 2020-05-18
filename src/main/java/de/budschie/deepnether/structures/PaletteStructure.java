package de.budschie.deepnether.structures;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PaletteStructure extends StructureBase
{
	@Override
	protected ArrayList<BlockObject> read(CompoundNBT compound, World world, BlockPos blockPos) 
	{
		ArrayList<BlockObject> list = new ArrayList<BlockObject>();
		
		BlockState[] blockPalette = new BlockState[compound.getCompound(StructureConst.KEY_PALETTE_BLOCK_COMPOUND).size()];
		
		for(int i = 0; i < blockPalette.length; i++)
		{
			blockPalette[i] = NBTUtil.readBlockState(compound.getCompound(StructureConst.KEY_PALETTE_BLOCK_COMPOUND).getCompound(StructureConst.KEY_PALETTE_BLOCK_PREFIX+i));
		}
		

			short[] blocks = bytesToShort(compound.getByteArray(StructureConst.KEY_BLOCKS));
			int pos = 0;
			for(int x = 0; x < width; x++)
			{
				for(int y = 0; y < height; y++)
				{
					for(int z = 0; z < length; z++)
					{
						if(blocks[pos] == -1)
						{
							list.add(new BlockObject(Blocks.AIR.getDefaultState(), new BlockPos(x,y,z)));
						}
						else
						{
							list.add(new BlockObject(blockPalette[blocks[pos]], new BlockPos(x, y, z)));
							// list.add(new BlockObject(Registry.BLOCK.getObjectById(ID[blocks[pos]]).getStateFromMeta(meta[blocks[pos]]), new BlockPos(x,y,z)));
						}
						
						pos++;
					}
				}
			}
		
		
		return list;
	}
	
	ArrayList<PaletteObject> paletteObjects;

	@Override
	protected WrittenData write(ArrayList<BlockObject> blocks) 
	{
		CompoundNBT compound = new CompoundNBT();
		byte ver = 2;
		compound.putByte(StructureConst.VERSION, ver);
		
		compound.putShort(StructureConst.KEY_HEIGHT, height);
		compound.putShort(StructureConst.KEY_LENGTH, length);
		compound.putShort(StructureConst.KEY_WIDTH, width);
		
		paletteObjects = new ArrayList<PaletteObject>();
		
		short[] paletteCalcBytes = new short[width*height*length];
		
		int pos = 0;
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				for(int z = 0; z < length; z++)
				{
					if(blocks.get(pos).getBlock() == Blocks.AIR.getDefaultState())
					{
						paletteCalcBytes[pos] = -1;
					}
					else
					{
						paletteCalcBytes[pos] = addToPalette(blocks.get(pos));
					}
					
					pos++;
				}
			}
		}

		compound.putByteArray(StructureConst.KEY_BLOCKS, shortToBytes(paletteCalcBytes));
		
		byte[] meta = new byte[paletteObjects.size()];
		int[] ID = new int[paletteObjects.size()];
		
		CompoundNBT palette = new CompoundNBT();
		
		for(int i = 0; i < paletteObjects.size(); i++)
		{
			PaletteObject object = paletteObjects.get(i);
			
			palette.put(StructureConst.KEY_PALETTE_BLOCK_PREFIX+i, NBTUtil.writeBlockState(object.getState()));
		}
		
		compound.put(StructureConst.KEY_PALETTE_BLOCK_COMPOUND, palette);
		
		return new WrittenData(null, compound);
	}
	
	private short addToPalette(BlockObject object)
	{
		//byte ret = 1;
		for(byte i = 0; i < paletteObjects.size(); i++)
		{
			if(paletteObjects.get(i).compareWithBlockState(object.getBlock()))
			{
				return i;
			}
		}
		
		paletteObjects.add(new PaletteObject(object.getBlock()));
		
		return (short) (paletteObjects.size() - 1);
	}
	
	public short[] bytesToShort(byte[] bytes) {

		short[] shorts = new short[bytes.length/2];
		// to turn bytes to shorts as either big endian or little endian. 
		ByteBuffer.wrap(bytes).order(ByteOrder.nativeOrder()).asShortBuffer().get(shorts);
			//System.out.println("OUT OF WHILE");
		     return shorts;
		}
		public byte[] shortToBytes(short[] value) {
		    //return ByteBuffer.allocate(value.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer().put(value));
			ByteBuffer buf = ByteBuffer.allocate(value.length * 2).order(ByteOrder.nativeOrder());
			for(int i = 0; i < value.length; i++)
			{
				buf.putShort(value[i]);
			}
			
			return buf.array();
		}
}
