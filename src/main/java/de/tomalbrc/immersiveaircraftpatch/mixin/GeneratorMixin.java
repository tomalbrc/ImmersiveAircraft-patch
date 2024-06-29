package de.tomalbrc.immersiveaircraftpatch.mixin;

import io.github.theepicblock.polymc.api.PolyRegistry;
import io.github.theepicblock.polymc.impl.generator.EntityPolyGenerator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPolyGenerator.class)
public class GeneratorMixin {
    @Inject(remap = false, method = "addEntityToBuilder", at = @At("HEAD"), cancellable = true)
    private static <T extends Entity> void iap$customIsNamespaceVanilla(EntityType<T> entityType, PolyRegistry builder, CallbackInfo ci) {
        if (BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getNamespace().startsWith("immersive_aircraft")) {
            ci.cancel();
        }
    }
}
