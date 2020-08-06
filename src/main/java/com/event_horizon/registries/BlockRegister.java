package com.event_horizon.registries;

import com.event_horizon.EventHorizon;
import com.event_horizon.blocks.FissionReactorBlock;
import com.event_horizon.blocks.ores.AluminumOreBlock;
import com.event_horizon.blocks.ores.CopperOreBlock;
import com.event_horizon.blocks.ores.SilverOreBlock;
import com.event_horizon.blocks.ores.SulfurOreBlock;
import com.event_horizon.blocks.ores.TinOreBlock;
import com.event_horizon.blocks.ores.UraniumOreBlock;
import com.event_horizon.blocks.ores.ZirconiumOreBlock;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegister
{
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EventHorizon.MOD_ID);
	
	//Blocks
	public static final RegistryObject<Block> FISSION_REACTOR = BLOCKS.register("fission_reactor", FissionReactorBlock::new);
	
	public static final RegistryObject<Block> ZIRCONIUM_ORE = BLOCKS.register("zirconium_ore", ZirconiumOreBlock::new);
	public static final RegistryObject<Block> ALUMINUM_ORE = BLOCKS.register("aluminum_ore", AluminumOreBlock::new);
	public static final RegistryObject<Block> URANIUM_ORE = BLOCKS.register("uranium_ore", UraniumOreBlock::new);
	public static final RegistryObject<Block> SILVER_ORE = BLOCKS.register("silver_ore", SilverOreBlock::new);
	public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", CopperOreBlock::new);
	public static final RegistryObject<Block> SULFUR_ORE = BLOCKS.register("sulfur_ore", SulfurOreBlock::new);
	public static final RegistryObject<Block> TIN_ORE = BLOCKS.register("tin_ore", TinOreBlock::new);
	
	public static void init()
	{
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	/*
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event)
    {
    	IForgeRegistry<Block> reg = event.getRegistry();
    	reg.register(COPPER_ORE_BLOCK);
    }
    
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event)
    {
    	IForgeRegistry<Item> reg = event.getRegistry();
    	reg.register(COPPER_ORE_ITEM);
    }
    */
}
