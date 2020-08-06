package com.event_horizon.registries;

import com.event_horizon.EventHorizon;
import com.event_horizon.tile_entities.FissionReactorTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityRegister
{
	private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, EventHorizon.MOD_ID);
	
	//Tile Entities
	public static final RegistryObject<TileEntityType<FissionReactorTileEntity>> FISSION_REACTOR = TILE_ENTITIES.register("fission_reactor", 
			() -> TileEntityType.Builder.create(FissionReactorTileEntity::new, BlockRegister.FISSION_REACTOR.get()).build(null)
	);
	
	public static void init()
	{
		TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
