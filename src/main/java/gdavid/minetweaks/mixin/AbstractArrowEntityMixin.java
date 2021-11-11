package gdavid.minetweaks.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeverBlock;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AbstractArrowEntity.class)
public class AbstractArrowEntityMixin {
	
	@Inject(method = "func_230299_a_", at = @At("HEAD"))
	private void onBlockHit(BlockRayTraceResult hit, CallbackInfo callback) {
		AbstractArrowEntity self = ((AbstractArrowEntity) (Object) this);
		BlockPos nextTo = hit.getPos().offset(hit.getFace());
		BlockState state = self.world.getBlockState(nextTo);
		if (state.getBlock() == Blocks.LEVER) {
			((LeverBlock) Blocks.LEVER).setPowered(state, self.world, nextTo);
		}
	}
	
}
