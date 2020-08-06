package com.event_horizon.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class AluminumOreBlock extends OreBlock
{
	public AluminumOreBlock()
	{
		super(Block.Properties.create(Material.IRON)
				.hardnessAndResistance(3.0f, 3.0f)
				.sound(SoundType.METAL)
				.harvestTool(ToolType.PICKAXE)
				.harvestLevel(1)
				.setRequiresTool()
		);
	}
}
