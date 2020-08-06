package com.event_horizon.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ZirconiumOreBlock extends OreBlock
{
	public ZirconiumOreBlock()
	{
		super(Block.Properties.create(Material.IRON)
				.hardnessAndResistance(5.0f, 5.0f)
				.sound(SoundType.METAL)
				.harvestTool(ToolType.PICKAXE)
				.harvestLevel(2)
				.setRequiresTool()
		);
	}
}
