package com.example.aop;

import lombok.Data;
import lombok.RequiredArgsConstructor;


import java.util.concurrent.ConcurrentHashMap;

@Data
@RequiredArgsConstructor
public class Calls {
    private ConcurrentHashMap<String, Integer> map;

    public Calls(ConcurrentHashMap<String, Integer> map) {
        this.map = map;
    }
}
