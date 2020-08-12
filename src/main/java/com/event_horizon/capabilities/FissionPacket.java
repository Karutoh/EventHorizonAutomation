package com.event_horizon.capabilities;

import java.util.function.Supplier;

import com.event_horizon.registries.CapabilityRegister;
import com.event_horizon.registries.ItemRegister;
import com.event_horizon.tile_entities.FissionReactorTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

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
	
	public static void consumer(FissionPacket packet, Supplier<Context> ctx)
	{
		NetworkDirection dir = ctx.get().getDirection();
		
		if (dir.getReceptionSide().isClient())
		{
			ctx.get().enqueueWork(() -> {
				@SuppressWarnings("resource")
				World world = Minecraft.getInstance().world;
				
				TileEntity tile = world.getTileEntity(packet.getBlockPos());
				if (tile instanceof FissionReactorTileEntity)
				{
					IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(() -> new TypeNotPresentException("IItemHandler", null));
					
					ItemStack stack = itemHandler.getStackInSlot(packet.getSlotNum());
					if (stack.getItem() == ItemRegister.FUEL_ROD.get())
					{
						IFission fission = stack.getCapability(CapabilityRegister.FISSION).orElseThrow(() -> new TypeNotPresentException("IFission", null));
						fission.decode(packet);
					}
				}
			});
		}
		
		ctx.get().setPacketHandled(true);
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
