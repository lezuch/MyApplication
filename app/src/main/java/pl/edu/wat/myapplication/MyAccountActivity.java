package pl.edu.wat.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import me.iwf.photopicker.PhotoPagerActivity;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;


public class MyAccountActivity extends ActionBarActivity {
    private static final int ALERT_DIALOG1 = 1;
    ImageView avatar;
    Button okno;
    int REQUEST_CODE;
    TextView wiek;
    TextView wzrost;
    TextView waga;

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
        wiek.setText(String.valueOf(loggedUser.getWiek()));
        wzrost.setText(String.valueOf(loggedUser.getWzrost()));
        waga.setText(String.valueOf(loggedUser.getWaga()));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        avatar = (ImageView) findViewById(R.id.imageView2);
        okno = (Button) findViewById(R.id.Awatar);
        wiek =(TextView)findViewById(R.id.textWiek);
        wzrost=(TextView)findViewById(R.id.textWzrost);
        waga=(TextView)findViewById(R.id.textWaga);

        User loggedUser =  SessionUser.getInstance().getUser();
        Realm realm=Realm.getInstance(getApplicationContext());
        RealmResults<User> result2 = realm.where(User.class)
                .equalTo("login", loggedUser.getLogin())
                .findAll();
        wiek.setText(String.valueOf(loggedUser.getWiek()));
        wzrost.setText(String.valueOf(loggedUser.getWzrost()));
        waga.setText(String.valueOf(loggedUser.getWaga()));


        okno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(ALERT_DIALOG1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_account, menu);
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

    public void onAwatar(View view) {
        Realm realm = Realm.getInstance(getApplicationContext());
        showToast("Zmieniono avatar");

        Picasso.with(this)
                .load("http://i.imgur.com/DvpvklR.png")
                .placeholder(R.drawable.ic_drawer)
                .error(R.drawable.icon_128)
                .into(avatar);
        String url="";
        try {
            User loggedUser = SessionUser.getInstance().getUser();
            realm.beginTransaction();
            RealmResults<User> r = realm.where(User.class)
                    .equalTo("login", loggedUser.getLogin()).findAll();
            User user = r.get(0);
            user.setUrlAwatara(url);
            SessionUser.getInstance().getUser().setUrlAwatara(url);
            realm.commitTransaction();
            showToast("Dane do bazy danych");
        } catch (Exception e) {
        }
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        Dialog dialog;
        AlertDialog.Builder builder;
        switch(id) {
            case ALERT_DIALOG1:
                builder = new AlertDialog.Builder(this);
                builder.setMessage("Skąd chcesz wczytać avatar")
                        .setCancelable(false)
                        .setPositiveButton("Internet", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onAwatar(null);
                            }
                        })
                        .setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                PhotoPickerIntent intent = new PhotoPickerIntent(MyAccountActivity.this);
                                intent.setPhotoCount(1);
                                intent.setShowCamera(true);
                                startActivityForResult(intent, REQUEST_CODE);

                            }
                        });

                dialog = builder.create();
                break;
            default:
                dialog = null;
        }
        return dialog;
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                Realm realm = Realm.getInstance(getApplicationContext());
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                Bitmap myBitmap = BitmapFactory.decodeFile(photos.get(0));
                avatar.setImageBitmap(myBitmap);
                String url;
                url=photos.get(0);
                try {
                    User loggedUser = SessionUser.getInstance().getUser();
                    realm.beginTransaction();
                    RealmResults<User> r = realm.where(User.class)
                            .equalTo("login", loggedUser.getLogin()).findAll();
                    User user = r.get(0);
                    user.setUrlAwatara(url);
                    SessionUser.getInstance().getUser().setUrlAwatara(url);
                    realm.commitTransaction();
                    showToast("Dane do bazy danych");
                } catch (Exception e) {
                }
            }
        }
    }

    public void onAktualizujDane(View view) {
        boolean wrapInScrollView = true;
        new MaterialDialog.Builder(this)
                .title("Moje dane")
                .customView(R.layout.custom_view_dane, wrapInScrollView)
                .positiveText("Zatwierdź")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        View view = (View) dialog.getCustomView();
                        EditText age = (EditText) view.findViewById(R.id.wiek);
                        String wiekString ;
                        wiekString= age.getText().toString();
                        int wiek=Integer.parseInt(wiekString);
                        EditText waga = (EditText) view.findViewById(R.id.waga);
                        String wagaString ;
                        wagaString= waga.getText().toString();
                        float weight=Float.parseFloat(wagaString);
                        EditText wzrost = (EditText) view.findViewById(R.id.wzrost);
                        String wzrostString ;
                        wzrostString= wzrost.getText().toString();
                        float height=Float.parseFloat(wzrostString);
                        Realm realm = Realm.getInstance(getApplicationContext());
                      //  realm.beginTransaction();
                        try {
                            User loggedUser = SessionUser.getInstance().getUser();
                            realm.beginTransaction();
                            RealmResults<User> r = realm.where(User.class)
                                    .equalTo("login", loggedUser.getLogin()).findAll();
                            User user = r.get(0);
                            user.setWaga(weight);
                            user.setWiek(wiek);
                            user.setWzrost(height);
                            SessionUser.getInstance().getUser().setWaga(weight);
                            SessionUser.getInstance().getUser().setWiek(wiek);
                            SessionUser.getInstance().getUser().setWzrost(height);
                            realm.commitTransaction();
                            showToast("Dane zaktualizowane");
                        } catch (Exception e) {
                        }
                    }
                    })
                    .negativeText("Anuluj")
                    .show();
                    }
                }
