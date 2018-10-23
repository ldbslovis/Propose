package com.muabe.sample;

import android.util.Log;
import android.widget.Button;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.muabe.propose.Propose;
import com.muabe.propose.action.TestAction;
import com.muabe.propose.combine.Combination;
import com.muabe.propose.combine.Combine;
import com.muabe.propose.combine.TestCombination;
import com.muabe.propose.guesture.TestEvent;

import java.util.ArrayList;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @since 2018-04-30
 */
@Layout(R.layout.main)
public class MainFragment extends UniFragment{
    @GetView
    Button button;



    @Override
    public void onPost() {
        TestCombination e1 = new TestCombination("E1", 1);
        TestCombination e2 = new TestCombination("E2", 4);

        TestCombination e3 = new TestCombination("E3", 5);

        TestCombination e4 = new TestCombination("E4", 3);
        TestCombination e5 = new TestCombination("E5", 7);

        TestCombination e6 = new TestCombination("E6", 2);
        TestCombination e7 = new TestCombination("E7", 1);
        TestCombination e8 = new TestCombination("E8", 10);

        TestCombination e9 = new TestCombination("E9", 2);
        TestCombination e10 = new TestCombination("E10", 6);

        Combination combination =
                Combine.one(
                            Combine.all(
                                    e1,
                                    e2),
                            e3,
                            Combine.one(
                                    e4,
                                    e5,
                                    Combine.one(
                                            e6,
                                            e7,
                                            e8,
                                            Combine.all(
                                                    e9,
                                                    e10)
                                        )
                                )
                            );

        e6.priority = 1;
        ArrayList<Combination> combinations;
        combinations = Combine.scan(combination);
        print(1, combinations);// 1:E8(15)

        e8.compare = 1;
        combinations = Combine.scan(combination);
        print(2, combinations);// 2:E8(4)

        e9.compare = 10;
        combinations = Combine.scan(combination);
        print(3, combinations);// 3:E8(4)

        e8.compare = 0;
        combinations = Combine.scan(combination);
        print(4, combinations);// 4:E9,E10(18<-19)

        e9.compare = 0;
        combinations = Combine.scan(combination);
        print(5, combinations);// 5:E10(6)

        e10.compare = 0;
        e9.compare = 1;
        combinations = Combine.scan(combination);
        print(6, combinations);// 6:E9(6)

        e10.compare = 1;
        e9.compare = 1;
        combinations = Combine.scan(combination);
        print(7, combinations);// 7:E9,E10(6)

        e10.compare = 0;
        e9.compare = 0;
        combinations = Combine.scan(combination);
        print(8, combinations);// 8:E5(18<-21)

        Propose propose = new Propose(getContext());
        button.setOnTouchListener(propose);


        TestAction testAction = new TestAction();
        propose.addActionMudle(testAction);
        TestEvent testEvent = new TestEvent();
        testAction.go(testEvent);
        testAction.go(testEvent);
        testAction.go(testEvent);
        testAction.go(testEvent);
        testAction.go(testEvent);

        Log.e("dd","총:"+testEvent.count);
    }

    void print(int num, ArrayList<Combination> combinations){
        String msg = "";
        for(Combination c : combinations){
            msg += " "+((TestCombination)c).name+"="+((TestCombination)c).compare;
        }
        Log.e("dsf","필터링"+num+":"+msg);
    }
}