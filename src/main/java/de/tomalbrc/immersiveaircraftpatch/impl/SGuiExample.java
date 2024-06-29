package de.tomalbrc.immersiveaircraftpatch.impl;

import eu.pb4.sgui.api.gui.SimpleGui;
import io.github.theepicblock.polymc.api.gui.GuiPoly;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class SGuiExample implements GuiPoly {
    @Override
    public AbstractContainerMenu replaceScreenHandler(AbstractContainerMenu base, ServerPlayer player, int syncId) {
        var gui = new SimpleGui(MenuType.GENERIC_3x3, player, false);
        gui.setTitle(Component.literal("SGui test"));
        gui.open();
        return null;
    }
}