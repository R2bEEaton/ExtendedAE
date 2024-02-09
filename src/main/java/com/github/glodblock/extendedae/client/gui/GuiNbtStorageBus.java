package com.github.glodblock.extendedae.client.gui;

import appeng.api.config.AccessRestriction;
import appeng.api.config.ActionItems;
import appeng.api.config.Settings;
import appeng.api.config.StorageFilter;
import appeng.api.config.YesNo;
import appeng.client.gui.implementations.UpgradeableScreen;
import appeng.client.gui.style.PaletteColor;
import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.widgets.ActionButton;
import appeng.client.gui.widgets.ServerSettingToggleButton;
import appeng.client.gui.widgets.SettingToggleButton;
import appeng.core.localization.GuiText;
import com.github.glodblock.extendedae.container.ContainerNbtStorageBus;
import com.github.glodblock.extendedae.network.EAENetworkServer;
import com.github.glodblock.extendedae.network.packet.CGenericPacket;
import com.github.glodblock.extendedae.network.packet.sync.IActionHolder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public class GuiNbtStorageBus extends UpgradeableScreen<ContainerNbtStorageBus> implements IActionHolder {

    private final Map<String, Consumer<Object[]>> actions = new Object2ObjectOpenHashMap<>();
    private final SettingToggleButton<AccessRestriction> rwMode;
    private final SettingToggleButton<StorageFilter> storageFilter;
    private final SettingToggleButton<YesNo> filterOnExtract;

    public GuiNbtStorageBus(ContainerNbtStorageBus menu, Inventory playerInventory, Component title, ScreenStyle style) {
        super(menu, playerInventory, title, style);
        this.widgets.addOpenPriorityButton();
        addToLeftToolbar(new ActionButton(ActionItems.WRENCH, btn -> menu.partition()));
        this.rwMode = new ServerSettingToggleButton<>(Settings.ACCESS, AccessRestriction.READ_WRITE);
        this.storageFilter = new ServerSettingToggleButton<>(Settings.STORAGE_FILTER, StorageFilter.EXTRACTABLE_ONLY);
        this.filterOnExtract = new ServerSettingToggleButton<>(Settings.FILTER_ON_EXTRACT, YesNo.YES);
        this.addToLeftToolbar(this.storageFilter);
        this.addToLeftToolbar(this.filterOnExtract);
        this.addToLeftToolbar(this.rwMode);
        EAENetworkServer.INSTANCE.sendToServer(new CGenericPacket("update"));
    }

    @Override
    protected void updateBeforeRender() {
        super.updateBeforeRender();
        this.storageFilter.set(this.menu.getStorageFilter());
        this.rwMode.set(this.menu.getReadWriteMode());
        this.filterOnExtract.set(this.menu.getFilterOnExtract());
    }

    @Override
    public void drawFG(GuiGraphics guiGraphics, int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawFG(guiGraphics, offsetX, offsetY, mouseX, mouseY);
        var poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(10, 17, 0);
        poseStack.scale(0.6f, 0.6f, 1);
        var color = style.getColor(PaletteColor.DEFAULT_TEXT_COLOR);
        if (menu.getConnectedTo() != null) {
            guiGraphics.drawString(font, GuiText.AttachedTo.text(menu.getConnectedTo()), 0, 0, color.toARGB(), false);
        } else {
            guiGraphics.drawString(font, GuiText.Unattached.text(), 0, 0, color.toARGB(), false);
        }
        poseStack.popPose();
    }

    @Override
    protected void init() {
        super.init();
    }

    @NotNull
    @Override
    public Map<String, Consumer<Object[]>> getActionMap() {
        return this.actions;
    }
}