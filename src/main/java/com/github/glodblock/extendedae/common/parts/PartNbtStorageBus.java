package com.github.glodblock.extendedae.common.parts;

import appeng.api.parts.IPartItem;
import appeng.api.parts.IPartModel;
import appeng.core.AppEng;
import appeng.items.parts.PartModels;
import appeng.parts.PartModel;
import appeng.util.prioritylist.IPartitionList;
import com.github.glodblock.extendedae.EAE;
import com.github.glodblock.extendedae.common.me.nbtlist.NbtPriorityList;
import com.github.glodblock.extendedae.common.parts.base.PartSpecialStorageBus;
import com.github.glodblock.extendedae.container.ContainerNbtStorageBus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

public class PartNbtStorageBus extends PartSpecialStorageBus {

    public static final ResourceLocation MODEL_BASE = new ResourceLocation(EAE.MODID, "part/nbt_storage_bus_base");

    @PartModels
    public static final IPartModel MODELS_OFF = new PartModel(MODEL_BASE, new ResourceLocation(AppEng.MOD_ID, "part/storage_bus_off"));

    @PartModels
    public static final IPartModel MODELS_ON = new PartModel(MODEL_BASE, new ResourceLocation(AppEng.MOD_ID, "part/storage_bus_on"));

    @PartModels
    public static final IPartModel MODELS_HAS_CHANNEL = new PartModel(MODEL_BASE, new ResourceLocation(AppEng.MOD_ID, "part/storage_bus_has_channel"));

    public PartNbtStorageBus(IPartItem<?> partItem) {
        super(partItem);
    }

    public MenuType<?> getMenuType() {
        return ContainerNbtStorageBus.TYPE;
    }

    @Override
    protected final int getUpgradeSlots() {
        return 2;
    }

    protected IPartitionList createFilter() {
        if (this.filter == null) {
            this.filter = new NbtPriorityList();
        }
        return this.filter;
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
