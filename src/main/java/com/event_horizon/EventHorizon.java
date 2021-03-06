package com.event_horizon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.antlr.v4.runtime.atn.BasicState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.event_horizon.capabilities.FissionPacketHandler;
import com.event_horizon.registries.BlockRegister;
import com.event_horizon.registries.CapabilityRegister;
import com.event_horizon.registries.ContainerRegister;
import com.event_horizon.registries.ItemRegister;
import com.event_horizon.registries.TileEntityRegister;

@Mod("event_horizon")
public class EventHorizon
{
	public static final String MOD_ID = "event_horizon";
	
    @SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger();
    
    public static final ItemGroup TAB = new ItemGroup("event_horizon")
    {
    	@Override
    	public ItemStack createIcon()
    	{
    		return new ItemStack(ItemRegister.FISSION_REACTOR.get());
    	}
    };

    public EventHorizon()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onServerStarting);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::modelBake);
        
        TileEntityRegister.init();
        ContainerRegister.init();
        BlockRegister.init();
        ItemRegister.init();
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    	CapabilityRegister.init();
    	FissionPacketHandler.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }

    public void onServerStarting(FMLServerStartingEvent event)
    {
    }
    
    public void modelBake(ModelBakeEvent event)
    {
    	try
    	{
			IUnbakedModel model = B3DLoader.INSTANCE.loadModel(new ResourceLocation(MOD_ID, "fission_reactor.b3d"));
			model.
			model.bakeModel(event.getModelLoader(), ModelLoader.defaultTextureGetter(), new BasicState(model.getDefaultState(), false), DefaultVertexFormats.ITEM);
			
		}
    	catch (Exception e)
    	{
			e.printStackTrace();
		}
    }
}
