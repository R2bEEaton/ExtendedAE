package com.github.glodblock.extendedae.common.me.nbtlist;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.util.prioritylist.IPartitionList;

import java.util.List;

public class NbtPriorityList implements IPartitionList {

    public NbtPriorityList() {
    }

    @Override
    public boolean isListed(AEKey input) {
        if (input instanceof AEItemKey itemKey) {
            return itemKey.hasTag();
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterable<AEKey> getItems() {
        return List.of();
    }

}
