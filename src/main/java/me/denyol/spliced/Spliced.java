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
import me.denyol.spliced.block.SplicedBlocks;
import me.denyol.spliced.common.SplicedGuiHandler;
import me.denyol.spliced.common.SplicedRecipes;
import me.denyol.spliced.proxy.ISplicedProxy;
import me.denyol.spliced.tileentity.SplicedTileEntities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.Logger;

@Mod(modid = SplicedRef.MODID, version = Spliced.VERSION, name = Spliced.NAME, acceptedMinecraftVersions = "[1.10.2]")
public class Spliced
{
    public static final String NAME = "Spliced";
	public static final String VERSION = "@VERSION@";

    @Mod.Instance(SplicedRef.MODID)
    public static Spliced instance;

	@SidedProxy(clientSide = "me.denyol.spliced.proxy.ClientProxy", serverSide = "me.denyol.spliced.proxy.ServerProxy")
	public static ISplicedProxy proxy;

	public static Logger logger;

	public SplicedRef ref;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		ref = new SplicedRef();

		SplicedBlocks.loadBlocks();

		proxy.registerBlockInventoryRender();

		SplicedTileEntities.registerTileEntities();

		ShapedOreRecipe recipe = new ShapedOreRecipe(SplicedBlocks.splicer, " L ", "BSB", "III",
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
