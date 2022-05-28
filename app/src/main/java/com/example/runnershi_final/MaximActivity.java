package com.example.runnershi_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MaximActivity extends AppCompatActivity {

    TextView tvMaxim, tvPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maxim);
        int cnt;
        ArrayList<String> mList = new ArrayList<>();
        ArrayList<String> pList = new ArrayList<>();
        mList.add("성공은 최종적인 게 아니며\n실패는 치명적인 게 아니다\n중요한 것은 지속하고지 하는 용기다.");
        mList.add("한 차례의 패배를\n최후의 패배로 혼동하지 말라.");
        mList.add("다른 사람이 무엇을 하는지 신경 쓰지 말라.\n더 나은 당신이 되기 위해 노력하고\n매일 당신의 기록을 꺠뜨려라.");
        mList.add("힘겨운 상환에 처하고 모든 게 장애로 느껴질 때,\n단 1분도 더 버틸 수 없다고 느껴질 때,\n포기해서는 안 된다.\n바로 그런 시점과 위치에서\n상황은 바뀌기 시작하니까.");
        mList.add("나는 이룰때 까지 노력할 것이다.");

        pList.add("-윈스턴 처칠-");
        pList.add("-F.스콧 피츠제럴드-");
        pList.add("-윌리엄 보엣커-");
        pList.add("-해리엇 비처 스토우-");
        pList.add("-브라이언 트레이시-");

        tvMaxim = findViewById(R.id.tv_maxim);
        tvPerson = findViewById(R.id.tv_person);

        Random random = new Random();
        cnt = random.nextInt(5);

        tvMaxim.setText(mList.get(cnt));
        tvPerson.setText(pList.get(cnt));

    }
}