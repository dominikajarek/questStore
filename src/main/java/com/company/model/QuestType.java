package com.company.model;

import java.util.HashMap;
import java.util.Map;

public enum QuestType {

    EASY(1),
    MODERATE(2),
    DIFFICULT(3);

    private int id;

    QuestType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private static final Map<Integer, QuestType> lookup = new HashMap<>();

    static {
        for (QuestType env : QuestType.values()) {
            lookup.put(env.getId(), env);
        }
    }
    public static QuestType get(int id) {
        return lookup.get(id);
    }
}
