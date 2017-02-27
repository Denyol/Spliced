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

package me.denyol.spliced.world.gen;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

/**
 * Created by Daniel on 27/2/17.
 */
public class WorldGenGreatTree extends WorldGenAbstractTree
{

	/** The minimum height of a generated tree. */
	private final int minTreeHeight;
	/** The metadata value of the wood to use in tree generation. */
	private final IBlockState metaWood;
	/** The metadata value of the leaves to use in tree generation. */
	private final IBlockState metaLeaves;

	public WorldGenGreatTree(boolean notify, int minTreeHeight, IBlockState woodType, IBlockState leavesType)
	{
		super(notify);
		this.minTreeHeight = minTreeHeight;
		this.metaWood = woodType;
		this.metaLeaves = leavesType;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		int treeHeight = rand.nextInt(3) + this.minTreeHeight;
		boolean flag = true;

		if (position.getY() >= 1 && position.getY() + treeHeight + 1 <= worldIn.getHeight()) // check tree grows area
		{
			for (int y = position.getY(); y <= position.getY() + 1 + treeHeight; ++y)
			{
				int k = 1;

				if (y == position.getY())
				{
					k = 0;
				}

				if (y >= position.getY() + 1 + treeHeight - 2)
				{
					k = 2;
				}

				BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

				for (int x = position.getX() - k; x <= position.getX() + k && flag; ++x)
				{
					for (int z = position.getZ() - k; z <= position.getZ() + k && flag; ++z)
					{
						if (y >= 0 && y < worldIn.getHeight())
						{
							if (!this.isReplaceable(worldIn,mutableBlockPos.setPos(x, y, z)))
							{
								flag = false;
							}
						}
						else
						{
							flag = false;
						}
					}
				}
			}

			if (!flag)
			{
				return false;
			}
			else
			{
				IBlockState state = worldIn.getBlockState(position.down());

				if (state.getBlock().canSustainPlant(state, worldIn, position.down(), net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SAPLING) && position.getY() < worldIn.getHeight() - treeHeight - 1)
				{
					this.setDirtAt(worldIn, position.down());
					int k2 = 3;
					int l2 = 0;

					// add leaves
					for (int leavesHeight = position.getY() - 6 + treeHeight; leavesHeight <= position.getY() + treeHeight; ++leavesHeight)
					{
						int i4 = leavesHeight - (position.getY() + treeHeight);
						//Spliced.logger.info("i4, //leaves pos start: " + i4);
						int j1 = 1 - i4 / 2;
						//Spliced.logger.info("j1, //bulb size: " + j1);

						for (int k1 = position.getX() - j1; k1 <= position.getX() + j1; ++k1)
						{
							//Spliced.logger.info("k1, //leaves bulb width x: " + k1);
							int l1 = k1 - position.getX();
							//Spliced.logger.info("l1, //leaves bulb start pos x: " + l1);

							for (int i2 = position.getZ() - j1; i2 <= position.getZ() + j1; ++i2)
							{
								int j2 = i2 - position.getZ();

								if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0 && i4 != 0)
								{
									BlockPos blockpos = new BlockPos(k1, leavesHeight, i2);
									state = worldIn.getBlockState(blockpos);

									if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos) || state.getMaterial() == Material.VINE)
									{
										this.setBlockAndNotifyAdequately(worldIn, blockpos, this.metaLeaves);
									}
								}
							}
						}
					}

					for (int height = 0; height < treeHeight; ++height)
					{
						BlockPos upN = position.up(height);
						state = worldIn.getBlockState(upN);

						if (state.getBlock().isAir(state, worldIn, upN) || state.getBlock().isLeaves(state, worldIn, upN) || state.getMaterial() == Material.VINE)
						{
							this.setBlockAndNotifyAdequately(worldIn, position.up(height), this.metaWood);
						}
					}

					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
	}
}
