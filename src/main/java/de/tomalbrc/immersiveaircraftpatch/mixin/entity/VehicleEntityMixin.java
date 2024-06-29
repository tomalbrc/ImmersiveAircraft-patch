package de.tomalbrc.immersiveaircraftpatch.mixin.entity;


import de.tomalbrc.immersiveaircraftpatch.impl.IVehicleEntity;
import immersive_aircraft.entity.VehicleEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VehicleEntity.class)
public abstract class VehicleEntityMixin implements IVehicleEntity {
    @Inject(remap = false, method = "tickPilot", at = @At("HEAD"), cancellable = true)
    private void iap$onTick(CallbackInfo ci) {
        control();
        ci.cancel();
    }

    @Redirect(remap = false, method = "onAboveBubbleCol", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"))
    private void iap$onAboveBubbleColPlaySound(Level instance, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, boolean bl) {
        instance.playSound(null, d,e,f,soundEvent, soundSource, g, h);
    }

    @Redirect(remap = false, method = "tickPilot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"))
    private void iap$onTickPlaySound(Level instance, double d, double e, double f, SoundEvent soundEvent, SoundSource soundSource, float g, float h, boolean bl) {
        instance.playSound(null, d,e,f,soundEvent, soundSource, g, h);
    }

    @Redirect(remap = false, method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;isClientSide:Z", args = ""))
    private boolean iap$onTickControl(Level instance) {
        return true;
    }

    @Redirect(remap = false, method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;isClientSide:Z", args = ""))
    private boolean iap$onMove(Level instance) {
        return true;
    }

    @Redirect(remap = false, method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isClientSide()Z", args = ""))
    private boolean iap$onTickControl2(Level instance) {
        return true;
    }

    @Unique
    private void control() {
        Entity pilot = getPassengers().get(0);
        if (pilot instanceof ServerPlayer player) {
            boolean left = player.xxa > 0;
            boolean right = player.xxa < 0;
            boolean up = player.zza > 0;
            boolean down = player.zza < 0;

            setInputs(iap$getMovementMultiplier(
                            left,
                            right
                    ), iap$getMovementMultiplier(
                            player.jumping,
                            player.isShiftKeyDown()
                    ),
                    iap$getMovementMultiplier(
                            useAirplaneControls() ? up : up,
                            useAirplaneControls() ? down : down
                    )
            );

            if (player.jumping && player.isShiftKeyDown()) {
                player.sendSystemMessage(Component.literal("Dismounting"));
                player.stopRiding();
            }
        } else {
            setInputs(0, 0, 0);
        }
    }

    @Inject(remap = false, method = "isWithinParticleRange", at = @At("HEAD"), cancellable = true)
    public void iap$onIsWithinParticleRange(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Unique
    private static float iap$getMovementMultiplier(boolean positive, boolean negative) {
        if (positive == negative) {
            return 0.0f;
        }
        return positive ? 1.0f : -1.0f;
    }

}
