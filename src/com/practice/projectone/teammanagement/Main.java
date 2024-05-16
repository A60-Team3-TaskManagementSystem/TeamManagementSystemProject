package com.practice.projectone.teammanagement;

import com.practice.projectone.teammanagement.core.TMSEngineImpl;
import com.practice.projectone.teammanagement.core.contracts.TMSEngine;

public class Main {
    public static void main(String[] args) {
        TMSEngine engine = new TMSEngineImpl();

        engine.start();
    }
}