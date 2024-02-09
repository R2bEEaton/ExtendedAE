package com.github.glodblock.extendedae.container;

import appeng.api.config.AccessRestriction;
import appeng.api.config.Settings;
import appeng.api.config.StorageFilter;
import appeng.api.config.YesNo;
import appeng.api.util.IConfigManager;
import appeng.menu.guisync.GuiSync;
import appeng.menu.implementations.MenuTypeBuilder;
import appeng.menu.implementations.UpgradeableMenu;
import com.github.glodblock.extendedae.common.parts.PartNbtStorageBus;
import com.github.glodblock.extendedae.network.EAENetworkServer;
import com.github.glodblock.extendedae.network.packet.SGenericPacket;
import com.github.glodblock.extendedae.network.packet.sync.IActionHolder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;

public class ContainerNbtStorageBus extends UpgradeableMenu<PartNbtStorageBus> implements IActionHolder {

    private final Map<String, Consumer<Object[]>> actions = new Object2ObjectOpenHashMap<>();
    private static final String ACTION_PARTITION = "partition";

    public static final MenuType<ContainerNbtStorageBus> TYPE = MenuTypeBuilder
            .create(ContainerNbtStorageBus::new, PartNbtStorageBus.class)
            .build("nbt_storage_bus");

    @GuiSync(3)
    public AccessRestriction rwMode = AccessRestriction.READ_WRITE;

    @GuiSync(4)
    public StorageFilter storageFilter = StorageFilter.EXTRACTABLE_ONLY;

    @GuiSync(7)
    public YesNo filterOnExtract = YesNo.YES;

    @GuiSync(8)
    @Nullable
    public Component connectedTo;

    @GuiSync(9)
    public String exp = "";

    public ContainerNbtStorageBus(int id, Inventory ip, PartNbtStorageBus te) {
        super(TYPE, id, ip, te);

        registerClientAction(ACTION_PARTITION, this::partition);
        this.connectedTo = te.getConnectedToDescription();
    }

    @Override
    protected void setupConfig() {
        // NO-OP
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        this.connectedTo = getHost().getConnectedToDescription();
    }

    @Override
    protected void loadSettingsFromHost(IConfigManager cm) {
        this.setReadWriteMode(cm.getSetting(Settings.ACCESS));
        this.setStorageFilter(cm.getSetting(Settings.STORAGE_FILTER));
        this.setFilterOnExtract(cm.getSetting(Settings.FILTER_ON_EXTRACT));
    }

    @Override
    public boolean isSlotEnabled(int idx) {
        return false;
    }

    public void partition() {
        if (isClientSide()) {
            sendClientAction(ACTION_PARTITION);
            return;
        }
        this.broadcastChanges();
    }

    public AccessRestriction getReadWriteMode() {
        return this.rwMode;
    }

    private void setReadWriteMode(AccessRestriction rwMode) {
        this.rwMode = rwMode;
    }

    public StorageFilter getStorageFilter() {
        return this.storageFilter;
    }

    private void setStorageFilter(StorageFilter storageFilter) {
        this.storageFilter = storageFilter;
    }

    public YesNo getFilterOnExtract() {
        return this.filterOnExtract;
    }

    public void setFilterOnExtract(YesNo filterOnExtract) {
        this.filterOnExtract = filterOnExtract;
    }

    @Nullable
    public Component getConnectedTo() {
        return connectedTo;
    }

    @NotNull
    @Override
    public Map<String, Consumer<Object[]>> getActionMap() {
        return this.actions;
    }
}
