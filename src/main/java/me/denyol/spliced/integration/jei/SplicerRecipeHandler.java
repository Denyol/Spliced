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

import me.denyol.spliced.api.recipe.RecipeSplice;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

/**
 * Created by Daniel on 22/2/17.
 */
public class SplicerRecipeHandler implements IRecipeHandler<RecipeSplice>
{

	@Override
	public Class<RecipeSplice> getRecipeClass()
	{
		return RecipeSplice.class;
	}

	@Override
	public String getRecipeCategoryUid()
	{
		return SplicerRecipeCategory.ID;
	}

	@Override
	public String getRecipeCategoryUid(RecipeSplice recipe)
	{
		return this.getRecipeCategoryUid();
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(RecipeSplice recipe)
	{
		return new SplicerRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(RecipeSplice recipe)
	{
		return true;
	}
}
