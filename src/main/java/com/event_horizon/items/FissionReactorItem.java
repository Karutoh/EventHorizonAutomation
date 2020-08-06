package com.event_horizon.items;

import com.event_horizon.EventHorizon;
import com.event_horizon.registries.BlockRegister;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class FissionReactorItem extends BlockItem
{
	public FissionReactorItem()
	{
		super(BlockRegister.FISSION_REACTOR.get(), new Item.Properties()
			.group(EventHorizon.TAB)
		);
	}
}
