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

package me.denyol.spliced.client.gui;

import me.denyol.spliced.tileentity.TileEntitySplicer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

/**
 * Created by Daniel on 19/2/17.
 */
public class ContainerSplicer extends Container
{

	private TileEntitySplicer tileEntitySplicer;
	private int energy;
	private int splicingProgressTotal;
	private int splicingProgress;

	public ContainerSplicer(TileEntitySplicer tileEntitySplicer, InventoryPlayer inventoryPlayer)
	{
		this.tileEntitySplicer = tileEntitySplicer;

		addSlotToContainer(new SlotItemHandler(tileEntitySplicer.getInventory(), 0, 44, 17));
		addSlotToContainer(new SlotItemHandler(tileEntitySplicer.getInventory(), 1, 134, 17));
		addSlotToContainer(new SlotItemHandler(tileEntitySplicer.getInventory(), 2, 62, 58));
		addSlotToContainer(new SlotItemHandler(tileEntitySplicer.getInventory(), 3, 116, 58));

		addPlayerInventory(inventoryPlayer);
	}

	private void addPlayerInventory(InventoryPlayer playerInv)
	{
		for (int row = 0; row < 3; ++row)
		{
			for (int col = 0; col < 9; ++col)
			{
				this.addSlotToContainer(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return playerIn.getDistanceSq(tileEntitySplicer.getPos().add(0.5, 0.5, 0.5)) <= 64;
	}

	@Nullable
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack previous = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		InventoryPlayer playerInv = playerIn.inventory;

		if (slot != null && slot.getHasStack())
		{
			ItemStack current = slot.getStack();
			previous = current.copy();

			int minPlayerSlot = inventorySlots.size() - playerInv.mainInventory.length;
			if (index < minPlayerSlot)
			{
				if (!this.mergeItemStack(current, minPlayerSlot, this.inventorySlots.size(), true))
					return null;
			} else if (!this.mergeItemStack(current, 0, minPlayerSlot, false))
				return null;

			if (current.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}

		tileEntitySplicer.markDirty();

		return previous;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for(IContainerListener listener : listeners)
		{
			if(energy != this.tileEntitySplicer.getEnergy())
				listener.sendProgressBarUpdate(this, 0, this.tileEntitySplicer.getEnergy());
			if(splicingProgressTotal != this.tileEntitySplicer.getSplicingProgressTotal())
				listener.sendProgressBarUpdate(this, 1, tileEntitySplicer.getSplicingProgressTotal());
			if(splicingProgress != this.tileEntitySplicer.getSplicingProgress())
				listener.sendProgressBarUpdate(this, 2, tileEntitySplicer.getSplicingProgress());
		}

		this.energy = tileEntitySplicer.getEnergy();
		this.splicingProgressTotal = tileEntitySplicer.getSplicingProgressTotal();
		this.splicingProgress = tileEntitySplicer.getSplicingProgress();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data)
	{
		switch (id)
		{
			case 0:
				tileEntitySplicer.setEnergy(data);
				break;
			case 1:
				tileEntitySplicer.setSplicingProgressTotal(data);
				break;
			case 2:
				tileEntitySplicer.setSplicingProgress(data);
		}
	}
}
