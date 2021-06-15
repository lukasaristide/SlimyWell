package com.lukasaristide.icytower;

import java.util.List;

public interface Database {
    public abstract List<Integer> get();
    public abstract void insert(List<Integer> list);
    public abstract void clear();
}
