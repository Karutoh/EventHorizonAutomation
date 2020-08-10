package com.event_horizon.tile_entities;

import javax.annotation.Nonnull;

import com.event_horizon.capabilities.Fission;
import com.event_horizon.capabilities.IFission;
import com.event_horizon.registries.CapabilityRegister;
import com.event_horizon.registries.ItemRegister;
import com.event_horizon.registries.TileEntityRegister;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.Explosion.Mode;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class FissionReactorTileEntity extends TileEntity implements ITickableTileEntity
{	
	private ItemStackHandler items = createHandler();
	private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);
	
	public FissionReactorTileEntity()
	{
		super(TileEntityRegister.FISSION_REACTOR.get());
	}
	
	@Override
	public void tick()
	{
		if (!world.isRemote())
		{
			ItemStack fuel = items.getStackInSlot(2);
			if (fuel.getItem() == ItemRegister.FUEL_ROD.get())
			{	
				IFission fission = fuel.getCapability(CapabilityRegister.FISSION).orElse(null);
				if (fission != null)
				{
					long count = Math.round(Minecraft.getInstance().getTickLength() / Fission.getDuration());
					
					//ItemStack control = items.getStackInSlot(0);
					
					ItemStack source = items.getStackInSlot(1);
					if (source.getItem() == ItemRegister.SILVER_ROD.get())
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
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
		
		if (itemHandler != null)
		{
			itemHandler.invalidate();
			itemHandler = null;
		}
	}
	
	private ItemStackHandler createHandler()
	{
        return new ItemStackHandler(3) {

            @Override
            protected void onContentsChanged(int slot)
            {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack)
            {
            	if (slot == 1 && stack.getItem() == ItemRegister.SILVER_ROD.get())
            		return true;
            	else if (slot == 2 && stack.getItem() == ItemRegister.FUEL_ROD.get())
            		return true;
            	
        		return false;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
            {
            	if (slot == 0)
            		return stack;
            	else if (slot == 1 && stack.getItem() != ItemRegister.SILVER_ROD.get())
                    return stack;
            	else if (slot == 2 && stack.getItem() != ItemRegister.FUEL_ROD.get())
            		return stack;
            	
        		return super.insertItem(slot, stack, simulate);
            }
        };
	}
	
	@Override
	public void remove()
	{
		super.remove();
		
		itemHandler.invalidate();
	}
	
	@Override
	public CompoundNBT write(CompoundNBT nbt)
	{
		super.write(nbt);
		
		nbt.put("inv", items.serializeNBT());
		
		return nbt;
	}
	
	@Override
	public void read(BlockState blockState, CompoundNBT nbt)
	{
		super.read(blockState, nbt);
		
		items.deserializeNBT(nbt);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return itemHandler.cast();
		
		return super.getCapability(cap, side);
	}
}
