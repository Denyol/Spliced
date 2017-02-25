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

package me.denyol.spliced.block;

import me.denyol.spliced.api.SplicedRef;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by Daniel on 18/2/17.
 */
public final class SplicedBlocks
{

	public static BlockBase splicer;

	/**
	 * Instantiates the blocks and calls its register method.
	 */
	public static void loadBlocks()
	{
		splicer = new BlockSplicer(ModBlocks.SPLICER);
	}

	public static void registerItemBlockRendering()
	{
		splicer.registerForInventoryRendering();
	}

	public enum ModBlocks
	{
		SPLICER("splicer", "splicer", Material.IRON);

		private String unlocalizedName;
		private String registryName; // should be unique
		private CreativeTabs creativeTabs;
		private Material blockMaterial;

		ModBlocks(String unlocalizedName, String registryName, Material material)
		{
			this.unlocalizedName = unlocalizedName;
			this.registryName = registryName;
			this.creativeTabs = SplicedRef.creativeTab;
			this.blockMaterial = material;
		}

		public String getUnlocalizedName()
		{
			return unlocalizedName;
		}

		public String getRegistryName()
		{
			return registryName;
		}

		public CreativeTabs getCreativeTabs()
		{
			return creativeTabs;
		}

		public Material getMaterial()
		{
			return blockMaterial;
		}

	}
}
