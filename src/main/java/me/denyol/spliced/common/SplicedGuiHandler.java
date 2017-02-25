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

import me.denyol.spliced.client.gui.ContainerSplicer;
import me.denyol.spliced.client.gui.GuiSplicer;
import me.denyol.spliced.tileentity.TileEntitySplicer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by Daniel on 19/2/17.
 */
public class SplicedGuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
		if(ID == GuiID.SPLICER.ordinal() && te instanceof TileEntitySplicer)
			return new ContainerSplicer((TileEntitySplicer) te, player.inventory);

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		if(ID == GuiID.SPLICER.ordinal() && te instanceof TileEntitySplicer)
			return new GuiSplicer((TileEntitySplicer) te, player.inventory);

		return null;
	}

	public enum GuiID
	{
		SPLICER
	}
}
