package com.event_horizon.events;

import com.event_horizon.EventHorizon;
import com.event_horizon.gui.screen.FissionReactorScreen;
import com.event_horizon.registries.ContainerRegister;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EventHorizon.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event)
	{
		ScreenManager.registerFactory(ContainerRegister.FISSION_REACTOR.get(), FissionReactorScreen::new);
	}
}
