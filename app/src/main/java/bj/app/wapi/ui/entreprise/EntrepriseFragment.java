package bj.app.wapi.ui.entreprise;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import adapter.ExploitationAdapter;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import api.RetrofitClient;
import bj.app.wapi.ui.NewChampsActivity;
import bj.app.wapi.R;
import entityBackend.Champs;
import entityBackend.ChampsLocation;
import entityBackend.Depense;
import entityBackend.Employee;
import entityBackend.Farmer;
import entityBackend.SaisonCulture;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntrepriseFragment extends Fragment {

    RecyclerView recyclerView;
    ExploitationAdapter adapter;
    List<Champs> mData = new ArrayList<>();
    List<ChampsLocation> mDataLoacation = new ArrayList<>();
    List<SaisonCulture> mDataSaison = new ArrayList<>();
    List<Employee> mDataEmployee = new ArrayList<>();
    List<Depense> mDataDepense = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    Farmer famer;


    @Override
    public void onStart() {
        super.onStart();
        loadRessoucesChamps();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_entreprise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floatingActionButton = view.findViewById(R.id.fab_exploitation);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewChampsActivity.class));
            }
        });
        recyclerView = view.findViewById(R.id.rv_exploitation);
        loadRessoucesChamps();
    }

    private void loadRessoucesChamps() {
        //System.out.println("User id is now  :"+FirebaseAuth.getInstance().getUid());
        Call<Farmer> call = RetrofitClient
                .getmInstance()
                .getApi()
                .getAllChamps(FirebaseAuth.getInstance().getUid()); // "XsVVqDCcw4dsuKsg3f2cGSz7EfB3"

        call.enqueue(new Callback<Farmer>() {
            @Override
            public void onResponse(Call<Farmer> call, Response<Farmer> response) {
                try {
                    if (response.code() == 200){

                        famer = response.body();

                        //for (int i=0; i<list.size(); i++){
                            System.out.println("Result Champs : "+famer.toString());
                        //}

                        mData = famer.getChamps();
                        adapter = new ExploitationAdapter(EntrepriseFragment.this.getActivity(), mData);
                        recyclerView.setAdapter(adapter);
                        //recyclerView.addItemDecoration(new DividerItemDecoration(EntrepriseFragment.this.getContext(), LinearLayoutManager.VERTICAL));
                        recyclerView.setLayoutManager(new LinearLayoutManager(EntrepriseFragment.this.getContext()));
                        adapter.notifyDataSetChanged();

                        //JSONArray jsonArray = new JSONArray(response.body().toString());
                        //mData = response.body();
                        //System.out.println("Result is in string : "+jsonArray);
                        //System.out.println("Result is in jsonArray : "+jsonArray);
                        //if (mData != null)
                          //  adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getActivity(), "Response code is :"+response.code()+"\n"+" S_Response message "+response.message(), Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Farmer> call, Throwable t) {
                Toast.makeText(getActivity(), "Error message "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



        /*mDataLoacation = new ArrayList<>();
        mDataEmployee = new ArrayList<>();
        mDataDepense = new ArrayList<>();
        mDataSaison = new ArrayList<>();
        mData = new ArrayList<>();*/

        /*mDataLoacation.add(new ChampsLocation(10.33, 12.558));
        mDataLoacation.add(new ChampsLocation(10.33, 12.558));
        mDataLoacation.add(new ChampsLocation(10.33, 12.558));
        mDataLoacation.add(new ChampsLocation(10.33, 12.558));*/

        /*mDataEmployee.add(new Employee(1L, "Marc Landers",new ArrayList<>()));
        mDataEmployee.add(new Employee(1L, "Tom Landers", new ArrayList<>()));
        mDataEmployee.add(new Employee(1L, "Jimy Landers", new ArrayList<>()));
        mDataEmployee.add(new Employee(1L, "Paul Landers", new ArrayList<>()));

        mDataDepense.add(new Depense(1L,"120000","Pour achat d'intrant"));
        mDataDepense.add(new Depense(1L,"20000","Pour achat de sacs"));
        mDataDepense.add(new Depense(1L,"170000","Pour achat de produit de culture"));

        mDataSaison.add(new SaisonCulture(1L, "Tomate","2020-2021",mDataDepense));
        mDataSaison.add(new SaisonCulture(1L, "Piment","2019-2020",mDataDepense));
        mDataSaison.add(new SaisonCulture(1L, "Pomme","2018-2019",mDataDepense));
        mDataSaison.add(new SaisonCulture(1L, "Patate","2017-2018",mDataDepense));*/

        /*mData.add(new Champs("Cocotomey",false, "Bientôt"));
        mData.add(new Champs("Ouidah",false, "Dans deux jours"));
        mData.add(new Champs("Akpakpa",false, "En juin"));
        mData.add(new Champs("Zè",false, "Le 15 mai"));

        adapter.notifyDataSetChanged();*/

    }
}