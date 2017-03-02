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

package me.denyol.spliced;

import me.denyol.spliced.api.SplicedRef;
import me.denyol.spliced.block.BlockGreatSapling;
import me.denyol.spliced.block.BlockSplicer;
import me.denyol.spliced.common.SplicedGuiHandler;
import me.denyol.spliced.common.SplicedRecipes;
import me.denyol.spliced.item.*;
import me.denyol.spliced.proxy.ISplicedProxy;
import me.denyol.spliced.tileentity.SplicedTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
@Mod(modid = SplicedRef.MODID, version = Spliced.VERSION, name = Spliced.NAME, acceptedMinecraftVersions = "[1.10.2]")
public class Spliced
{
    public static final String NAME = "Spliced";
	public static final String VERSION = "@VERSION@";

	public static CreativeTabs creativeTab = new CreativeTabs(SplicedRef.MODID)
	{
		@Override
		public Item getTabIconItem()
		{
			return Item.getItemFromBlock(splicer);
		}
	};

	public static Item.ToolMaterial darkSteel = EnumHelper.addToolMaterial("darkSteel", 3, 1820, 8.5f, 3.0F, 12);

	public static final BlockSplicer splicer = new BlockSplicer("splicer", Material.IRON);
	public static final BlockGreatSapling greatSapling = new BlockGreatSapling("great_sapling");

	public static final ItemBlockGreatSapling itemBlockGreatSapling = new ItemBlockGreatSapling(greatSapling);
	public static final ItemDarkSteelPickaxe itemDarkSteelPickaxe = new ItemDarkSteelPickaxe("dark_steel_pickaxe", darkSteel);
	public static final ItemDarkSteelSword itemDarkSteelSword = new ItemDarkSteelSword("dark_steel_sword", darkSteel);
	public static final ItemDarkSteelAxe itemDarkSteelAxe = new ItemDarkSteelAxe("dark_steel_axe", darkSteel);
	public static final ItemDarkSteelShovel itemDarkSteelShovel = new ItemDarkSteelShovel("dark_steel_shovel", darkSteel);
	public static final ItemDarkSteelHoe itemDarkSteelHoe = new ItemDarkSteelHoe("dark_steel_hoe", darkSteel);

	@Mod.Instance(SplicedRef.MODID)
    public static Spliced instance;

	@SidedProxy(clientSide = "me.denyol.spliced.proxy.ClientProxy", serverSide = "me.denyol.spliced.proxy.ServerProxy")
	public static ISplicedProxy proxy;

	public static Logger logger;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(splicer);
		event.getRegistry().register(greatSapling);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				splicer.createItemBlock(),
				itemBlockGreatSapling.setRegistryName("great_sapling"),
				itemDarkSteelPickaxe, itemDarkSteelSword, itemDarkSteelAxe, itemDarkSteelShovel, itemDarkSteelHoe
		);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		proxy.registerBlockInventoryRender();

		SplicedTileEntities.registerTileEntities();

		ShapedOreRecipe recipe = new ShapedOreRecipe(splicer, " L ", "BSB", "III",
				'L', new ItemStack(Blocks.STONE_SLAB, 1, 0), 'B', "barsIron", 'S', new ItemStack(Items.DIAMOND_SWORD), 'I', "blockIron");
		GameRegistry.addRecipe(recipe);

		SplicedRecipes.addRecipes();
	}

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		NetworkRegistry.INSTANCE.registerGuiHandler(Spliced.instance, new SplicedGuiHandler());
    }
}
