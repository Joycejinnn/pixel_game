package com.example.javagame.GameDesign.Components;

import com.almasb.fxgl.dsl.components.RechargeableIntComponent;
import com.example.javagame.GameDesign.Config;

public class LevelComponent extends RechargeableIntComponent {
    public LevelComponent(){
        super(Config.Weapon_MaxLevel, 0);
    }

    public void upgrade(){
        //upgrade one level and it won't overtake the maximum
        restore(1);
    }

    public  void downgrade(){
        //downgrade one level and it won't overtake the minimum
        damage(1);
    }

    public void gradeFull(){
        restoreFully();
    }
}
