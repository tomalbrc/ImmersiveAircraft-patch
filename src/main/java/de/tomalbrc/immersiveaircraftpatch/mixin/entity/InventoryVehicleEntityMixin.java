package de.tomalbrc.immersiveaircraftpatch.mixin.entity;


import com.llamalad7.mixinextras.sugar.Local;
import de.tomalbrc.immersiveaircraftpatch.ImmersiveAircraft_patch;
import de.tomalbrc.immersiveaircraftpatch.impl.IVehicleEntity;
import eu.pb4.sgui.api.gui.SimpleGui;
import immersive_aircraft.entity.InventoryVehicleEntity;
import immersive_aircraft.entity.inventory.SparseSimpleInventory;
import immersive_aircraft.entity.inventory.slots.SlotDescription;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryVehicleEntity.class)
public abstract class InventoryVehicleEntityMixin implements IVehicleEntity {

    @Inject(remap = false, method = "openInventory", at = @At(value = "INVOKE", target = "Limmersive_aircraft/cobalt/network/NetworkHandler;sendToPlayer(Limmersive_aircraft/cobalt/network/Message;Lnet/minecraft/server/level/ServerPlayer;)V"), cancellable = true)
    public void iap$onOpenInventory(ServerPlayer player, CallbackInfo ci, @Local AbstractContainerMenu screenHandler) {
        var _this = (InventoryVehicleEntity)((Object)this);

        ImmersiveAircraft_patch.openGui(player, _this);

        ci.cancel();
    }
}
