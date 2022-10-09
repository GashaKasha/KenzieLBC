package com.kenzie.appserver;

import com.kenzie.appserver.controller.model.CollectionResponse;
import com.kenzie.appserver.service.model.Collection;

import java.util.Comparator;

public class DateCreatedComparator implements Comparator<Collection> {
    @Override
    public int compare(Collection o1, Collection o2) {
        return o1.getCreationDate().compareTo(o2.getCreationDate());
    }
}
