package com.event_horizon.gui.screen;

import com.event_horizon.EventHorizon;
import com.event_horizon.capabilities.IFission;
import com.event_horizon.gui.container.FissionReactorContainer;
import com.event_horizon.items.SilverRod;
import com.event_horizon.registries.CapabilityRegister;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FissionReactorScreen extends ContainerScreen<FissionReactorContainer>
{
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(EventHorizon.MOD_ID, "textures/gui/container/fission_reactor.png");
	
	public FissionReactorScreen(FissionReactorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		super(screenContainer, inv, titleIn);
		guiLeft = 0;
		guiTop = 0;
		xSize = 175;
		ySize = 165;
	}
	
	@Override
	public void render(final MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks)
	{
		renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		
		int blitX = (width - xSize) / 2;
		int blitY = (height - ySize) / 2;
		
		blit(matrixStack, blitX, blitY, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y)
	{
		super.drawGuiContainerForegroundLayer(matrixStack, x, y);
		
		ItemStack fuel = this.getContainer().getTileEntity().getItems().get(2);
		IFission fission = fuel.getCapability(CapabilityRegister.FISSION).orElse(null);
		if (fission == null)
		{
			font.drawString(matrixStack, "Status: Inactive", 85.0f, 23.0f, 0xFF0000);
			font.drawString(matrixStack, "Temp: 0K", 85.0f, 23.0f + font.FONT_HEIGHT + 3.0f, 0xFFFFFF);
			font.drawString(matrixStack, "Neut: 0", 85.0f, 23.0f + (font.FONT_HEIGHT + 3.0f) * 2, 0xFFFFFF);
		}
		else
		{
			if (fission.getNeutrons() > 0)
				font.drawString(matrixStack, "Status: Active", 85.0f, 23.0f, 0x00FF00);
			else
				font.drawString(matrixStack, "Status: Inactive", 85.0f, 23.0f, 0xFF0000);
			
			font.drawString(matrixStack, "Temp: " + fission.getTemperature() + "K", 85.0f, 23.0f + font.FONT_HEIGHT + 3.0f, 0xFFFFFF);
			font.drawString(matrixStack, "Neut: " + fission.getNeutrons(), 85.0f, 23.0f + (font.FONT_HEIGHT + 3.0f) * 2, 0xFFFFFF);
		}
	}
}
