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

import net.minecraftforge.energy.EnergyStorage;

/**
 * Created by Daniel on 18/2/17.
 */
public class SplicedEnergyStorage extends EnergyStorage
{
	public SplicedEnergyStorage(int capacity)
	{
		super(capacity);
	}

	public SplicedEnergyStorage(int capacity, int maxTransfer)
	{
		super(capacity, maxTransfer, maxTransfer);
	}

	public SplicedEnergyStorage(int capacity, int maxReceive, int maxExtract)
	{
		super(capacity, maxReceive, maxExtract);
	}

	public void setEnergy(int amount)
	{
		energy = amount;
	}
}
