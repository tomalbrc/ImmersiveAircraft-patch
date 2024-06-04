package de.tomalbrc.immersiveaircraftpatch.impl;

import de.tomalbrc.bil.core.holder.entity.simple.InteractableEntityHolder;
import de.tomalbrc.bil.core.holder.wrapper.DisplayWrapper;
import de.tomalbrc.bil.core.model.Model;
import de.tomalbrc.bil.core.model.Pose;
import immersive_aircraft.entity.EngineVehicle;
import immersive_aircraft.entity.VehicleEntity;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CustomModelHolder extends InteractableEntityHolder {
    private float xRot = 0;
    private float yRot = 0;
    private float localYOffset;

    public CustomModelHolder(VehicleEntity parent, Model model) {
        super(parent, model);

        getAnimator().playAnimation("animation");
    }

    @Override
    protected void applyPose(Pose pose, DisplayWrapper display) {
        Vector3f tr = pose.translation();
        if (this.scale != 1F) {
            display.element().setScale(pose.scale().mul(this.scale, new Vector3f()));
        } else {
            display.element().setScale(pose.readOnlyScale());
        }

        var rot = pose.leftRotation()
                .rotateLocalY((-parent.yRotO+180) * Mth.DEG_TO_RAD);
        var rot2 = pose.rightRotation()
                .rotateX(parent.xRotO * Mth.DEG_TO_RAD);

        tr.rotateX(-parent.xRotO * Mth.DEG_TO_RAD);
        tr.rotateY((-parent.yRotO+180) * Mth.DEG_TO_RAD);

        display.element().setTranslation(tr);
        display.element().setRightRotation(rot2);
        display.element().setLeftRotation(rot);
        display.startInterpolationIfDirty();
    }

    @Override
    protected void updateElement(DisplayWrapper<?> display) {
        //display.element().setYaw(-parent.yRotO + 180);
        //display.element().setPitch(parent.xRotO);
        this.applyPose(display.getDefaultPose(), display);
    }

    @Override
    protected void onTick() {
        if (this.parent instanceof EngineVehicle engineVehicle) {
            if (engineVehicle.getEngineTarget() <= 0.1f) {
                //getAnimator().pauseAnimation("animation");
            } else {
            }
        }
    }
}
