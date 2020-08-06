package com.event_horizon.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IFission extends INBTSerializable<CompoundNBT>
{
	public void tick();
	
	public static float getDuration()
	{
		return 0.0f;
	}
	
	public float getTemperature();
	
	public void removeNeutrons(final long neutrons);
	
	public void addNeutrons(final long neutrons);
	
	public long getNeutrons();
	
	public void encode(FissionPacket packet);
	
	public void decode(FissionPacket packet);
}
