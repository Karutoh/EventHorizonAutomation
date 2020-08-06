package com.event_horizon.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class SulfurOreBlock extends OreBlock
{
	public SulfurOreBlock()
	{
		super(Block.Properties.create(Material.IRON)
			.hardnessAndResistance(2.5f, 2.5f)
			.sound(SoundType.METAL)
			.harvestTool(ToolType.PICKAXE)
			.harvestLevel(1)
			.setRequiresTool()
		);
	}
}
