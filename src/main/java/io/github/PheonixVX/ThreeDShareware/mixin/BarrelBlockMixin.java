package io.github.PheonixVX.ThreeDShareware.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

@Mixin(BarrelBlock.class)
public class BarrelBlockMixin extends Block {
	public BarrelBlockMixin(Settings settings) {
		super(settings);
	}

	@Override
	public void onEntityCollision (BlockState state, World world, BlockPos pos, Entity entity) {
		if (!world.isClient && entity instanceof ProjectileEntity) {
			ProjectileEntity projectileEntity = (ProjectileEntity)entity;
			Entity theEntity = projectileEntity.getOwner();
			world.createExplosion(theEntity, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, 5.0f, Explosion.DestructionType.DESTROY);
		}
	}

	/**
	 * @author chess dude
	 * @reason so that mixin doesnt hate me
	 */
	@Overwrite
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
}
