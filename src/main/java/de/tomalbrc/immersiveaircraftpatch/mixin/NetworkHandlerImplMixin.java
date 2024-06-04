package de.tomalbrc.immersiveaircraftpatch.mixin;

import immersive_aircraft.cobalt.network.Message;
import immersive_aircraft.fabric.cobalt.network.NetworkHandlerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkHandlerImpl.class)
public class NetworkHandlerImplMixin {
    @Inject(remap = false, method = "sendToServer", at = @At("HEAD"), cancellable = true)
    void aip$onSendToServer(Message msg, CallbackInfo ci) {
        ci.cancel();
    }
}
