package gdavid.minetweaks.mixin;

import gdavid.minetweaks.Mod;
import net.minecraft.block.*;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {
	
	@Inject(method = "onBlockHit", at = @At("HEAD"), cancellable = true)
	private void onBlockHit(BlockHitResult hit, CallbackInfo callback) {
		var self = (PersistentProjectileEntity) (Object) this;
		if (self.asItemStack().isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(Mod.id, "projectile_toggles_lever")))) {
			var nextTo = hit.getBlockPos().offset(hit.getSide());
			var state = self.world.getBlockState(nextTo);
			if (state.getBlock() instanceof LeverBlock lever) lever.togglePower(state, self.world, nextTo);
		}
		if (self.asItemStack().isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(Mod.id, "projectile_breaks_glass")))) {
			var state = self.world.getBlockState(hit.getBlockPos());
			boolean glass = state.getBlock() instanceof AbstractGlassBlock;
			boolean glassPane = state.isOf(Blocks.GLASS_PANE) || state.getBlock() instanceof StainedGlassPaneBlock;
			if (glass || glassPane) {
				float impulse = (float) -self.getVelocity().dotProduct(new Vec3d(hit.getSide().getUnitVector())) * (glassPane ? 1.8f : 1);
				if (impulse >= 1.3) {
					self.world.breakBlock(hit.getBlockPos(), true, self);
					self.setVelocity(self.getVelocity().multiply(glassPane ? 0.5 : 0.25));
					self.setCritical(false);
					callback.cancel();
				}
			}
		}
	}
	
}
