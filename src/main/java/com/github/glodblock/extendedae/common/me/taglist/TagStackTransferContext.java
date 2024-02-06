package com.github.glodblock.extendedae.common.me.taglist;

import appeng.api.behaviors.StackTransferContext;
import appeng.api.config.Actionable;
import appeng.api.networking.energy.IEnergySource;
import appeng.api.networking.security.IActionSource;
import appeng.api.networking.storage.IStorageService;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.util.prioritylist.IPartitionList;

// clueless
@SuppressWarnings("UnstableApiUsage")
public class TagStackTransferContext implements StackTransferContext {
    private final IStorageService internalStorage;
    private final IEnergySource energySource;
    private final IActionSource actionSource;
    private final IPartitionList filter;
    private final int initialOperations;
    private int operationsRemaining;
    private boolean isInverted;

    public TagStackTransferContext(IStorageService internalStorage, IEnergySource energySource, IActionSource actionSource, int operationsRemaining, IPartitionList filter) {
        this.internalStorage = internalStorage;
        this.energySource = energySource;
        this.actionSource = actionSource;
        this.filter = filter;
        this.initialOperations = operationsRemaining;
        this.operationsRemaining = operationsRemaining;
    }

    @Override
    public IStorageService getInternalStorage() {
        return internalStorage;
    }

    @Override
    public IEnergySource getEnergySource() {
        return energySource;
    }

    @Override
    public IActionSource getActionSource() {
        return actionSource;
    }

    @Override
    public int getOperationsRemaining() {
        return operationsRemaining;
    }

    @Override
    public void setOperationsRemaining(int operationsRemaining) {
        this.operationsRemaining = operationsRemaining;
    }

    @Override
    public boolean hasOperationsLeft() {
        return operationsRemaining > 0;
    }

    @Override
    public boolean hasDoneWork() {
        return initialOperations > operationsRemaining;
    }

    @Override
    public boolean isKeyTypeEnabled(AEKeyType space) {
        return space == AEKeyType.items() || space == AEKeyType.fluids();
    }

    @Override
    public boolean isInFilter(AEKey key) {
        return filter.isEmpty() || filter.isListed(key);
    }

    @Override
    public IPartitionList getFilter() {
        return filter;
    }

    @Override
    public void setInverted(boolean inverted) {
        isInverted = inverted;
    }

    @Override
    public boolean isInverted() {
        return !filter.isEmpty() && isInverted;
    }

    @Override
    public boolean canInsert(AEItemKey what, long amount) {
        return internalStorage.getInventory().insert(
                what,
                amount,
                Actionable.SIMULATE,
                actionSource) > 0;
    }

    @Override
    public void reduceOperationsRemaining(long inserted) {
        operationsRemaining -= inserted;
    }
}
