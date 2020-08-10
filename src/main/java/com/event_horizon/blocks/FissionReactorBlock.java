package com.event_horizon.blocks;

import com.event_horizon.gui.container.FissionReactorContainer;
import com.event_horizon.registries.TileEntityRegister;
import com.event_horizon.tile_entities.FissionReactorTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class FissionReactorBlock extends Block
{
	public FissionReactorBlock()
	{
		super(Block.Properties.create(Material.MISCELLANEOUS)
				.hardnessAndResistance(3.0f, 3.0f)
				.sound(SoundType.METAL)
				.harvestTool(ToolType.PICKAXE)
				.harvestLevel(2)
				.setRequiresTool()
		);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return TileEntityRegister.FISSION_REACTOR.get().create();
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (!worldIn.isRemote())
		{
			TileEntity tile = worldIn.getTileEntity(pos);
			if (tile instanceof FissionReactorTileEntity)
			{
				INamedContainerProvider containerProvider = new INamedContainerProvider()
				{
					@Override
					public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
						return new FissionReactorContainer(windowId, worldIn, pos, playerInventory, playerEntity);
					}

					@Override
					public ITextComponent getDisplayName() {
						return new TranslationTextComponent("screen.event_horizon.fission_reactor");
					}
				};
				
				NetworkHooks.openGui((ServerPlayerEntity)player, containerProvider, pos);
			}
		}
		
		return ActionResultType.SUCCESS;
	}
}
