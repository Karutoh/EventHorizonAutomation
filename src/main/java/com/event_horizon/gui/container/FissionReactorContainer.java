package com.event_horizon.gui.container;

import java.util.Objects;

import com.event_horizon.registries.BlockRegister;
import com.event_horizon.registries.ContainerRegister;
import com.event_horizon.tile_entities.FissionReactorTileEntity;
import com.event_horizon.utils.FunctionalIntReferenceHolder;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;

public class FissionReactorContainer extends Container
{
	private final IWorldPosCallable canInteractwithCallable;
	private final FissionReactorTileEntity tile;
	
	public FissionReactorContainer(final int windowId, final PlayerInventory playerInventory, final FissionReactorTileEntity tile)
	{
		super(ContainerRegister.FISSION_REACTOR.get(), windowId);
		canInteractwithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());
		this.tile = tile;
		
		//Control Rod Slot
		addSlot(new Slot(tile, 0, 44, 17));
		
		//Neutron Source Slot
		addSlot(new Slot(tile, 1, 8, 53));
		
		//Fuel Rod Slot
		addSlot(new Slot(tile, 2, 44, 53));
		
		//Hotbar
		for (int x = 0; x < 9; ++x)
			addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));
		
		//Player Inventory		
		for (int x = 0; x < 9; ++x)
			for (int y = 0; y < 3; ++y)
				addSlot(new Slot(playerInventory, 9 + x * 3 + y, 8 + x * 18, 84 + y * 18));
	}
	
	public FissionReactorContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data)
	{
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(canInteractwithCallable, playerIn, BlockRegister.FISSION_REACTOR.get());
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
	{
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack tmp = slot.getStack();
			itemStack = tmp.copy();
			
			if (index < 3)
				if (!mergeItemStack(tmp, 3, inventorySlots.size(), true))
					return ItemStack.EMPTY;
			else if (!this.mergeItemStack(tmp, 0, 3, false))
				return ItemStack.EMPTY;
			
			if (tmp.isEmpty())
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();
		}
		
		return itemStack;
	}
	
	public FissionReactorTileEntity getTileEntity()
	{
		return tile;
	}
	
	private static FissionReactorTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data)
	{
		Objects.requireNonNull(playerInventory, "Player inventory cannot be null.");
		Objects.requireNonNull(data, "Data cannot be null.");
		
		final TileEntity tile = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (tile instanceof FissionReactorTileEntity)
			return (FissionReactorTileEntity)tile;
		
		throw new IllegalStateException("Tile entity is not correct. " + tile);
	}
}
