package com.codewithparas.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codewithparas.whatsapp.Model.Users;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    FirebaseUser firebaseUser;
    DatabaseReference myRef;
    ViewPagerAdapter ViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("MyUser").child(firebaseUser
                .getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                Toast.makeText(MainActivity.this, "User Login:"+users.getUserName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

                return true;
        }
        return false;
    }

   class ViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment>fregments;
        private ArrayList<String>titles;

       public ViewPagerAdapter(@NonNull FragmentManager fm) {
           super(fm);
           this.fregments = new ArrayList<>() ;
           this.titles = new ArrayList<>() ;
       }
        public Fragment getItem(int position){
           return fregments.get(position);
        }
        @Override
       public int getCount() {
           return fregments.size(); }

       public void addFragments(Fragment fregment, String title){
        fregments.add(fregment);
        titles.add(title);
       }

       @Nullable
       @Override
       public CharSequence getPageTitle(int position) {
           return super.getPageTitle(position);
       }
   }
}