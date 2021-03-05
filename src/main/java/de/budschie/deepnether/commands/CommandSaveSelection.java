package de.budschie.deepnether.commands;

import java.util.ArrayList;
import java.util.UUID;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;

import de.budschie.deepnether.structures.BlockObject;
import de.budschie.deepnether.structures.BlockPosHelper;
import de.budschie.deepnether.structures.PaletteStructure;
import de.budschie.deepnether.structures.StructureBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class CommandSaveSelection
{
	@SubscribeEvent
	public static void onRightClick(RightClickBlock rightClickevent)
	{
		if(!CommandToggleSel.isSelectionActive)
			return;
		BlockPos pos = rightClickevent.getPos();
		// intentional reaching across logic sides, cuz debug pruprose etc.
		if(rightClickevent.getPlayer().isCrouching())
		{
			rightClickevent.getPlayer().sendMessage(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "POS2: " + pos.getX() + " " + pos.getY() + " " + pos.getZ()), new UUID(0, 0));
			pos2 = pos;
		}
		else
		{
			rightClickevent.getPlayer().sendMessage(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "POS1: " + pos.getX() + " " + pos.getY() + " " + pos.getZ()), new UUID(0, 0));
			pos1 = pos;
		}
	}
	
	public static BlockPos pos1;
	public static BlockPos pos2;
	
	public static void registerCommandTpDim(CommandDispatcher<CommandSource> dispatcher)
	{
		LiteralCommandNode<CommandSource> literalcommandnode = dispatcher
				.register(Commands.literal("savesel").requires((p_198740_0_) ->
				{
					return p_198740_0_.hasPermissionLevel(2);
				}).then(Commands.argument("type", StringArgumentType.word()).then(Commands.argument("filename", StringArgumentType.word()).executes((cmd) ->
				{
					return saveSel(cmd);
				}))));
	}

	private static int saveSel(CommandContext<CommandSource> cmdContext)
	{
		try
		{
		if(pos1 != null && pos2 != null)
		{
			StructureBase structureType = getStructure(cmdContext.getArgument("type", String.class));
			if(structureType == null)
				throw new CommandException(new StringTextComponent(TextFormatting.RED + "The command couldn't be executed.\nReason: The structure type was invalid. Valid types: ['palette']"));
			
			BlockPos[] sortedPos = BlockPosHelper.sortPos(pos1, pos2);
			
			BlockPos whl = sortedPos[1].subtract(sortedPos[0]);
			
			ArrayList<BlockObject> blocks = new ArrayList<>();
			
			for(int x = sortedPos[0].getX(); x < sortedPos[1].getX(); x++)
			{
				for(int y = sortedPos[0].getY(); y < sortedPos[1].getY(); y++)
				{
					for(int z = sortedPos[0].getZ(); z < sortedPos[1].getZ(); z++)
					{
						BlockPos pos = new BlockPos(x, y, z);
						blocks.add(new BlockObject(cmdContext.getSource().getWorld().getBlockState(pos), pos));
					}
				}
			}
			
			structureType.writeData(cmdContext.getArgument("filename", String.class), blocks, whl.getX(), whl.getY(), whl.getZ(), sortedPos[0], cmdContext.getSource().getWorld());
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return 0;
	}
	
	public static StructureBase getStructure(String structure)
	{
		if(structure.equals("palette"))
			return new PaletteStructure();
		return null;
	}
}
