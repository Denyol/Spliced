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

import com.google.common.base.Predicate;
import me.denyol.spliced.Spliced;
import me.denyol.spliced.world.gen.WorldGenGreatTree;
import net.minecraft.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Created by Daniel on 25/2/17.
 */
public class BlockGreatSapling extends BlockBush implements IGrowable
{
	public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.create("type", BlockPlanks.EnumType.class, new Predicate<BlockPlanks.EnumType>()
	{
		@Override
		public boolean apply(@Nullable BlockPlanks.EnumType input)
		{
			return input.getMetadata() <= 3;
		}
	});
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
	protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

	public BlockGreatSapling()
	{
		this("great_sapling");
	}

	public BlockGreatSapling(String registryName)
	{
		this.setRegistryName(registryName);
		this.setUnlocalizedName(registryName);
		this.setCreativeTab(Spliced.creativeTab);
		this.setDefaultState(this.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.OAK).withProperty(STAGE, 0));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return SAPLING_AABB;
	}

	private void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
		WorldGenerator worldgenerator;

		IBlockState woodMeta;
		IBlockState leavesMeta;

		//should probably we re-written
		switch (state.getValue(VARIANT))
		{
			case SPRUCE:
				woodMeta = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
				leavesMeta = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, false);
				break;
			case BIRCH:
				woodMeta = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH);
				leavesMeta = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH).withProperty(BlockLeaves.CHECK_DECAY, false);
				break;
			case JUNGLE:
				woodMeta = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
				leavesMeta = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty(BlockLeaves.CHECK_DECAY, false);
				break;
			default:
				woodMeta = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
				leavesMeta = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY, false);
				break;

		}

		worldgenerator = new WorldGenGreatTree(true, 15, woodMeta, leavesMeta);

		IBlockState airState = Blocks.AIR.getDefaultState();
		worldIn.setBlockState(pos, airState, 4);

		if(!worldgenerator.generate(worldIn, rand, pos))
			worldIn.setBlockState(pos, state, 4);
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
		{
			super.updateTick(worldIn, pos, state, rand);

			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
			{
				this.grow(worldIn, pos, state, rand);
			}
		}
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		for (BlockPlanks.EnumType type : BlockPlanks.EnumType.values())
		{
			if(type.getMetadata() > 3)
				break;
			list.add(new ItemStack(itemIn, 1, type.getMetadata()));
		}
	}

	/**
	 * Check whether the given BlockPos has a Sapling of the given type
	 */
	public boolean isTypeAt(World worldIn, BlockPos pos, BlockPlanks.EnumType type)
	{
		IBlockState blockstate = worldIn.getBlockState(pos);
		return blockstate.getBlock() == this && blockstate.getValue(VARIANT) == type;
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
	 * returns the metadata of the dropped item based on the old metadata of the block.
	 */
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(VARIANT).getMetadata();
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.byMetadata(meta & 7)).withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | state.getValue(VARIANT).getMetadata();
		i = i | (int)state.getValue(STAGE) << 3;
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT, STAGE});
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return (double)worldIn.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		this.grow(worldIn, pos, state, rand);
	}

	private void grow(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if ((int)state.getValue(STAGE) == 0)
		{
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		}
		else
		{
			this.generateTree(worldIn, pos, state, rand);
		}
	}
}
