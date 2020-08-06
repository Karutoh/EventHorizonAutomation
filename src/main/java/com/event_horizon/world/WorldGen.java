package com.event_horizon.world;

import com.event_horizon.EventHorizon;
import com.event_horizon.registries.BlockRegister;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = EventHorizon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldGen
{
	@SubscribeEvent
	public static void generateOres(FMLLoadCompleteEvent event)
	{
		for (Biome biome : ForgeRegistries.BIOMES)
		{
			if (biome.getCategory() == Biome.Category.NETHER)
				continue;
			
			if (biome.getCategory() == Biome.Category.THEEND)
				continue;
			
			genOre(biome, 15, 8, 5, 45, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegister.ZIRCONIUM_ORE.get().getDefaultState(), 10);
			genOre(biome, 15, 8, 5, 45, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegister.ALUMINUM_ORE.get().getDefaultState(), 10);
			genOre(biome, 15, 8, 5, 45, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegister.URANIUM_ORE.get().getDefaultState(), 10);
			genOre(biome, 15, 8, 5, 45, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegister.SILVER_ORE.get().getDefaultState(), 10);
			genOre(biome, 15, 8, 5, 45, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegister.COPPER_ORE.get().getDefaultState(), 10);
			genOre(biome, 15, 8, 5, 45, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegister.SULFUR_ORE.get().getDefaultState(), 10);
			genOre(biome, 15, 8, 5, 45, OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegister.TIN_ORE.get().getDefaultState(), 10);
		}
	}
	
	private static void genOre(Biome biome, int count, int bottom, int top, int max, FillerBlockType filler, BlockState state, int size)
	{
		CountRangeConfig range = new CountRangeConfig(count, bottom, top, max);
		OreFeatureConfig feature = new OreFeatureConfig(filler, state, size);
		ConfiguredPlacement<CountRangeConfig> placement = Placement.COUNT_RANGE.configure(range);
		biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(feature).withPlacement(placement));
	}
}
