package pl.edu.wat.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.View.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MainActivity extends ActionBarActivity {

    MyAdapter mAdapter;
    EditText passwordEditText;
    EditText logEditText;
    TextView title;
    CheckBox rememberMe;
    private Button btnSave;
    private static final String PREFERENCES_SURNAME = "myPreferences";
    private static final String PREFERENCES_TEXT_LOGIN = "textField";
    private static final String PREFERENCES_TEXT_PASSWORD = "textField2";
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passwordEditText= (EditText)  findViewById(R.id.password);
        logEditText=(EditText)findViewById(R.id.loginMain);
        title=(TextView)findViewById(R.id.item_title);
        preferences = getSharedPreferences(PREFERENCES_SURNAME, MainActivity.MODE_PRIVATE);
        btnSave = (Button) findViewById(R.id.register);
        rememberMe=(CheckBox)findViewById(R.id.rememberME);
        restoreData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Realm realm = Realm.getInstance(this);
        RealmResults<User> users = realm.where(User.class).findAll();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(users,this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onNextClick(View view) {


        String login;
        login = logEditText.getText().toString();
        String password;
        password = passwordEditText.getText().toString();

        if (login.isEmpty() || password.isEmpty()) {
            showToast("Wprowadź dane");

        } else {
            Realm realm = Realm.getInstance(getApplicationContext());
            RealmResults<User> result2 = realm.where(User.class)
                    .equalTo("login", login).findAll();
            String passwordToCompare;
            passwordToCompare = result2.get(0).getPassword();
            String name;
            name = result2.get(0).getName();
            String surname;
            surname = result2.get(0).getSurname();
            if (passwordToCompare.equals(password)) {

                User loggedUser = new User();
                loggedUser.setName(name);
                loggedUser.setSurname(surname);
                loggedUser.setLogin(login);
                SessionUser.getInstance().setUser(loggedUser);
                Intent intent = new Intent(this, LogowanieActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
                if (rememberMe.isChecked()) {
                    saveData();
                } else {
                    cleanData();
                }
            } else {

                showToast("Błędne Hasło");
            }
        }
    }
    private void cleanData() {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString(PREFERENCES_TEXT_PASSWORD,"");
        preferencesEditor.putString(PREFERENCES_TEXT_LOGIN,"");
        preferencesEditor.commit();
    }


    private void saveData() {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        String editTextData = passwordEditText.getText().toString();
        preferencesEditor.putString(PREFERENCES_TEXT_PASSWORD, editTextData);
        String editTextData1 = logEditText.getText().toString();
        preferencesEditor.putString(PREFERENCES_TEXT_LOGIN, editTextData1);
        preferencesEditor.commit();
    }
    private void restoreData() {
        String textFromPreferences = preferences.getString(PREFERENCES_TEXT_PASSWORD, "");
        passwordEditText.setText(textFromPreferences);
        String textFromPreferences1 = preferences.getString(PREFERENCES_TEXT_LOGIN, "");
        logEditText.setText(textFromPreferences1);
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public void onRegister(View view) {
        boolean wrapInScrollView = true;
        new MaterialDialog.Builder(this)
                .title("Wprowadż dane")
                .customView(R.layout.custom_view, wrapInScrollView)
                .positiveText("Zarejestruj")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        View view = (View) dialog.getCustomView();
                        EditText editLogin = (EditText) view.findViewById(R.id.login);
                        String login;
                        login = editLogin.getText().toString();
                        EditText editName = (EditText) view.findViewById(R.id.name);
                        String name;
                        name = editName.getText().toString();
                        EditText editSurname = (EditText) view.findViewById(R.id.surname);
                        String surname;
                        surname = editSurname.getText().toString();
                        EditText editPassword = (EditText) view.findViewById(R.id.password);
                        String pass;
                        pass = editPassword.getText().toString();

                        if (login.isEmpty() || name.isEmpty() || pass.isEmpty() || surname.isEmpty()) {
                            showToast("Wszystkie pola muszą być wypełnione");
                        } else {
                            mAdapter.notifyDataSetChanged();
                            Realm realm = Realm.getInstance(getApplicationContext());
                            realm.beginTransaction();
                            RealmResults<User> loginCompare = realm.where(User.class)
                                    .equalTo("login", login).findAll();
                            if (loginCompare.size() > 0) {
                                showToast("Taki login już istnieje");
                            } else {
                                try {
                                    User user = realm.createObject(User.class); // Create a new object
                                    user.setName(name);
                                    user.setSurname(surname);
                                    user.setPassword(pass);
                                    user.setLogin(login);
                                    realm.commitTransaction();
                                    showToast("Dane zapisane");


                                } catch (Exception e) {

                                }
                            }

                        }


                    }

                })
                .show();


    }

    public void onPrzeniesDane(View view) {
          User user = (User) view.getTag();
          logEditText.setText(user.getLogin());
    }
}
