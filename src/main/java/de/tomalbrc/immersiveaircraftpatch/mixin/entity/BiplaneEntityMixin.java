package de.tomalbrc.immersiveaircraftpatch.mixin.entity;

import immersive_aircraft.entity.BiplaneEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BiplaneEntity.class)
public abstract class BiplaneEntityMixin {

    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;isClientSide:Z", ordinal = 0))
    public boolean iap$onTickChangeIsClientSide(Level instance) {
        return true;
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    public void iap$onTickChangeIsClientSide(Level instance, ParticleOptions particleOptions, double d, double e, double f, double g, double h, double i) {
        if (instance instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particleOptions, d, e, f, 0, g, h, i, 1);
        }
    }
}