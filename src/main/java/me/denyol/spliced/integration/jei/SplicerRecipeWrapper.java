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
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 22/2/17.
 */
public class SplicerRecipeWrapper extends BlankRecipeWrapper
{

	private final List<ItemStack> inputs;
	private final List<ItemStack> outputs;
	private RecipeSplice recipe;

	public SplicerRecipeWrapper(RecipeSplice recipe)
	{
		this.recipe = recipe;
		List<ItemStack> inputs = new ArrayList<>();
		inputs.add(recipe.getBaseInput());
		inputs.add(recipe.getSecondaryInput());
		this.inputs = inputs;

		List<ItemStack> outputs = new ArrayList<>();
		outputs.add(recipe.getMainOutput());
		outputs.add(recipe.getSecondaryOutput());
		this.outputs = outputs;
	}

	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInputs(ItemStack.class, inputs);
		ingredients.setOutputs(ItemStack.class, outputs);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		minecraft.fontRendererObj.drawStringWithShadow("RF per tick: " + getEnergyPerTick(), 58, 19, Color.gray.getRGB());
		GlStateManager.color(1, 1, 1, 1);
	}

	public int getEnergyPerTick()
	{
		return recipe.getRequiredEnergy();
	}
}
