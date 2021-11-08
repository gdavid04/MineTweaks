package gdavid.minetweaks.mixin;

import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;

@Mixin(value = PotionEntity.class)
public class PotionEntityMixin {
	
	@Inject(method = "func_213888_a", at = @At("HEAD"))
	private void splash(List<EffectInstance> effects, Entity hit, CallbackInfo callback) {
		PotionEntity self = (PotionEntity) (Object) this;
		Random rand = self.world.getRandom();
		if (effects.stream().filter(effect -> {
			Effect e = effect.getPotion();
			return e == Effects.INSTANT_DAMAGE || e == Effects.POISON || e == Effects.WITHER;
		}).findAny().isPresent()) {
			BlockState[] grassReplacements = new BlockState[] {
				Blocks.DIRT.getDefaultState(),
				Blocks.COARSE_DIRT.getDefaultState(),
				Blocks.GRASS_PATH.getDefaultState()
			};
			for (int i = 0; i < 128; i++) {
				BlockPos pos = self.getPosition();
				for (int j = 0; j < i / 16; j++) {
					pos = pos.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * (rand.nextInt(3) / 2), rand.nextInt(3) - 1);
					BlockState state = self.world.getBlockState(pos);
					if (state.hasOpaqueCollisionShape(self.world, pos)) {
						if (state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock() == Blocks.MYCELIUM) {
							BlockState newState = grassReplacements[rand.nextInt(grassReplacements.length)];
							self.world.setBlockState(pos, newState);
						} else if (state.getBlock() == Blocks.DIRT) {
							if (rand.nextBoolean()) self.world.setBlockState(pos, Blocks.COARSE_DIRT.getDefaultState());
						}
						break;
					}
				}
			}
		}
	}
	
}
