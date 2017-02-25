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

package me.denyol.spliced.api;

import me.denyol.spliced.api.recipe.RecipeSplice;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel on 14/2/17.
 * @author Daniel Tucker
 */
public final class SplicedRecipeManager
{
	private static final Map<String, RecipeSplice> splicedRecipes = new HashMap<>();

	/**
	 * Registers a splicing recipe.
	 * @param recipe the spliced recipe
	 * @return boolean indicating if the recipe was added successfully
	 */
	public static boolean registerSplicedRecipe(RecipeSplice recipe)
	{

		if(SplicedRecipeManager.getRecipesForInputs(recipe.getBaseInput(), recipe.getSecondaryInput()).size() > 1)
			return false;

		if(!splicedRecipes.containsKey(recipe.getUID()))
		{
			splicedRecipes.put(recipe.getUID(), recipe);
			return true;
		}
		return false;
	}

	/**
	 * Gets the splicing blocks recipe from its unique ID.
	 * @param UID unique blocks recipe ID
	 * @return {@link RecipeSplice} or null if it is not registered
	 */
	@Nullable
	public static RecipeSplice getRecipe(String UID)
	{
		return splicedRecipes.get(UID);
	}

	@Nullable
	public static List<RecipeSplice> getRecipesForInputs(ItemStack baseInput, ItemStack secondaryInput)
	{
		if(baseInput == null || secondaryInput == null)
			return null;

		List<RecipeSplice> result = new ArrayList<>();
		for(RecipeSplice value : splicedRecipes.values())
		{
			if(value.getBaseInput().isItemEqual(baseInput) && value.getSecondaryInput().isItemEqual(secondaryInput))
				result.add(value);
		}

		return result;
	}

	public static void addSplicedRecipe(@Nonnull String UID, int processTicks, int requiredEnergyPerTick, @Nonnull ItemStack baseInput, @Nonnull ItemStack secondaryInput, @Nonnull ItemStack mainOutput, ItemStack secondaryOutput)
	{
		registerSplicedRecipe(new RecipeSplice(UID, processTicks, requiredEnergyPerTick, baseInput, secondaryInput, mainOutput, secondaryOutput));
	}

	public static List<RecipeSplice> getRecipes()
	{
		List<RecipeSplice> recipes = new ArrayList<>();
		for(RecipeSplice val : splicedRecipes.values())
			recipes.add(val);

		return recipes;
	}

}
