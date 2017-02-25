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

package me.denyol.spliced.item;

import me.denyol.spliced.api.SplicedRef;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by Daniel on 10/2/17.
 */
public class SplicedItems
{

	public static ItemKnife knife;

	public static void register()
	{
		knife = new ItemKnife(ItemTypes.KNIFE ,Item.ToolMaterial.IRON);
	}

	public enum ItemTypes
	{
		KNIFE("splicer", "splicer_knife");

		private String unlocalizedName;
		private String registryName; // should be unique
		private CreativeTabs creativeTab;

		ItemTypes(String unlocalizedName, String registryName)
		{
			this.unlocalizedName = unlocalizedName;
			this.registryName = registryName;
			this.creativeTab = SplicedRef.creativeTab;
		}

		public CreativeTabs getCreativeTab()
		{
			return this.creativeTab;
		}

		public String getUnlocalizedName()
		{
			return this.unlocalizedName;
		}

		public String getRegistryName()
		{
			return this.registryName;
		}
	}
}
