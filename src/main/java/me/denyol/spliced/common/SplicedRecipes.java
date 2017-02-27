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

package me.denyol.spliced.common;

import me.denyol.spliced.Spliced;
import me.denyol.spliced.api.SplicedRecipeManager;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by Daniel on 25/2/17.
 */
public final class SplicedRecipes
{
	public static void addRecipes()
	{
		SplicedRecipeManager.addSplicedRecipe("SplicedSandCobble", 60, 20,
				new ItemStack(Blocks.SAND),
				new ItemStack(Blocks.COBBLESTONE),
				new ItemStack(Blocks.GRAVEL),
				null);

		SplicedRecipeManager.addSplicedRecipe("SplicedDirtSeeds", 60, 25,
				new ItemStack(Blocks.DIRT),
				new ItemStack(Items.WHEAT_SEEDS, 32),
				new ItemStack(Blocks.GRASS),
				null);

		SplicedRecipeManager.addSplicedRecipe("SplicedCharcoalCoal", 100, 20,
				new ItemStack(Items.COAL, 2, 1),
				new ItemStack(Items.COAL),
				new ItemStack(Items.COAL, 2),
				null);

		SplicedRecipeManager.addSplicedRecipe("SplicedJungleSpruce", 60, 20,
				new ItemStack(Blocks.PLANKS, 1, 3),
				new ItemStack(Blocks.PLANKS, 1, 1),
				new ItemStack(Blocks.PLANKS, 1, 0),
				null);

		SplicedRecipeManager.addSplicedRecipe("SplicedCobbleCoalBlock", 80, 25,
				new ItemStack(Blocks.COBBLESTONE, 32),
				new ItemStack(Blocks.COAL_BLOCK),
				new ItemStack(Blocks.OBSIDIAN, 1),
				null);

		SplicedRecipeManager.addSplicedRecipe("SplicedNetherrackBrick", 60, 20,
				new ItemStack(Blocks.NETHERRACK),
				new ItemStack(Blocks.BRICK_BLOCK),
				new ItemStack(Blocks.NETHER_BRICK),
				null);

		SplicedRecipeManager.addSplicedRecipe("SplicedCoalCoal", 60, 20,
				new ItemStack(Items.COAL, 4),
				new ItemStack(Items.COAL, 4),
				new ItemStack(Blocks.COAL_BLOCK),
				null);

		SplicedRecipeManager.addSplicedRecipe("SplicedCobblestoneCoal", 80, 20,
				new ItemStack(Blocks.COBBLESTONE),
				new ItemStack(Items.COAL, 4),
				new ItemStack(Blocks.COAL_ORE),
				null);

		for (BlockPlanks.EnumType type : BlockPlanks.EnumType.values())
		{
			if(type == BlockPlanks.EnumType.ACACIA || type == BlockPlanks.EnumType.DARK_OAK)
				continue;
			SplicedRecipeManager.addSplicedRecipe("SplicedSaplingBone"+type.toString(), 60, 15,
					new ItemStack(Items.BONE, 3),
					new ItemStack(Blocks.SAPLING, 1, type.getMetadata()),
					new ItemStack(Spliced.greatSapling, 1,type.getMetadata()),
					null);
		}
	}
}
