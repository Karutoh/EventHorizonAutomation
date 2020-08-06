package com.event_horizon.capabilities;

import com.event_horizon.registries.CapabilityRegister;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class FissionProvider implements ICapabilitySerializable<CompoundNBT>
{
	private LazyOptional<IFission> instance = LazyOptional.of(Fission::new);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		return CapabilityRegister.FISSION.orEmpty(cap, instance);
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		return instance.orElseThrow(() -> new IllegalArgumentException("at serialize")).serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		instance.orElseThrow(() -> new IllegalArgumentException("at serialize")).deserializeNBT(nbt);;
	}

}
