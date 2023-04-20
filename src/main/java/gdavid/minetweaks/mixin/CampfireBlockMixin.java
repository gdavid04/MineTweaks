package gdavid.minetweaks.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
	
	@Inject(method = "onEntityCollision", at = @At("HEAD"))
	private void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo callback) {
		if (!entity.isFireImmune() && state.get(CampfireBlock.LIT) && entity instanceof LivingEntity
				&& !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
			entity.setOnFireFor(8);
		}
	}
	
}
