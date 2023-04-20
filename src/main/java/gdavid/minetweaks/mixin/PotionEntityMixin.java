package gdavid.minetweaks.mixin;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionEntity.class)
public class PotionEntityMixin {
	
	@Inject(method = "applySplashPotion", at = @At("HEAD"))
	private void splash(List<StatusEffectInstance> effects, Entity hit, CallbackInfo callback) {
		PotionEntity self = (PotionEntity) (Object) this;
		Random rand = self.world.getRandom();
		if (effects.stream().anyMatch(effect -> {
			StatusEffect e = effect.getEffectType();
			return e == StatusEffects.INSTANT_DAMAGE || e == StatusEffects.POISON || e == StatusEffects.WITHER;
		})) {
			BlockState[] grassReplacements = new BlockState[] { Blocks.DIRT.getDefaultState(),
					Blocks.COARSE_DIRT.getDefaultState(), Blocks.DIRT_PATH.getDefaultState() };
			for (int i = 0; i < 128; i++) {
				BlockPos pos = self.getBlockPos();
				for (int j = 0; j < i / 16; j++) {
					pos = pos.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * (rand.nextInt(3) / 2),
							rand.nextInt(3) - 1);
					BlockState state = self.world.getBlockState(pos);
					if (state.isOpaqueFullCube(self.world, pos)) {
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
