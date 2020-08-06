package com.event_horizon.capabilities;

import com.event_horizon.EventHorizon;
import com.event_horizon.items.FuelRod;
import com.event_horizon.registries.CapabilityRegister;
import com.event_horizon.tile_entities.FissionReactorTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
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
		INSTANCE.registerMessage(FISSION_ID, FissionPacket.class, FissionPacket::encode, FissionPacket::decode, (packet, ctx) -> {
			NetworkDirection dir = ctx.get().getDirection();
			
			if (dir.getReceptionSide().isClient())
			{
				ctx.get().enqueueWork(() -> {
					World world = Minecraft.getInstance().world;
					
					TileEntity tile = world.getTileEntity(packet.getBlockPos());
					if (tile instanceof FissionReactorTileEntity)
					{
						ItemStack stack = ((FissionReactorTileEntity)tile).getItems().get(packet.getSlotNum());
						if (stack.getItem() instanceof FuelRod)
						{
							IFission fission = stack.getCapability(CapabilityRegister.FISSION).orElse(null);
							if (fission != null)
								fission.decode(packet);
						}
					}
				});
			}
			
			ctx.get().setPacketHandled(true);
		});
	}
	
	public static void sendPacket(ServerPlayerEntity player, FissionPacket packet)
	{
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
	}
}
