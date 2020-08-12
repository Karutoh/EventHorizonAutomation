package com.event_horizon.gui.container;

import com.event_horizon.capabilities.FissionPacket;
import com.event_horizon.capabilities.FissionPacketHandler;
import com.event_horizon.capabilities.IFission;
import com.event_horizon.registries.BlockRegister;
import com.event_horizon.registries.CapabilityRegister;
import com.event_horizon.registries.ContainerRegister;
import com.event_horizon.tile_entities.FissionReactorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class FissionReactorContainer extends Container
{
	private FissionReactorTileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;
	
	public FissionReactorContainer(int windowId, World world, BlockPos blockPos, PlayerInventory playerInventory, PlayerEntity playerEntity)
	{
		super(ContainerRegister.FISSION_REACTOR.get(), windowId);
		this.tileEntity = (FissionReactorTileEntity) world.getTileEntity(blockPos);
		this.playerEntity = playerEntity;
		this.playerInventory = new InvWrapper(playerInventory);
		
		if (tileEntity != null)
		{
			tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent((capability) -> {
				//Control Rod Slot
				addSlot(new SlotItemHandler(capability, 0, 44, 17));
				
				//Neutron Source Slot
				addSlot(new SlotItemHandler(capability, 1, 8, 53));
				
				//Fuel Rod Slot
				addSlot(new SlotItemHandler(capability, 2, 44, 53));
			});
		}
		
		//Hotbar
		for (int x = 0; x < 9; ++x)
			addSlot(new SlotItemHandler(this.playerInventory, x, 8 + x * 18, 142));
		
		//Player Inventory		
		for (int x = 0; x < 9; ++x)
			for (int y = 0; y < 3; ++y)
				addSlot(new SlotItemHandler(this.playerInventory, 9 + x * 3 + y, 8 + x * 18, 84 + y * 18));
	}
	
	@Override
	public void detectAndSendChanges()
	{
		IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
		if (itemHandler != null)
		{
			IFission fission = itemHandler.getStackInSlot(2).getCapability(CapabilityRegister.FISSION).orElse(null);
			if (fission != null)
			{	
				if (fission.getNeutrons() > 0)
				{
					FissionPacket packet = new FissionPacket(tileEntity.getPos(), 2);
					fission.encode(packet);
					
					FissionPacketHandler.sendPacket((ServerPlayerEntity)playerEntity, packet);
				}
			}
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(IWorldPosCallable.of(playerIn.world, tileEntity.getPos()), playerIn, BlockRegister.FISSION_REACTOR.get());
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
		return tileEntity;
	}
}
