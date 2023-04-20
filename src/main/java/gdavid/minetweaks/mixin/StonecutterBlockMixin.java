package gdavid.minetweaks.mixin;

import gdavid.minetweaks.Mod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StonecutterBlock.class)
public class StonecutterBlockMixin extends Block {
	
	private static final RegistryKey<DamageType> sawDamage = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Mod.id, "saw"));
	
	public StonecutterBlockMixin(Settings settings) {
		super(settings);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		entity.damage(new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(sawDamage)), 2);
	}
	
}
