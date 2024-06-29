package de.tomalbrc.immersiveaircraftpatch;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.sgui.api.gui.SimpleGui;
import immersive_aircraft.entity.InventoryVehicleEntity;
import immersive_aircraft.entity.inventory.slots.SlotDescription;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Items;

public class ImmersiveAircraft_patch implements ModInitializer {
    @Override
    public void onInitialize() {
        PolymerResourcePackUtils.addModAssets("immversive_aircraft");
        PolymerResourcePackUtils.markAsRequired();
    }

    public static void openGui(ServerPlayer player, InventoryVehicleEntity vehicleEntity) {
        var gui = new SimpleGui(MenuType.GENERIC_9x6, player, false);
        gui.open();

        for (int i = 0; i < 9 * 6; i++) {
            gui.setSlot(i, Items.BLUE_STAINED_GLASS_PANE.getDefaultInstance());
        }

        int i = 5;
        var inventory = vehicleEntity.getInventoryDescription().getSlots("inventory");
        for (SlotDescription slotDescription : inventory) {
            gui.setSlotRedirect(i, slotDescription.getSlot(vehicleEntity, vehicleEntity));
            i++;
            if (i % 9 < 5) i += 5;
        }

        var boiler = vehicleEntity.getInventoryDescription().getSlots("boiler");
        if (!boiler.isEmpty()) gui.setSlotRedirect(9*1, boiler.get(0).getSlot(vehicleEntity, vehicleEntity));

        var booster = vehicleEntity.getInventoryDescription().getSlots("booster");
        gui.setSlotRedirect(9*3, booster.get(0).getSlot(vehicleEntity, vehicleEntity));

        var weapon = vehicleEntity.getInventoryDescription().getSlots("weapon");
        if (!weapon.isEmpty()) gui.setSlotRedirect(11, weapon.get(0).getSlot(vehicleEntity, vehicleEntity));
        var banner = vehicleEntity.getInventoryDescription().getSlots("banner");
        if (!banner.isEmpty()) gui.setSlotRedirect(12, banner.get(0).getSlot(vehicleEntity, vehicleEntity));

        i = 20;
        var upgrade = vehicleEntity.getInventoryDescription().getSlots("upgrade");
        if (!upgrade.isEmpty()) for (SlotDescription slotDescription : upgrade) {
            gui.setSlotRedirect(i, slotDescription.getSlot(vehicleEntity, vehicleEntity));
            i++;
            if (i % 9 < 2) i += 2;
            if (i % 9 > 3) i += 7;
        }
    }
}
