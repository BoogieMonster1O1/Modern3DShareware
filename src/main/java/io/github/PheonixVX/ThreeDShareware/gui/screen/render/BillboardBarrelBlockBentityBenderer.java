package io.github.PheonixVX.ThreeDShareware.gui.screen.render;

import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

public class BillboardBarrelBlockBentityBenderer extends BlockEntityRenderer<BarrelBlockEntity> {
	public static final Identifier BARREL_TEX = new Identifier("minecraft:textures/block/barrel_side.png");
	public static final Vector3f[] VECTORS = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
	
	public BillboardBarrelBlockBentityBenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(BarrelBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		Camera camera = this.dispatcher.camera;
		Vec3d vec3d = camera.getPos();
		BlockPos pos = entity.getPos();
		float x = (float) (pos.getX() - vec3d.getX());
		float y = (float) (pos.getY() - vec3d.getY());
		float z = (float) (pos.getZ() - vec3d.getZ());
		Quaternion rotation = camera.getRotation();

		Vector3f vector3f = new Vector3f(-1.0F, -1.0F, 0.0F);
		vector3f.rotate(rotation);

		for(int k = 0; k < 4; ++k) {
			Vector3f vector3f2 = VECTORS[k];
			vector3f2.rotate(rotation);
			vector3f2.add(x, y, z);
		}

		VertexConsumer barrelVc = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(BARREL_TEX));
		barrelVc.vertex(VECTORS[0].getX(), VECTORS[0].getY(), VECTORS[0].getZ()).color(255, 255, 255, 255).texture(1, 1).overlay(overlay).light(light).normal(0, 0, 0).next();
		barrelVc.vertex(VECTORS[1].getX(), VECTORS[1].getY(), VECTORS[1].getZ()).color(255, 255, 255, 255).texture(1, 0).overlay(overlay).light(light).normal(0, 0, 0).next();
		barrelVc.vertex(VECTORS[2].getX(), VECTORS[2].getY(), VECTORS[2].getZ()).color(255, 255, 255, 255).texture(0, 0).overlay(overlay).light(light).normal(0, 0, 0).next();
		barrelVc.vertex(VECTORS[3].getX(), VECTORS[3].getY(), VECTORS[3].getZ()).color(255, 255, 255, 255).texture(0, 1).overlay(overlay).light(light).normal(0, 0, 0).next();

		{
			MatrixStack extra = new MatrixStack();
			extra.push();
			extra.translate(0, 0.5, 0);
			VertexConsumer fireVc = vertexConsumers.getBuffer(TexturedRenderLayers.getEntityCutout());
			Sprite sprite = ModelLoader.FIRE_0.getSprite();
			float minU = sprite.getMinU();
			float minV = sprite.getMinV();
			float maxU = sprite.getMaxU();
			float maxV = sprite.getMaxV();
			MatrixStack.Entry entry = extra.peek();
			drawFireVertex(entry, fireVc, VECTORS[0].getX(), VECTORS[0].getY(), VECTORS[0].getZ(), maxU, maxV);
			drawFireVertex(entry, fireVc, VECTORS[1].getX(), VECTORS[1].getY(), VECTORS[1].getZ(), minU, maxV);
			drawFireVertex(entry, fireVc, VECTORS[2].getX(), VECTORS[2].getY(), VECTORS[2].getZ(), minU, minV);
			drawFireVertex(entry, fireVc, VECTORS[3].getX(), VECTORS[3].getY(), VECTORS[3].getZ(), maxU, minV);
			extra.pop();
		}
	}

	private static void drawFireVertex(MatrixStack.Entry entry, VertexConsumer vertices, float x, float y, float z, float u, float v) {
		vertices.vertex(entry.getModel(), x, y, z).color(255, 255, 255, 255).texture(u, v).overlay(0, 10).light(240).normal(entry.getNormal(), 0.0F, 1.0F, 0.0F).next();
	}
}
