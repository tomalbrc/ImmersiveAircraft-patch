package de.tomalbrc.immersiveaircraftpatch.mixin.entity.model;

import de.tomalbrc.bil.api.AnimatedEntity;
import de.tomalbrc.bil.core.holder.entity.EntityHolder;
import de.tomalbrc.bil.core.model.Model;
import de.tomalbrc.bil.file.loader.BbModelLoader;
import de.tomalbrc.immersiveaircraftpatch.impl.CustomModelHolder;
import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import immersive_aircraft.entity.AirshipEntity;
import immersive_aircraft.entity.GyrodyneEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GyrodyneEntity.class)
public class GyrodyneEntityMixin implements AnimatedEntity {
    @Unique
    private static final ResourceLocation ID = new ResourceLocation("iap:gyrodyne");
    @Unique
    private static final Model MODEL = BbModelLoader.load(ID);

    @Unique
    private CustomModelHolder holder;

    @Inject(remap = false, method = "<init>", at = @At("TAIL"))
    void iap$onInit(EntityType entityType, Level world, CallbackInfo ci) {
        GyrodyneEntity _this = (GyrodyneEntity)(Object)this;
        this.holder = new CustomModelHolder(_this, MODEL);
        EntityAttachment.ofTicking(this.holder, _this);
    }

    @Override
    public EntityHolder getHolder() {
        return this.holder;
    }
}