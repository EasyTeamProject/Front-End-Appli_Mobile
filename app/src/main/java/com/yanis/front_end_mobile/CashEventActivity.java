package com.yanis.front_end_mobile;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CashEventActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_event);
        tabLayout=(TabLayout)findViewById(R.id.tablayout_id);
        viewPager=(ViewPager)findViewById(R.id.viewpager_id);

        ViewPageAdapter viewPageAdapter=new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new RefundFragment(),"Refund");
        viewPageAdapter.addFragment(new ExpensesFragment(),"Expenses");

        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    public void onAddRefundPressed(View view){
        Intent i =new Intent(this,AddRefundActivity.class);
        startActivity(i);
    }

    public void onAddExpensePressed(View view){
        Intent i =new Intent(this,AddExpenseActivity.class);
        startActivity(i);
    }
}
