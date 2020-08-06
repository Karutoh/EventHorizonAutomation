package com.event_horizon.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class CopperOreBlock extends OreBlock {
	public CopperOreBlock()
	{	
		super(Block.Properties.create(Material.IRON)
				.hardnessAndResistance(3.0f, 3.0f)
				.sound(SoundType.METAL)
				.harvestTool(ToolType.PICKAXE)
				.harvestLevel(1)
				.setRequiresTool()
		);
	}
	
	/*
	@Override
	public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch)
	{
		return 1;
		
	}
	*/
}
