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

import me.denyol.spliced.Spliced;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by Daniel on 25/2/17.
 */
public class BlockGreatSapling extends BlockSapling
{
	public BlockGreatSapling(String registryName)
	{
		super();
		this.setRegistryName(registryName);
		this.setUnlocalizedName(registryName);
		this.setCreativeTab(Spliced.creativeTab);
	}

	public Item createItemBlock()
	{
		return new ItemBlock(this).setRegistryName(this.getRegistryName());
	}

	@Override
	public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
		WorldGenerator worldgenerator = new WorldGenTrees(true);

		worldgenerator.generate(worldIn, rand, pos);
		worldIn.setBlockState(pos, state, 4);
	}
}
