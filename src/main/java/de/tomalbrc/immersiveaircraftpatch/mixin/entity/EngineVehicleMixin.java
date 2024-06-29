package de.tomalbrc.immersiveaircraftpatch.mixin.entity;


import de.tomalbrc.immersiveaircraftpatch.impl.IVehicleEntity;
import immersive_aircraft.entity.EngineVehicle;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EngineVehicle.class)
public abstract class EngineVehicleMixin implements IVehicleEntity {
    @Redirect(remap = false, method = "setEngineTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"))
    private void iap$onSetEngineTargetPlaySound(Level instance, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, boolean bl) {
        instance.playSound(null, d,e,f,soundEvent, soundSource, g, h);
    }

    @Redirect(remap = false, method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"))
    private void iap$onTickPlaySound(Level instance, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, boolean bl) {
        instance.playSound(null, d,e,f,soundEvent, soundSource, g, h);
    }

    @Redirect(remap = false, method = "setEngineTarget", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;isClientSide:Z"))
    private boolean iap$onSetEngineTargetChangeClientSide(Level instance) {
        return true;
    }

    @Redirect(remap = false, method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;isClientSide:Z", ordinal = 0))
    private boolean iap$onTickChangeClientSide(Level instance) {
        return true;
    }
}
