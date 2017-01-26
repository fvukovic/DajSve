package hr.foi.air.dajsve.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;

import hr.foi.air.dajsve.Helpers.Baza;
import hr.foi.air.dajsve.R;

/**
 * Created by Filip on 26.1.2017..
 */

public class AdminLoginFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.admin_login_fragment, container, false);
        final EditText korisnickoIme = (EditText)rootView.findViewById(R.id.korime);
        final EditText lozinka = (EditText)rootView.findViewById(R.id.lozinka);

        rootView.findViewById(R.id.admin_prijava_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Baza baza = new Baza();


                boolean prijavljeni = false;
                try {
                    prijavljeni = baza.Prijava(korisnickoIme.getText().toString(), lozinka.getText().toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if(prijavljeni){
                    Toast.makeText(getContext(), "Uspješno ste se prijavili", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Neuspješna prijava", Toast.LENGTH_LONG).show();
                }


            }
        });

        return rootView;
    }

}
