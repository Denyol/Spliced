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

package me.denyol.spliced.tileentity;

import me.denyol.spliced.api.SplicedRecipeManager;
import me.denyol.spliced.api.recipe.RecipeSplice;
import me.denyol.spliced.block.SplicedEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

/**
 * Created by Daniel on 18/2/17.
 */
public class TileEntitySplicer extends TileEntity implements ITickable
{

	private int splicingProgress = 0;
	private int splicingProgressTotal = 0;
	public  int energyPerTick = 20;
	public static final int CAPACITY = 100000;

	/**
	 * Slots:
	 * 	0: Base input
	 * 	1: Secondary input
	 * 	2: Main output
	 * 	3: Secondary Output
	 */
	private ItemStackHandler inventory = new ItemStackHandler(4)
	{
		@Override
		protected void onContentsChanged(int slot)
		{
			markDirty();
		}
	};

	private SplicedEnergyStorage energyStorage = new SplicedEnergyStorage(TileEntitySplicer.CAPACITY);

	public TileEntitySplicer()
	{
		super();
	}

	/**
	 * Slots:
	 * 	0: Base input
	 * 	1: Secondary input
	 * 	2: Main output
	 * 	3: Secondary Output
	 */
	public ItemStackHandler getInventory()
	{
		return inventory;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return newState.getBlock() != oldState.getBlock();
	}

	/* --- Capabilities --- */
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| capability == CapabilityEnergy.ENERGY
				|| super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		else if (capability == CapabilityEnergy.ENERGY)
			return CapabilityEnergy.ENERGY.cast(energyStorage);
		return super.getCapability(capability, facing);
	}

	/* --- NBT Data --- */

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setTag("energy", CapabilityEnergy.ENERGY.writeNBT(energyStorage, null));
		compound.setTag("splicingProgress", new NBTTagInt(getSplicingProgress()));
		compound.setTag("splicingProgressTotal", new NBTTagInt(splicingProgressTotal));
		compound.setTag("energyPerTick", new NBTTagInt(energyPerTick));
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		if (compound.hasKey("energy"))
			CapabilityEnergy.ENERGY.readNBT(energyStorage, null, compound.getTag("energy"));
		if(compound.hasKey("splicingProgress"))
			splicingProgress = compound.getInteger("splicingProgress");
		if(compound.hasKey("splicingProgressTotal"))
			splicingProgressTotal = compound.getInteger("splicingProgressTotal");
		if (compound.hasKey("energyPerTick"))
			energyPerTick = compound.getInteger("energyPerTick");
	}

	/* --- ITickable --- */

	@Override
	public void update()
	{
		if(!worldObj.isRemote) // make sure calc happens on the server
		{
			List<RecipeSplice> recipes = SplicedRecipeManager.getRecipesForInputs(inventory.getStackInSlot(0), inventory.getStackInSlot(1));

			if(recipes == null || recipes.size() == 0 || recipes.size() > 1)
			{
				this.splicingProgress = 0;
				this.splicingProgressTotal = 0;
				return;
			}

			if(inventory.getStackInSlot(0).stackSize < recipes.get(0).getBaseInput().stackSize)
			{
				this.splicingProgress = 0;
				this.splicingProgressTotal = 0;
				return;
			}
			else if (inventory.getStackInSlot(1).stackSize < recipes.get(0).getSecondaryInput().stackSize)
			{
				this.splicingProgress = 0;
				this.splicingProgressTotal = 0;
				return;
			}

			// start
			if(canSplice(recipes.get(0)) && this.splicingProgressTotal == 0) // makes sure the machine isn't active and slots are not empty
			{
				this.splicingProgressTotal = getProgressTicksForRecipe(recipes.get(0));
				this.energyPerTick = recipes.get(0).getRequiredEnergy();
			}

			//continue
			if(splicingProgressTotal > 0 && canSplice(recipes.get(0)))
			{
				this.energyStorage.extractEnergy(energyPerTick, false);
				this.splicingProgress++;

				if(this.splicingProgress == this.splicingProgressTotal) // spliced
				{
					// reset timers
					this.splicingProgress = 0;
					this.splicingProgressTotal = 0;
					splice(recipes.get(0));
					markDirty();
				}
			}
			else
			{
				this.splicingProgress = 0;
				this.splicingProgressTotal = 0;
			}
		}
	}

	/* --- Splicing --- */

	private int getProgressTicksForRecipe(RecipeSplice recipeSplicedBlock)
	{
		return recipeSplicedBlock.processTicks;
	}

	/**
	 * Checks if the outputs are empty.
	 * @return boolean indicating if the machine could splice
	 */
	private boolean canSplice(RecipeSplice recipeSplice)
	{
		if(inventory.getStackInSlot(0) != null && inventory.getStackInSlot(1) != null && getEnergy() > 0)
		{
			if(inventory.getStackInSlot(2) == null && inventory.getStackInSlot(3) == null)
				return true;
			else if(inventory.getStackInSlot(2) != null && inventory.getStackInSlot(2).isItemEqual(recipeSplice.getMainOutput())
					&& (inventory.getStackInSlot(2).getMaxStackSize()-inventory.getStackInSlot(2).stackSize) >= recipeSplice.getMainOutput().stackSize)
				return true;
			else if(inventory.getStackInSlot(3) != null && inventory.getStackInSlot(3).isItemEqual(recipeSplice.getSecondaryOutput())
					&& (inventory.getStackInSlot(3).getMaxStackSize()-inventory.getStackInSlot(3).stackSize) >= recipeSplice.getMainOutput().stackSize)
				return true;
		}
		return false;
	}

	private void splice(RecipeSplice recipeSplice)
	{
		inventory.extractItem(0, recipeSplice.getBaseInput().stackSize, false);
		inventory.extractItem(1, recipeSplice.getSecondaryInput().stackSize, false);

		inventory.insertItem(2, recipeSplice.getMainOutput(), false);
		inventory.insertItem(3, recipeSplice.getSecondaryOutput(), false);
	}

	/* --- Sync Stuff --- */

	public int getEnergy()
	{
		return energyStorage.getEnergyStored();
	}

	public void setEnergy(int amount)
	{
		energyStorage.setEnergy(amount);
	}

	public int getSplicingProgress()
	{
		return splicingProgress;
	}

	public void setSplicingProgress(int amount)
	{
		splicingProgress = amount;
	}

	public int setSplicingProgressTotal(int ticks) {return splicingProgressTotal = ticks;}

	public int getSplicingProgressTotal()
	{
		return splicingProgressTotal;
	}
}
