package io.github.PheonixVX.ThreeDShareware.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.PheonixVX.ThreeDShareware.registry.GenericRegistry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("ConstantConditions")
public class CustomSplashScreen extends Screen {
	private static final Identifier MOJANG_LOGO = new Identifier("textures/gui/mojang_logo.png");
	private static final Identifier MOJANG_TEXT = new Identifier("textures/gui/mojang_text.png");
	private class_4295 field_19237;
	private long field_19238;
	private float field_19239;
	private long field_19240;
	private SoundInstance sound;

	public CustomSplashScreen() {
		super(new LiteralText("Amazing Logo"));
		this.field_19237 = class_4295.field_19243;
		this.field_19238 = -1L;
		this.field_19239 = 15.0F;
	}

	@Override
	public void render (MatrixStack matrices, int mouseX, int mouseY, float delta) {
		RenderSystem.enableTexture();
		RenderSystem.enableBlend();
		RenderSystem.disableAlphaTest();
		//GlStateManager.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
		RenderSystem.defaultBlendFunc();
		RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.clear(0x4100, MinecraftClient.IS_SYSTEM_MAC);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		long time = Util.getMeasuringTimeMs();
		long var6 = time - this.field_19240;
		this.field_19240 = time;
		if (this.field_19237 == class_4295.field_19243) {
			this.field_19237 = class_4295.field_19244;
			this.field_19239 = 10.0F;
			this.field_19238 = -1L;
		} else {
			if (this.field_19237 == class_4295.field_19244) {
				this.field_19239 -= (float)var6 / 1000.0F;
				if (this.field_19239 <= 0.0F) {
					this.field_19237 = class_4295.field_19245;
					this.field_19238 = time;
					this.sound = new PositionedSoundInstance(GenericRegistry.AWESOME_INTRO.getId(), SoundCategory.MASTER, 0.25F, 1.0F, false, 0, SoundInstance.AttenuationType.NONE, 0.0F, 0.0F, 0.0F, true) {
						public boolean method_20286() {
							return false;
						}
					};
					this.client.getSoundManager().play(this.sound);
				}
			} else if (!this.client.getSoundManager().isPlaying(this.sound)) {
				this.client.openScreen(new TitleScreen());
				this.field_19237 = class_4295.field_19243;
			}

			float var8 = 20.0F * this.field_19239;
			float var9 = 100.0F * MathHelper.sin(this.field_19239);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder vertexConsumer = tessellator.getBuffer();
			vertexConsumer.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
			if (this.field_19238 != -1L) {
				this.client.getTextureManager().bindTexture(MOJANG_TEXT);
				int var12 = MathHelper.clamp((int)(time - this.field_19238), 0, 255);
				this.method_20278(vertexConsumer, this.width / 2, this.height - this.height / 8, 208, 38, var12);
			}

			tessellator.draw();
			matrices.push();
			matrices.translate((float)this.width / 2.0F, (float)this.height / 2.0F, this.getZOffset());
			matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(var8));
			matrices.translate(var9, 0.0F, 0.0F);
			float var13 = 1.0F / (2.0F * this.field_19239 + 1.0F);
			matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(1.5F * this.field_19239));
			matrices.scale(var13, var13, 1.0F);
			this.client.getTextureManager().bindTexture(MOJANG_LOGO);
			vertexConsumer.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
			this.method_20278(vertexConsumer, 0, 0, 78, 76, 255);
			tessellator.draw();
			matrices.push();
		}
	}

	private void method_20278(BufferBuilder vertexConsumer, int x1, int y1, int width, int height, int alpha) {
		int x2 = width / 2;
		int y2 = height / 2;
		vertexConsumer.vertex(x1 - x2, y1 + y2, this.getZOffset()).texture(0.0F, 1.0F).color(255, 255, 255, alpha).next();
		vertexConsumer.vertex(x1 + x2, y1 + y2, this.getZOffset()).texture(1.0F, 1.0F).color(255, 255, 255, alpha).next();
		vertexConsumer.vertex(x1 + x2, y1 - y2, this.getZOffset()).texture(1.0F, 0.0F).color(255, 255, 255, alpha).next();
		vertexConsumer.vertex(x1 - x2, y1 - y2, this.getZOffset()).texture(0.0F, 0.0F).color(255, 255, 255, alpha).next();
	}

	@Override
	public void onClose () {
		this.client.openScreen(new TitleScreen());
	}

	enum class_4295 {
		field_19243,
		field_19244,
		field_19245
	}
}
