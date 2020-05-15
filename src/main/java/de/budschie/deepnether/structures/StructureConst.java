package de.budschie.deepnether.structures;

public class StructureConst 
{
	/** The base path where the structure data lies **/
	//public static final String BASE_PATH = "/assets/" + References.MODID + "/structures";
	public static final String BASE_PATH = "C:\\Users\\Budschie\\Desktop\\OutputData";
	/** The Version of the structure file (1 = Row Compression => How many blocks are in the row, 2 = Palette**/
	public static final String VERSION = "Version";
	
	/** Only used for type 1, the key for the numbers in a row **/
	public static final String KEY_ROW = "Rows";
	
	/** Relevant for all block types, will be handeled differently from type to type**/
	public static final String KEY_BLOCKS = "Blocks";
	
	/** Only used for type 2, stores the block as a compound **/
	public static final String KEY_PALETTE_BLOCK_COMPOUND = "PaletteBlockID";
	
	/** Only used for type 3, this will be added to the number of the blockID, is always a short **/
	public static final String KEY_MIN_BYTE = "KeyMinByte";
	
	/** X-Axis **/
	public static final String KEY_WIDTH = "Width";
	
	/** Y-Axis **/
	public static final String KEY_HEIGHT = "Height";
	
	/** Z-Axis **/
	public static final String KEY_LENGTH = "Length";
	
	/** The prefix of the palette block, eg. Block0, Block1**/
	public static final String KEY_PALETTE_BLOCK_PREFIX = "Block";
}
