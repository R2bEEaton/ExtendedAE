package com.github.glodblock.extendedae.container;

import appeng.menu.guisync.GuiSync;
import appeng.menu.implementations.MenuTypeBuilder;
import appeng.menu.implementations.UpgradeableMenu;
import com.github.glodblock.extendedae.common.parts.PartNbtExportBus;
import com.github.glodblock.extendedae.network.EAENetworkServer;
import com.github.glodblock.extendedae.network.packet.SGenericPacket;
import com.github.glodblock.extendedae.network.packet.sync.IActionHolder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public class ContainerNbtExportBus extends UpgradeableMenu<PartNbtExportBus> implements IActionHolder {
    private final Map<String, Consumer<Object[]>> actions = new Object2ObjectOpenHashMap<>();

    public static final MenuType<ContainerNbtExportBus> TYPE = MenuTypeBuilder
            .create(ContainerNbtExportBus::new, PartNbtExportBus.class)
            .build("nbt_export_bus");

    public ContainerNbtExportBus(int id, Inventory ip, PartNbtExportBus host) {
        super(TYPE, id, ip, host);
    }

    @Override
    protected void setupConfig() {
        // NO-OP
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
    }

    @Override
    public boolean isSlotEnabled(int idx) {
        return false;
    }

    @NotNull
    @Override
    public Map<String, Consumer<Object[]>> getActionMap() {
        return this.actions;
    }


}
