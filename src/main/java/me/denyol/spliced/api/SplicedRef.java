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

import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by Daniel on 14/2/17.
 */
public final class SplicedRef
{

	public static final String MODID = "spliced";
	public static final String API_VERSION = "0.1";
	public static CreativeTabs creativeTab;

	/*
	Initialised during preInit
	 */
	public SplicedRef()
	{
		creativeTab = new SplicedCreativeTab();
	}

}