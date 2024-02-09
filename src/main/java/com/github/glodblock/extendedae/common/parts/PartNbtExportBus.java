package com.github.glodblock.extendedae.common.parts;

import appeng.api.behaviors.StackTransferContext;
import appeng.api.networking.energy.IEnergyService;
import appeng.api.networking.storage.IStorageService;
import appeng.api.parts.IPartItem;
import appeng.api.parts.IPartModel;
import appeng.core.AppEng;
import appeng.items.parts.PartModels;
import appeng.parts.PartModel;
import appeng.util.prioritylist.IPartitionList;
import com.github.glodblock.extendedae.EAE;
import com.github.glodblock.extendedae.common.me.nbtlist.NbtPriorityList;
import com.github.glodblock.extendedae.common.me.nbtlist.NbtStackTransferContext;
import com.github.glodblock.extendedae.common.parts.base.PartSpecialExportBus;
import com.github.glodblock.extendedae.container.ContainerNbtExportBus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class PartNbtExportBus extends PartSpecialExportBus {

    public static final ResourceLocation MODEL_BASE = new ResourceLocation(EAE.MODID, "part/nbt_export_bus_base");

    @PartModels
    public static final IPartModel MODELS_OFF = new PartModel(MODEL_BASE,
            new ResourceLocation(AppEng.MOD_ID, "part/export_bus_off"));

    @PartModels
    public static final IPartModel MODELS_ON = new PartModel(MODEL_BASE,
            new ResourceLocation(AppEng.MOD_ID, "part/export_bus_on"));

    @PartModels
    public static final IPartModel MODELS_HAS_CHANNEL = new PartModel(MODEL_BASE,
            new ResourceLocation(AppEng.MOD_ID, "part/export_bus_has_channel"));

    public PartNbtExportBus(IPartItem<?> partItem) {
        super(partItem);
    }

    @Override
    protected MenuType<?> getMenuType() {
        return ContainerNbtExportBus.TYPE;
    }

    @NotNull
    protected StackTransferContext createTransferContext(IStorageService storageService, IEnergyService energyService) {
        return new NbtStackTransferContext(
                storageService,
                energyService,
                this.source,
                getOperationsPerTick(),
                createFilter()
        );
    }

    @Override
    protected IPartitionList createFilter() {
        if (this.filter == null) {
            this.filter = new NbtPriorityList();
        }
        return this.filter;
    }

    @Override
    protected final int getUpgradeSlots() {
        return 4;
    }

    @Override
    public IPartModel getStaticModels() {
        if (this.isActive() && this.isPowered()) {
            return MODELS_HAS_CHANNEL;
        } else if (this.isPowered()) {
            return MODELS_ON;
        } else {
            return MODELS_OFF;
        }
    }
}
