package com.event_horizon.registries;

import com.event_horizon.EventHorizon;
import com.event_horizon.gui.container.FissionReactorContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerRegister
{
	private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, EventHorizon.MOD_ID);
	
	//Containers
	public static final RegistryObject<ContainerType<FissionReactorContainer>> FISSION_REACTOR = CONTAINERS.register("fission_reactor", () -> IForgeContainerType.create((windowId, inv, data) -> {
		BlockPos blockPos = data.readBlockPos();
		World world = inv.player.getEntityWorld();
		return new FissionReactorContainer(windowId, world, blockPos, inv, inv.player);
	}));
	
	public static void init()
	{
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
