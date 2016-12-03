package vishal.alumini_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
/*----------------------------------------------*/
    public void guest(View view) {
        Intent guest = new Intent(Login.this,Guest.class);
        startActivity(guest);

    }

    public void register(View view) {
        Intent register = new Intent(Login.this,RegisterdHome.class);
        startActivity(register);
    }
    /*-----------------------------------*/
}
