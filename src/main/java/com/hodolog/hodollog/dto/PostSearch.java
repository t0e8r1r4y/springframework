package com.hodolog.hodollog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.*;

@Getter
@Setter
public class PostSearch {

    private static final int MAX_SIZE = 2000;
    private int page;
    private int size;

    @Builder
    public PostSearch(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public long getOffset() {
        return (long) ( max(1,page) - 1) * Math.min(size,MAX_SIZE);
    }
}
