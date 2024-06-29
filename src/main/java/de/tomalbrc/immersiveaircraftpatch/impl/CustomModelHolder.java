package de.tomalbrc.immersiveaircraftpatch.impl;

import com.mojang.math.Transformation;
import de.tomalbrc.bil.core.holder.wrapper.DisplayWrapper;
import de.tomalbrc.bil.core.model.Model;
import de.tomalbrc.bil.core.model.Pose;
import immersive_aircraft.entity.EngineVehicle;
import immersive_aircraft.entity.VehicleEntity;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CustomModelHolder extends CustomLivingEntityHolder {

    public CustomModelHolder(VehicleEntity parent, Model model) {
        super(parent, model);
    }

    @Override
    protected void applyPose(Pose pose, DisplayWrapper display) {
        Matrix4f matrix4f;
        var tr = new Transformation(pose.readOnlyTranslation().sub(0.f,0.6f,0.5f, new Vector3f()), pose.leftRotation(), pose.readOnlyScale().get(new Vector3f()), pose.rightRotation());
        matrix4f = tr.getMatrix();

        matrix4f
                .rotateLocalZ(-((VehicleEntity)parent).roll * Mth.DEG_TO_RAD)
                .rotateLocalX(-parent.xRotO * Mth.DEG_TO_RAD)
                .rotateLocalY(-(parent.yRotO+180) * Mth.DEG_TO_RAD)
        ;

       // matrix4f.translate(pose.readOnlyTranslation().sub(0.f,0.5f,0.1f, new Vector3f()));

        display.element().setTransformation(matrix4f);
        display.startInterpolationIfDirty();
    }


    @Override
    protected void onTick() {
        super.onTick();

        if (this.parent instanceof EngineVehicle engineVehicle) {
            if (engineVehicle.getEngineTarget() <= 0.1f) {
                //getAnimator().pauseAnimation("animation");
            } else {
                this.getAnimator().playAnimation("a2");
            }
        }
    }
}
