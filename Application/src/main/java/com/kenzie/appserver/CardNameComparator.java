package com.kenzie.appserver;

import com.kenzie.appserver.service.model.MagicTheGathering;

import java.util.Comparator;

public class CardNameComparator implements Comparator<MagicTheGathering> {
    @Override
    public int compare(MagicTheGathering o1, MagicTheGathering o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
