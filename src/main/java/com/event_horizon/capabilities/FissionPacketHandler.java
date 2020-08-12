package com.event_horizon.capabilities;

import com.event_horizon.EventHorizon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class FissionPacketHandler
{
	private static final String VERSION = "1";
	private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		new ResourceLocation(EventHorizon.MOD_ID, "main"),
		() -> VERSION,
		VERSION::equals,
		VERSION::equals
	);
	
	private static int FISSION_ID = 0;
	
	public static void init()
	{
		INSTANCE.registerMessage(FISSION_ID, FissionPacket.class, FissionPacket::encode, FissionPacket::decode, FissionPacket::consumer);
	}
	
	public static void sendPacket(ServerPlayerEntity player, FissionPacket packet)
	{
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
	}
}
