package gdavid.minetweaks.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity.PickupPermission;
import net.minecraft.item.ItemStack;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {
	
	@Inject(method = "onEntityCollided", at = @At("HEAD"))
	private static void onEntityCollided(World world, BlockPos pos, BlockState state, Entity entity, HopperBlockEntity hopper, CallbackInfo callback) {
		if (world.isClient) return;
		if (entity instanceof PersistentProjectileEntity projectile &&
				projectile.inGround && projectile.pickupType == PickupPermission.ALLOWED) {
			VoxelShape inputShape = hopper.getInputAreaShape().offset(pos.getX(), pos.getY(), pos.getZ());
			if (VoxelShapes.matchesAnywhere(VoxelShapes.cuboid(projectile.getBoundingBox()), inputShape, BooleanBiFunction.AND)) {
				ItemStack item = projectile.asItemStack();
				if (EnchantmentHelper.getLoyalty(item) == 0) {
					HopperBlockEntity.transfer(null, hopper, item, null);
					projectile.discard();
				}
			}
		}
	}
	
}
