package de.tomalbrc.immersiveaircraftpatch.impl;

import de.tomalbrc.bil.api.AnimatedEntity;
import de.tomalbrc.bil.core.model.Model;
import de.tomalbrc.bil.util.Utils;
import eu.pb4.polymer.virtualentity.api.elements.InteractionElement;
import immersive_aircraft.entity.VehicleEntity;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EntityDimensions;

import java.util.function.Consumer;

public class CustomLivingEntityHolderWithRideOffset<T extends VehicleEntity & AnimatedEntity> extends CustomLivingEntityHolder<T> {
    private static final EntityDimensions ZERO = EntityDimensions.fixed(0, 0);
    protected final InteractionElement rideInteraction;

    public CustomLivingEntityHolderWithRideOffset(T parent, Model model) {
        super(parent, model);

        this.rideInteraction = new InteractionElement();
        this.rideInteraction.setInvisible(true);
        this.addElement(this.rideInteraction);
    }

    protected float getRideOffset() {
        return (float) (this.parent.getBbHeight() + this.parent.getMyRidingOffset());
    }

    @Override
    protected void startWatchingExtraPackets(ServerGamePacketListenerImpl player, Consumer<Packet<ClientGamePacketListener>> consumer) {
        super.startWatchingExtraPackets(player, consumer);

        for (var packet : Utils.updateClientInteraction(this.rideInteraction, ZERO, this.getRideOffset())) {
            consumer.accept(packet);
        }
    }

    @Override
    protected void addDirectPassengers(IntList passengers) {
        super.addDirectPassengers(passengers);
        passengers.add(this.rideInteraction.getEntityId());
    }

    @Override
    public void onDimensionsUpdated(EntityDimensions dimensions) {
        super.onDimensionsUpdated(dimensions);
        this.sendPacket(new ClientboundBundlePacket(Utils.updateClientInteraction(this.rideInteraction, ZERO, this.getRideOffset())));
    }

    @Override
    public int getVehicleId() {
        return this.rideInteraction.getEntityId();
    }
}