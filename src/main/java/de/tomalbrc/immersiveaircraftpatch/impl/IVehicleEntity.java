package de.tomalbrc.immersiveaircraftpatch.impl;

import net.minecraft.world.entity.Entity;

import java.util.List;

public interface IVehicleEntity {
    void setInputs(float x, float y, float z);

    boolean useAirplaneControls();

    void updateController();

    List<Entity> getPassengers() ;

}
