package com.event_horizon.items;

import javax.annotation.Nullable;

import com.event_horizon.EventHorizon;
import com.event_horizon.capabilities.FissionProvider;
import com.event_horizon.capabilities.IFission;
import com.event_horizon.registries.CapabilityRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class FuelRod extends Item
{
	public FuelRod()
	{
		super(new Item.Properties()
			.group(EventHorizon.TAB)
			.maxStackSize(1)
		);
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack itemStack, @Nullable CompoundNBT nbt)
	{
		if (CapabilityRegister.FISSION == null)
			return null;
		else
			return new FissionProvider();
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
		
		IFission fission = stack.getCapability(CapabilityRegister.FISSION).orElse(null);
		if (fission != null)
			fission.tick();
	}
}
