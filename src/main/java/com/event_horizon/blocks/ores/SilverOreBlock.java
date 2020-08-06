package com.event_horizon.blocks.ores;

import com.event_horizon.items.FuelRod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class SilverOreBlock extends OreBlock
{
	public SilverOreBlock()
	{
		super(Block.Properties.create(Material.IRON)
			.hardnessAndResistance(2.5f, 2.5f)
			.sound(SoundType.METAL)
			.harvestTool(ToolType.PICKAXE)
			.harvestLevel(1)
			.setRequiresTool()
		);
	}
	
	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player)
	{
		ItemStack item = player.inventory.getCurrentItem();
		if (!(item.getItem() instanceof FuelRod))
			return;
			
		if (worldIn.isRemote())
			player.inventory.deleteStack(item);
		
		worldIn.createExplosion(null, player.getPosX(), player.getPosY(), player.getPosZ(), 10.0f, Mode.DESTROY);
	}
}
