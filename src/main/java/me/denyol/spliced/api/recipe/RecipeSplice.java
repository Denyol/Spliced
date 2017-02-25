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

package me.denyol.spliced.api.recipe;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.lang.Math.abs;

/**
 * Created by Daniel on 14/2/17.
 */
public class RecipeSplice
{

	private final ItemStack baseInput;
	private final ItemStack secondaryInput;
	private final ItemStack mainOutput;
	private ItemStack secondaryOutput;
	public final int processTicks;

	private final String UID;

	private final int requiredEnergy;

	/**
	 * @param UID a unique spliced blocks recipe ID
	 * @param requiredEnergy required energy to craft the outputs, if this is negative the absolute value will be used
	 * @param baseInput base input ItemStack
	 * @param secondaryInput secondary input ItemStack
	 * @param mainOutput main output ItemStack
	 * @param secondaryOutput secondary output ItemStack
	 */
	public RecipeSplice(String UID, int processTicks, int requiredEnergy, @Nonnull ItemStack baseInput, @Nonnull ItemStack secondaryInput, @Nonnull ItemStack mainOutput, ItemStack secondaryOutput)
	{
		this.requiredEnergy = abs(requiredEnergy);
		this.UID = UID;
		this.processTicks = processTicks;
		this.baseInput = baseInput;
		this.secondaryInput = secondaryInput;
		this.mainOutput = mainOutput;
		this.secondaryOutput = secondaryOutput;
	}

	/**
	 * @param UID a unique spliced blocks recipe ID
	 * @param requiredEnergy required energy to craft the outputs, if this is negative the absolute value will be used
	 * @param baseInput base input ItemStack
	 * @param secondaryInput secondary input ItemStack
	 * @param mainOutput main output ItemStack
	 */
	public RecipeSplice(String UID, int processTicks, int requiredEnergy, @Nonnull ItemStack baseInput, @Nonnull ItemStack secondaryInput, @Nonnull ItemStack mainOutput)
	{
		this.requiredEnergy = abs(requiredEnergy);
		this.UID = UID;
		this.processTicks = processTicks;
		this.baseInput = baseInput;
		this.secondaryInput = secondaryInput;
		this.mainOutput = mainOutput;
	}

	@Deprecated
	public boolean isValid()
	{
		return baseInput != null && secondaryInput != null && mainOutput != null && UID.length() > 0;
	}

	/**
	 * Gets the unique recipe ID
	 * @return unique recipe ID
	 */
	public String getUID()
	{
		return UID;
	}

	public ItemStack getBaseInput()
	{
		return baseInput.copy();
	}

	public ItemStack getSecondaryInput()
	{
		return secondaryInput.copy();
	}

	public ItemStack getMainOutput()
	{
		return mainOutput.copy();
	}

	@Nullable
	public ItemStack getSecondaryOutput()
	{
		if(secondaryOutput != null)
			return secondaryOutput.copy();

		return null;
	}

	public int getRequiredEnergy()
	{
		return requiredEnergy;
	}
}
