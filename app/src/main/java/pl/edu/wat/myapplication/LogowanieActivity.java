package pl.edu.wat.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmResults;


public class LogowanieActivity extends ActionBarActivity {

    TextView login;
    ImageView avatar;

    @Override
    protected void onResume() {
        super.onResume();
        User loggedUser =  SessionUser.getInstance().getUser();
        Realm realm=Realm.getInstance(getApplicationContext());
        RealmResults<User> result2 = realm.where(User.class)
                .equalTo("login", loggedUser.getLogin())
                .findAll();
        Bitmap myBitmap = BitmapFactory.decodeFile(result2.get(0).getUrlAwatara());
        avatar.setImageBitmap(myBitmap);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);
        String imie;
        imie=getIntent().getStringExtra("name");
        login=(TextView) findViewById(R.id.login);
        login.setText(imie);
        avatar=(ImageView)findViewById(R.id.AwatarLogowanie);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logowanie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.About) {
            new MaterialDialog.Builder(this)
                    .title("About")
                    .content("Wersja beta\n" +
                            "Tytuł:Workout of the Day \n" +
                            "Autor: Mateusz Leżuch\n" +
                            "Praktyka semestralna")
                    .positiveText("Ok")
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPowrot(View view) {

        Intent intent1=new Intent(this, MainActivity.class);
        startActivity(intent1);
    }

    public void onDalej(View view) {
        Intent intent1=new Intent(this, DrawerActivity.class);
        startActivity(intent1);

    }

    public void onLogoutClick(MenuItem item) {
        Intent intent2=new Intent(this, MainActivity.class);
        startActivity(intent2);

    }

    public void onMyAccountClick(MenuItem item) {
        Intent intent=new Intent(this, MyAccountActivity.class);
        startActivity(intent);

    }


}
