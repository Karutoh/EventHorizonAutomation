package com.event_horizon.items.ores;

import com.event_horizon.EventHorizon;
import com.event_horizon.registries.BlockRegister;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class CopperOreItem extends BlockItem
{
	public CopperOreItem()
	{
		super(BlockRegister.COPPER_ORE.get(), new Item.Properties()
			.group(EventHorizon.TAB)
		);
	}
}
