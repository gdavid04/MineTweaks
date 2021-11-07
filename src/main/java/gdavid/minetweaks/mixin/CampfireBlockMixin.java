package gdavid.minetweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(value = CampfireBlock.class)
public class CampfireBlockMixin {
	
	@Inject(method = "onEntityCollision", at = @At("HEAD"))
	private void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo callback) {
		if (!entity.isImmuneToFire() && state.get(CampfireBlock.LIT) && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
			entity.setFire(8);
		}
	}
	
}
