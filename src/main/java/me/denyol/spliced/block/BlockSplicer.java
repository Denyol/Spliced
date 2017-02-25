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
import me.denyol.spliced.common.SplicedGuiHandler;
import me.denyol.spliced.tileentity.TileEntitySplicer;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * Created by Daniel on 18/2/17.
 */
public class BlockSplicer extends Block
{

	public static final PropertyDirection PROPERTY_FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool PROPERTY_ACTIVE = PropertyBool.create("active");

	public BlockSplicer(String registryName, Material material)
	{
		super(material);
		this.setRegistryName(registryName);
		this.setUnlocalizedName(registryName);
		this.setCreativeTab(Spliced.creativeTab);

		this.setDefaultState(this.getDefaultState().withProperty(PROPERTY_FACING, EnumFacing.NORTH).withProperty(PROPERTY_ACTIVE, false));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing facing = EnumFacing.getHorizontal(meta & 3);

		boolean active = meta >> 2 != 0; // if its not 0 active = true

		return this.getDefaultState().withProperty(PROPERTY_FACING, facing).withProperty(PROPERTY_ACTIVE, active);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		EnumFacing facing = state.getValue(PROPERTY_FACING);
		boolean active = state.getValue(PROPERTY_ACTIVE);

		return facing.getHorizontalIndex() | ((active ? 1 : 0) << 2);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_FACING, PROPERTY_ACTIVE);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		if(placer != null)
		{
			return this.getDefaultState().withProperty(PROPERTY_FACING, placer.getHorizontalFacing().getOpposite()).withProperty(PROPERTY_ACTIVE, false);
		}

		return this.getDefaultState().withProperty(PROPERTY_FACING, EnumFacing.NORTH).withProperty(PROPERTY_ACTIVE, false);
	}

	@Override
	public boolean canSpawnInBlock()
	{
		return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		return super.isBlockSolid(worldIn, pos, side);
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public MapColor getMapColor(IBlockState state)
	{
		return MapColor.GRAY;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0, 0, 0, 1, 14/16f, 1);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntitySplicer();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote)
			playerIn.openGui(Spliced.instance, SplicedGuiHandler.GuiID.SPLICER.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if(tileEntity!= null && tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
		{
			ItemStackHandler itemHandler = (ItemStackHandler) tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			for(int i = 0; i < itemHandler.getSlots(); i++)
			{
				if(itemHandler.getStackInSlot(i) == null)
					continue;
				EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemHandler.getStackInSlot(i));

				item.motionX = (worldIn.rand.nextFloat() - 0.5f) * 0.1f;
				item.motionY = (worldIn.rand.nextFloat() - 0.5f) * 0.1f;
				item.motionZ = (worldIn.rand.nextFloat() - 0.5f) * 0.1f;

				worldIn.spawnEntityInWorld(item);
			}
		}

		super.breakBlock(worldIn, pos, state);
	}

	public Item createItemBlock()
	{
		return new ItemBlock(this).setRegistryName(this.getRegistryName());
	}
}
