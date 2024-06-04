package de.tomalbrc.immersiveaircraftpatch.mixin;


import de.tomalbrc.immersiveaircraftpatch.impl.IVehicleEntity;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import immersive_aircraft.entity.AirplaneEntity;
import immersive_aircraft.entity.VehicleEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VehicleEntity.class)
public abstract class VehicleEntityMixin implements IVehicleEntity {
    @Shadow public abstract @Nullable LivingEntity getControllingPassenger();

    @Inject(remap = false, method = "tickPilot", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo ci) {
        control();
        ci.cancel();
    }

    @Redirect(remap = false, method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;isClientSide:Z", args = ""))
    private boolean onTickControl(Level instance) {
        return true;
    }

    @Redirect(remap = false, method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/Level;isClientSide:Z", args = ""))
    private boolean onMove(Level instance) {
        return true;
    }

    @Redirect(remap = false, method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isClientSide()Z", args = ""))
    private boolean onTickControl2(Level instance) {
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
        } else {
            setInputs(0, 0, 0);
        }
    }

    @Unique
    private static float iap$getMovementMultiplier(boolean positive, boolean negative) {
        if (positive == negative) {
            return 0.0f;
        }
        return positive ? 1.0f : -1.0f;
    }

}
