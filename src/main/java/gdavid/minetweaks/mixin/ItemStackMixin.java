package gdavid.minetweaks.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

	private static final EntityAttributeModifier stickReach = new EntityAttributeModifier("Stick Reach", 1, EntityAttributeModifier.Operation.ADDITION);
	
	@Inject(method = "getAttributeModifiers", at = @At("RETURN"), cancellable = true)
	private void getAttributeModifiers(EquipmentSlot slot, CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> callback) {
		if (slot.getType() == EquipmentSlot.Type.HAND && ((ItemStack) (Object) this).getItem() == Items.STICK) {
			ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(callback.getReturnValue());
			builder.put(ReachEntityAttributes.REACH, stickReach);
			builder.put(ReachEntityAttributes.ATTACK_RANGE, stickReach);
			callback.setReturnValue(builder.build());
		}
	}

}
