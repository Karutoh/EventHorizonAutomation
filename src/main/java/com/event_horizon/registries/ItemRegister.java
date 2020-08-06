package com.event_horizon.registries;

import com.event_horizon.EventHorizon;
import com.event_horizon.items.FissionReactorItem;
import com.event_horizon.items.FuelRod;
import com.event_horizon.items.SilverRod;
import com.event_horizon.items.SulfurPowder;
import com.event_horizon.items.UraniumPellette;
import com.event_horizon.items.UraniumPowder;
import com.event_horizon.items.ores.AluminumIngot;
import com.event_horizon.items.ores.AluminumOreItem;
import com.event_horizon.items.ores.CopperIngot;
import com.event_horizon.items.ores.CopperOreItem;
import com.event_horizon.items.ores.SilverIngot;
import com.event_horizon.items.ores.SilverOreItem;
import com.event_horizon.items.ores.SulfurOreItem;
import com.event_horizon.items.ores.TinIngot;
import com.event_horizon.items.ores.TinOreItem;
import com.event_horizon.items.ores.UraniumOreItem;
import com.event_horizon.items.ores.ZirconiumIngot;
import com.event_horizon.items.ores.ZirconiumOreItem;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegister
{
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EventHorizon.MOD_ID);
	
	//Item Blocks
	public static final RegistryObject<Item> FISSION_REACTOR = ITEMS.register("fission_reactor", FissionReactorItem::new);
	
	public static final RegistryObject<Item> ZIRCONIUM_ORE = ITEMS.register("zirconium_ore", ZirconiumOreItem::new);
	public static final RegistryObject<Item> ALUMINUM_ORE = ITEMS.register("aluminum_ore", AluminumOreItem::new);
	public static final RegistryObject<Item> URANIUM_ORE = ITEMS.register("uranium_ore", UraniumOreItem::new);
	public static final RegistryObject<Item> SILVER_ORE = ITEMS.register("silver_ore", SilverOreItem::new);
	public static final RegistryObject<Item> COPPER_ORE = ITEMS.register("copper_ore", CopperOreItem::new);
	public static final RegistryObject<Item> SULFUR_ORE = ITEMS.register("sulfur_ore", SulfurOreItem::new);
	public static final RegistryObject<Item> TIN_ORE = ITEMS.register("tin_ore", TinOreItem::new);
	
	//Items
	public static final RegistryObject<Item> ZIRCONIUM_INGOT = ITEMS.register("zirconium_ingot", ZirconiumIngot::new);
	public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot", AluminumIngot::new);
	public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", SilverIngot::new);
	public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", CopperIngot::new);
	public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", TinIngot::new);
	
	public static final RegistryObject<Item> URANIUM_PELLETTE = ITEMS.register("uranium_pellette", UraniumPellette::new);
	public static final RegistryObject<Item> URANIUM_POWDER = ITEMS.register("uranium_powder", UraniumPowder::new);
	public static final RegistryObject<Item> SULFUR_POWDER = ITEMS.register("sulfur_powder", SulfurPowder::new);
	public static final RegistryObject<Item> SILVER_ROD = ITEMS.register("silver_rod", SilverRod::new);
	public static final RegistryObject<Item> FUEL_ROD = ITEMS.register("fuel_rod", FuelRod::new);
	
	public static void init()
	{
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
