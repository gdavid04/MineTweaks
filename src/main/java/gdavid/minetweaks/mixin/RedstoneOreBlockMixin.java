package gdavid.minetweaks.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = RedstoneOreBlock.class)
public class RedstoneOreBlockMixin extends Block {
	
	@Shadow
	private static void activate(BlockState state, World world, BlockPos pos) {
	}
	
	private RedstoneOreBlockMixin(Properties properties) {
		super(properties);
	}
	
	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos,
			boolean isMoving) {
		if (world.getRedstonePowerFromNeighbors(pos) > 8) {
			activate(state, world, pos);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (world.getRedstonePowerFromNeighbors(pos) > 8) {
			activate(state, world, pos);
		}
	}
	
	@Override
	public boolean canProvidePower(BlockState state) {
		return true;
	}
	
	@Override
	public int getWeakPower(BlockState state, IBlockReader blockAccess, BlockPos pos, Direction side) {
		return state.get(RedstoneOreBlock.LIT) ? 8 : 0;
	}
	
}
