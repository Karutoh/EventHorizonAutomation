package com.event_horizon.capabilities;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class FissionPacket
{
	private BlockPos blockPos;
	private int slotNum;
	private float temperature;
	private long neutrons;
	
	public FissionPacket(final BlockPos blockPos, final int slotNum)
	{
		this.blockPos = blockPos;
		this.slotNum = slotNum;
		this.temperature = 0;
		this.neutrons = 0;
	}
	
	public FissionPacket(PacketBuffer buffer)
	{
		blockPos = buffer.readBlockPos();
		slotNum = buffer.readInt();
		temperature = buffer.readFloat();
		neutrons = buffer.readLong();
	}
	
	public void encode(PacketBuffer buffer)
	{
		buffer.writeBlockPos(blockPos);
		buffer.writeInt(slotNum);
		buffer.writeFloat(temperature);
		buffer.writeLong(neutrons);
	}
	
	public static FissionPacket decode(PacketBuffer buffer)
	{
		return new FissionPacket(buffer);
	}
	
	public void setBlockPos(final BlockPos blockPos)
	{
		this.blockPos = blockPos;
	}
	
	public BlockPos getBlockPos()
	{
		return blockPos;
	}
	
	public void setSlotNum(final int slotNum)
	{
		this.slotNum = slotNum;
	}
	
	public int getSlotNum()
	{
		return slotNum;
	}
	
	public void setTemperature(final float temperature)
	{
		this.temperature = temperature;
	}
	
	public float getTemperature()
	{
		return temperature;
	}
	
	public void setNeutrons(final long neutrons)
	{
		this.neutrons = neutrons;
	}
	
	public long getNeutrons()
	{
		return neutrons;
	}
}
