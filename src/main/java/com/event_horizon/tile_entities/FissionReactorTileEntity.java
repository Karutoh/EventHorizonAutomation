package com.event_horizon.tile_entities;

import javax.annotation.Nonnull;

import com.event_horizon.blocks.FissionReactorBlock;
import com.event_horizon.capabilities.Fission;
import com.event_horizon.capabilities.FissionPacket;
import com.event_horizon.capabilities.FissionPacketHandler;
import com.event_horizon.capabilities.IFission;
import com.event_horizon.gui.container.FissionReactorContainer;
import com.event_horizon.items.FuelRod;
import com.event_horizon.items.SilverRod;
import com.event_horizon.registries.CapabilityRegister;
import com.event_horizon.registries.TileEntityRegister;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class FissionReactorTileEntity implements ITickableTileEntity
{
	private NonNullList<ItemStack> contents = NonNullList.withSize(3, ItemStack.EMPTY);
	protected int numPlayersUsing;
	private IItemHandlerModifiable items = createHandler();
	private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);
	
	public FissionReactorTileEntity(TileEntityType<?> typeIn)
	{
		super(typeIn);
	}
	
	public FissionReactorTileEntity()
	{
		this(TileEntityRegister.FISSION_REACTOR.get());
	}
	
	@Override
	public void tick()
	{
		if (!world.isRemote())
		{
			ItemStack fuel = contents.get(2);
			if (fuel.getItem() instanceof FuelRod)
			{	
				IFission fission = fuel.getCapability(CapabilityRegister.FISSION).orElse(null);
				if (fission != null)
				{
					long count = Math.round(Minecraft.getInstance().getTickLength() / Fission.getDuration());
					
					ItemStack control = contents.get(0);
					
					ItemStack source = contents.get(1);
					if (source.getItem() instanceof SilverRod)
					{
						fission.addNeutrons(count);
					}
					
					fission.tick();
					
					if (fission.getTemperature() > 1405)
					{
						world.removeTileEntity(pos);
						world.removeBlock(pos, false);
						
						world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 20.0f, Mode.DESTROY);
					}
				}
			}
		}
	}
	
	@Override
	public boolean canOpen(PlayerEntity player)
	{
		boolean result = super.canOpen(player);
		
		if (result == true)
		{
			if (!world.isRemote())
			{
				ItemStack stack = contents.get(2);
				if (stack.getItem() instanceof FuelRod)
				{
					IFission fission = stack.getCapability(CapabilityRegister.FISSION).orElse(null);
					if (fission != null)
					{
						FissionPacket packet = new FissionPacket(pos, 2);
						fission.encode(packet);
						
						FissionPacketHandler.sendPacket((ServerPlayerEntity)player, packet);
					}
				}
			}
		}
		
		return result;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 3;
	}
	
	@Override
	public NonNullList<ItemStack> getItems()
	{		
		return contents;
	}
	
	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn)
	{
		contents = itemsIn;
	}
	
	@Override
	protected ITextComponent getDefaultName()
	{
		return new TranslationTextComponent("container.fission_reactor");
	}
	
	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new FissionReactorContainer(id, player, this);
	}
	
	@Override
	public boolean receiveClientEvent(int id, int type)
	{
		if (id == 1)
		{
			numPlayersUsing = type;
			return true;
		}
		else
		{
			return super.receiveClientEvent(id, type);
		}
	}
	
	@Override
	public void openInventory(PlayerEntity player)
	{
		if (!player.isSpectator())
		{	
			if (numPlayersUsing < 0)
			{
				numPlayersUsing = 0;
			}
			
			++numPlayersUsing;
			onOpenOrClose();
		}
	}
	
	@Override
	public void closeInventory(PlayerEntity player)
	{
		if (!player.isSpectator())
		{
			--numPlayersUsing;
			onOpenOrClose();
		}
	}
	
	protected void onOpenOrClose()
	{
		Block block = getBlockState().getBlock();
		if (block instanceof FissionReactorBlock)
		{
			world.addBlockEvent(pos, block, 1, numPlayersUsing);
			world.notifyNeighborsOfStateChange(pos, block);
		}
	}
	
	public static int getPlayersUsing(IBlockReader reader, BlockPos pos)
	{
		BlockState state = reader.getBlockState(pos);
		if (state.hasTileEntity())
		{
			TileEntity tile = reader.getTileEntity(pos);
			if (tile instanceof FissionReactorTileEntity)
			{
				return ((FissionReactorTileEntity)tile).numPlayersUsing;
			}
		}
		
		return 0;
	}
	
	public static void swapContents(FissionReactorTileEntity a, FissionReactorTileEntity b)
	{
		NonNullList<ItemStack> list = a.getItems();
		a.setItems(b.getItems());
		b.setItems(list);
	}
	
	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
		
		if (itemHandler != null)
		{
			itemHandler.invalidate();
			itemHandler = null;
		}
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return itemHandler.cast();
		}
		
		return super.getCapability(cap, side);
	}
	
	private IItemHandlerModifiable createHandler()
	{
		return new InvWrapper(this);
	}
	
	@Override
	public void remove()
	{
		super.remove();
		
		if (itemHandler != null)
		{
			itemHandler.invalidate();
		}
	}
	
	@Override
	public CompoundNBT write(CompoundNBT nbt)
	{
		super.write(nbt);
		
		if (!checkLootAndWrite(nbt))
			ItemStackHelper.saveAllItems(nbt, contents);
		
		return nbt;
	}
	
	@Override
	public void read(BlockState blockState, CompoundNBT nbt)
	{
		super.read(blockState, nbt);
		
		contents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		if (!checkLootAndRead(nbt))
			ItemStackHelper.loadAllItems(nbt, contents);;
	}
}
