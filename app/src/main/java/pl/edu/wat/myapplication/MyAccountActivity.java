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
    TextView  okno;
    int REQUEST_CODE;

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
        setContentView(R.layout.activity_my_account);
        avatar = (ImageView) findViewById(R.id.imageView2);
        okno = (TextView) findViewById(R.id.Awatar);
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








}
