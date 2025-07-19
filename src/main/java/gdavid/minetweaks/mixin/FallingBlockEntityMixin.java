package gdavid.minetweaks.mixin;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
	
	@Shadow private boolean hurtEntities;
	
	public FallingBlockEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	
	@Inject(method = "handleFallDamage", at = @At("HEAD"))
	private void blockFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> ci) {
		if (!hurtEntities || fallDistance < 2.5f) return;
		var below = getBlockPos().down();
		var block = world.getBlockState(below);
		if (block.getBlock() instanceof AbstractGlassBlock || block.isOf(Blocks.GLASS_PANE) || block.getBlock() instanceof StainedGlassPaneBlock) {
			world.breakBlock(below, true, this);
		}
	}
	
}
