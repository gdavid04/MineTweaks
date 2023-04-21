package gdavid.minetweaks.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RedstoneOreBlock.class)
public class RedstoneOreBlockMixin extends Block {
	
	@Shadow
	private static void light(BlockState state, World world, BlockPos pos) {}
	
	private RedstoneOreBlockMixin(Settings settings) {
		super(settings);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		updatePower(state, world, pos);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
		updatePower(state, world, pos);
	}
	
	private static void updatePower(BlockState state, World world, BlockPos pos) {
		if (world.getReceivedRedstonePower(pos) > 8) light(state, world, pos);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean emitsRedstonePower(BlockState state) {
		return true;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction side) {
		return state.get(RedstoneOreBlock.LIT) ? 8 : 0;
	}
	
}
