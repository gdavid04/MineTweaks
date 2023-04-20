package gdavid.minetweaks.mixin;

import gdavid.minetweaks.Mod;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeverBlock;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {
	
	@Shadow
	protected abstract ItemStack asItemStack();
	
	@Inject(method = "onBlockHit", at = @At("HEAD"))
	private void onBlockHit(BlockHitResult hit, CallbackInfo callback) {
		PersistentProjectileEntity self = (PersistentProjectileEntity) (Object) this;
		if (asItemStack().isIn(TagKey.of(RegistryKeys.ITEM, new Identifier(Mod.id, "projectile_toggles_lever")))) {
			BlockPos nextTo = hit.getBlockPos().offset(hit.getSide());
			BlockState state = self.world.getBlockState(nextTo);
			if (state.getBlock() instanceof LeverBlock lever) lever.togglePower(state, self.world, nextTo);
		}
	}
	
}
