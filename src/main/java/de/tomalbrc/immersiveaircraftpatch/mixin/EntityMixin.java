package de.tomalbrc.immersiveaircraftpatch.mixin;

import eu.pb4.polymer.core.api.entity.PolymerEntity;
import immersive_aircraft.entity.VehicleEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements PolymerEntity {
    @Shadow @Nullable public abstract LivingEntity getControllingPassenger();

    public EntityType<?> getPolymerEntityType(ServerPlayer player) {
        return EntityType.PHANTOM;
    }

    @Inject(remap = false, method = "isControlledByLocalInstance", at = @At("HEAD"), cancellable = true)
    public void iap$onIsControlledByLocalInstance(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity var2 = this.getControllingPassenger();
        if (var2 instanceof Player player && (Object)this instanceof VehicleEntity) {
            cir.setReturnValue(true);
        }
    }
}
