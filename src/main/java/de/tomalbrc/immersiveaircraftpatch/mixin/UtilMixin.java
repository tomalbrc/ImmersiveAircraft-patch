package de.tomalbrc.immersiveaircraftpatch.mixin;

import io.github.theepicblock.polymc.impl.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Util.class)
public class UtilMixin {
    @Inject(remap = false, method = "isNamespaceVanilla", at = @At("HEAD"), cancellable = true)
    private static void iap$customIsNamespaceVanilla(String v, CallbackInfoReturnable<Boolean> cir) {
        if (v.startsWith("immersive_aircraft")) {
            cir.setReturnValue(true);
        }
    }
}
