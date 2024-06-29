package de.tomalbrc.immersiveaircraftpatch.mixin;

import de.tomalbrc.immersiveaircraftpatch.ImmersiveAircraft_patch;
import immersive_aircraft.entity.InventoryVehicleEntity;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerGamePacketListenerImpl.class, priority = 20000)
public class ServerGamePacketListenerImplMixin {
    @Shadow public ServerPlayer player;

    @Inject(method = "handlePlayerAction", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;", ordinal = 0), cancellable = true)
    private void iap$onHandlePlayerAction(ServerboundPlayerActionPacket serverboundPlayerActionPacket, CallbackInfo ci) {
        if (player.isPassenger() && player.getVehicle() instanceof InventoryVehicleEntity vehicleEntity) {
            ImmersiveAircraft_patch.openGui(player, vehicleEntity);
            ci.cancel();
        }
    }
}
