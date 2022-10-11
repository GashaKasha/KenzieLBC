package com.kenzie.appserver;

import com.kenzie.appserver.service.model.BoardGame;

import java.util.Comparator;

public class BoardGameNameComparator implements Comparator<BoardGame> {
    @Override
    public int compare(BoardGame o1, BoardGame o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
