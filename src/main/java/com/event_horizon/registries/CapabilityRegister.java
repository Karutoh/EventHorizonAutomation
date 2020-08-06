package com.event_horizon.registries;

import com.event_horizon.capabilities.Fission;
import com.event_horizon.capabilities.IFission;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityRegister
{
	@CapabilityInject(IFission.class)
	public static Capability<IFission> FISSION = null;
	
	public static void init()
	{
		CapabilityManager.INSTANCE.register(IFission.class, new IStorage<IFission>()
		{
			@Override
			public INBT writeNBT(Capability<IFission> capability, IFission instance, Direction side)
			{
				return instance.serializeNBT();
			}

			@Override
			public void readNBT(Capability<IFission> capability, IFission instance, Direction side, INBT nbt)
			{
				instance.deserializeNBT((CompoundNBT)nbt);
			}
		}, Fission::new);
	}
}
