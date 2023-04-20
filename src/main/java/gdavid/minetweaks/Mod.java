package gdavid.minetweaks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class Mod implements ModInitializer {
	
	public static final String id = "minetweaks";
	
	@Override
	public void onInitialize() {
		DynamicRegistrySetupCallback.EVENT.register(reg ->
			reg.getOptional(RegistryKeys.DAMAGE_TYPE).ifPresent(damageTypes ->
				Registry.register(damageTypes, new Identifier(id, "saw"), new DamageType(id + ".saw", 0))
			)
		);
	}
	
}
