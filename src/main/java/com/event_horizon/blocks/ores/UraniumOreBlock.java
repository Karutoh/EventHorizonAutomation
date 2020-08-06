package com.event_horizon.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class UraniumOreBlock extends OreBlock
{
	public UraniumOreBlock()
	{
		super(Block.Properties.create(Material.IRON)
			.hardnessAndResistance(6.0f, 6.0f)
			.sound(SoundType.METAL)
			.harvestTool(ToolType.PICKAXE)
			.harvestLevel(2)
			.setRequiresTool()
		);
	}
}
