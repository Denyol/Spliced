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

import me.denyol.spliced.api.SplicedRef;
import me.denyol.spliced.tileentity.TileEntitySplicer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 19/2/17.
 */
public class GuiSplicer extends GuiContainer
{

	private static final ResourceLocation background = new ResourceLocation(SplicedRef.MODID, "textures/gui/splicer.png");

	private final TileEntitySplicer tileEntity;

	public GuiSplicer(TileEntitySplicer tileEntitySplicer, InventoryPlayer inventoryPlayer)
	{
		super(new ContainerSplicer(tileEntitySplicer, inventoryPlayer));

		this.tileEntity = tileEntitySplicer;

		xSize = 176;
		ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0 ,0, xSize, ySize);

		if(tileEntity.getSplicingProgress() > 0)
		{
			int progress = getProgressScaled(27);
			this.drawTexturedModalRect(guiLeft + 93, guiTop + 29, 177, 1, 7, progress);
		}

		int energyLeft = getEnergyLeftScaled(58);
		this.drawTexturedModalRect(guiLeft + 8, guiTop + 16 + 58 - energyLeft, 177, 29, 16, energyLeft);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		int mouseRealX = mouseX - guiLeft;
		int mouseRealY = mouseY - guiTop;
		if(mouseRealX >= 8 && mouseRealY >= 16 && mouseRealX <= 24 && mouseRealY <= 74)
		{
			List<String> text = new ArrayList<>();
			text.add(tileEntity.getEnergy() + " of");
			text.add(TileEntitySplicer.CAPACITY + " §7RF§r");
			drawHoveringText(text, mouseRealX, mouseRealY, mc.fontRendererObj);
		}
	}

	public int getEnergyLeftScaled(int pixels)
	{
		return tileEntity.getEnergy() * pixels / TileEntitySplicer.CAPACITY;
	}

	public int getProgressScaled(int pixels)
	{
		int total = tileEntity.getSplicingProgressTotal();
		int currentProgress = tileEntity.getSplicingProgress();
		return currentProgress * pixels / total;
	}
}
