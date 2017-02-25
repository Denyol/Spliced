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

package me.denyol.spliced.integration.jei;

import me.denyol.spliced.api.SplicedRecipeManager;
import me.denyol.spliced.block.SplicedBlocks;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;

/**
 * Created by Daniel on 22/2/17.
 */
@JEIPlugin
public class JeiPlugin extends BlankModPlugin
{

	@Override
	public void register(IModRegistry registry)
	{
		registry.addRecipeCategories(new SplicerRecipeCategory(registry.getJeiHelpers().getGuiHelper()));

		registry.addRecipeHandlers(new SplicerRecipeHandler());

		registry.addRecipes(SplicedRecipeManager.getRecipes());

		registry.addRecipeCategoryCraftingItem(new ItemStack(SplicedBlocks.splicer), SplicerRecipeCategory.ID);
	}
}
