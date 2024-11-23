package com.hapangama;

import lombok.Data;

@Data
public class Main {
    MainManager mainManager;
    public static void main(String[] args) throws Exception {
        MainManager mainManager = new MainManager();
    }
}