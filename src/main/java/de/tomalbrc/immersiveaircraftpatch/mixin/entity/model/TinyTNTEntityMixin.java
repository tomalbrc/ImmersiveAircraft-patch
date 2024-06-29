package de.tomalbrc.immersiveaircraftpatch.mixin.entity.model;

import de.tomalbrc.bil.api.AnimatedEntity;
import de.tomalbrc.bil.core.holder.entity.EntityHolder;
import de.tomalbrc.bil.core.model.Model;
import de.tomalbrc.bil.file.loader.BbModelLoader;
import de.tomalbrc.immersiveaircraftpatch.impl.CustomModelHolder;
import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import immersive_aircraft.entity.AirshipEntity;
import immersive_aircraft.entity.bullet.TinyTNT;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TinyTNT.class)
public class TinyTNTEntityMixin implements PolymerEntity {
    @Override
    public EntityType<?> getPolymerEntityType(ServerPlayer player) {
        return EntityType.TNT;
    }
}