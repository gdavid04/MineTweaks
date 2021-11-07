package gdavid.minetweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;

import gdavid.minetweaks.MineTweaks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(value = StonecutterBlock.class)
public class StonecutterBlockMixin extends Block {

	private static final DamageSource saw = new DamageSource(MineTweaks.modId + ".saw");
	
	private StonecutterBlockMixin(Properties properties) {
		super(properties);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		entity.attackEntityFrom(saw, 2);
	}
	
}
