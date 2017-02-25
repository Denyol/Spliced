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

import me.denyol.spliced.api.SplicedRef;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Daniel on 22/2/17.
 */
public class SplicerRecipeCategory extends BlankRecipeCategory<SplicerRecipeWrapper>
{

	public static final String ID = SplicedRef.MODID + ".splicerRecipeCategory";
	private IDrawable background;

	public SplicerRecipeCategory(IGuiHelper helper)
	{
		background = helper.createDrawable(new ResourceLocation(SplicedRef.MODID, "textures/gui/splicer.png"), 43, 16, 108, 59);
	}

	@Override
	public String getUid()
	{
		return ID;
	}

	@Override
	public String getTitle()
	{
		return I18n.format("tile.splicer.name");
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SplicerRecipeWrapper recipeWrapper, IIngredients ingredients)
	{
		IGuiItemStackGroup guiItemStack = recipeLayout.getItemStacks();

		guiItemStack.init(0, true, 0, 0);
		guiItemStack.init(1, true, 90, 0);
		guiItemStack.init(2, false, 18, 41);
		guiItemStack.init(3, false, 72, 41);

		guiItemStack.set(0, ingredients.getInputs(ItemStack.class).get(0));
		guiItemStack.set(1, ingredients.getInputs(ItemStack.class).get(1));
		guiItemStack.set(2, ingredients.getOutputs(ItemStack.class).get(0));
		guiItemStack.set(3, ingredients.getOutputs(ItemStack.class).get(1));
	}
}
