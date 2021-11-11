package gdavid.minetweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.ForgeMod;

@Mixin(value = ItemStack.class)
public class ItemStackMixin {

	private static final AttributeModifier stickReach = new AttributeModifier("Stick Reach", 1, Operation.ADDITION);
	
	@Inject(method = "getAttributeModifiers", at = @At("RETURN"), cancellable = true)
	private void getAttributeModifiers(EquipmentSlotType slot,
			CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> callback) {
		if (slot.getSlotType() == EquipmentSlotType.Group.HAND && ((ItemStack) (Object) this).getItem() == Items.STICK) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(callback.getReturnValue());
			builder.put(ForgeMod.REACH_DISTANCE.get(), stickReach);
			callback.setReturnValue(builder.build());
		}
	}
	
}
