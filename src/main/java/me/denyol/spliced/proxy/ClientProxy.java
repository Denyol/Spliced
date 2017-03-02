/*
 * <Spliced Minecraft Forge mod>
 *     Copyright (C) <2017>  <Daniel Tucker>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.denyol.spliced.proxy;

import me.denyol.spliced.Spliced;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

/**
 * Created by Daniel on 10/2/17.
 */
public class ClientProxy implements ISplicedProxy
{
	@Override
	public void registerBlockInventoryRender()
	{
		registerForInventoryRendering(Spliced.splicer, "active=false,facing=north", 0);
		registerForInventoryRendering(Spliced.itemDarkSteelPickaxe, "layer0", 0);
		registerForInventoryRendering(Spliced.itemDarkSteelAxe, "layer0", 0);
		registerForInventoryRendering(Spliced.itemDarkSteelShovel, "layer0", 0);
		registerForInventoryRendering(Spliced.itemDarkSteelSword, "layer0", 0);
		registerForInventoryRendering(Spliced.itemDarkSteelHoe, "layer0", 0);

		for(BlockPlanks.EnumType type : BlockPlanks.EnumType.values())
		{
			if(type.getMetadata() > 3)
				break;
			ModelResourceLocation model = new ModelResourceLocation(Spliced.itemBlockGreatSapling.getRegistryName(), "inventory,type=" + type.toString());
			ModelLoader.setCustomModelResourceLocation(Spliced.itemBlockGreatSapling, type.getMetadata(), model);
		}
	}

	public void registerForInventoryRendering(Block block, String variant, int meta)
	{
		Item item = Item.getItemFromBlock(block);
		if(item != null)
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(block.getRegistryName(), variant));
	}

	public void registerForInventoryRendering(Item item, String variant, int meta)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), variant));
	}
}
