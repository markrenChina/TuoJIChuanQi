package com.example.tuojichuanqi.framework.util;

import com.example.tuojichuanqi.game.model.monster;

import java.util.ArrayList;

public class DamageCalculation {
    public static void implementDamageCalculation(int minAttack, int maxAttack, ArrayList<monster> attMonsters) {
        int actualAttack =RandomNumberGenerator.getRandIntBetween(minAttack,maxAttack);
        for (int i=0;i<attMonsters.size();i++){
            attMonsters.get(i).setHP(attMonsters.get(i).getHP()-actualAttack);
            System.out.println("ActualAttack"+actualAttack);
        }
    }
}
