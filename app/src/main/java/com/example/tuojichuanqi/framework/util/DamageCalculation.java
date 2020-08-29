package com.example.tuojichuanqi.framework.util;

import com.example.tuojichuanqi.game.model.monster;

import java.util.ArrayList;

public class DamageCalculation {
    public static void implementDamageCalculation(int MIN_Attack, int MAX_Attack, ArrayList<monster> Att_monsters) {
        int ActualAttack=RandomNumberGenerator.getRandIntBetween(MIN_Attack,MAX_Attack);
        for (int i=0;i<Att_monsters.size();i++){
            Att_monsters.get(i).setHP(Att_monsters.get(i).getHP()-ActualAttack);
            System.out.println("ActualAttack"+ActualAttack);
        }
    }
}
