package de.tomalbrc.immersiveaircraftpatch;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import org.mariuszgromada.math.mxparser.License;

public class ImmersiveAircraft_patch implements ModInitializer {
    @Override
    public void onInitialize() {
        PolymerResourcePackUtils.addModAssets("immversive_aircraft");
        PolymerResourcePackUtils.markAsRequired();
    }
}
