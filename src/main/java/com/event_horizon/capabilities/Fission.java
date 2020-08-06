package com.event_horizon.capabilities;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;

public class Fission implements IFission
{
	private static final float duration = 0.0001f;
	private float temperature;
	private long neutrons;
	
	public Fission()
	{
		temperature = 293;
		neutrons = 0;
	}
	
	public Fission(FissionPacket packet)
	{
		temperature = packet.getTemperature();
		neutrons = packet.getNeutrons();
	}
	
	@Override
	public void tick()
	{
		long count = Math.round(Minecraft.getInstance().getTickLength() / duration);
		
		//2.8993624e-5
		
		if (count > neutrons)
		{
			temperature += .001 * ((double)neutrons * 3.0);
			neutrons *= 3;
		}
		else
		{
			temperature += .001 * ((double)count * 3.0);
			neutrons = (neutrons - count) + count * 3;
		}
	}
	
	public static float getDuration()
	{
		return duration;
	}
	
	@Override
	public float getTemperature()
	{
		return temperature;
	}

	@Override
	public void removeNeutrons(long neutrons)
	{
		if (neutrons < 0)
			return;
		
		if (this.neutrons - neutrons < 0)
			this.neutrons = 0;
		else
			this.neutrons -= neutrons;
	}
	
	@Override
	public void addNeutrons(long neutrons)
	{
		if (neutrons < 0)
			return;
			
		this.neutrons += neutrons;
	}

	@Override
	public long getNeutrons()
	{
		return neutrons;
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		
		nbt.putFloat("temperature", temperature);
		nbt.putLong("neutrons", neutrons);
		
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		temperature = nbt.getFloat("temperature");
		neutrons = nbt.getLong("neutrons");
	}
	
	@Override
	public void encode(FissionPacket packet)
	{
		packet.setTemperature(temperature);
		packet.setNeutrons(neutrons);
	}

	@Override
	public void decode(FissionPacket packet)
	{
		temperature = packet.getTemperature();
		neutrons = packet.getNeutrons();
	}
}
