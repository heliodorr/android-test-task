package ru.cft.cfttesttask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import ru.cft.cfttesttask.adapters.CurrencyAdapter;
import ru.cft.cfttesttask.model.CurrencyModel;
import ru.cft.cfttesttask.model.CurrencyModel.CurrencyItem;

import java.util.ArrayList;
import java.util.Map;

import static ru.cft.cfttesttask.network.GetCurrencyData.getData;

import ru.cft.cfttesttask.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    CurrencyAdapter currencyAdapter = new CurrencyAdapter();
    CurrencyModel model = new CurrencyModel();
    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;

    ImageButton button;


    public class GetDataTask extends AsyncTask<Void, Void, Map<String, CurrencyItem>> {

        @Override
        protected Map<String, CurrencyItem> doInBackground(Void... voids) {
            return getData();
        }

        @Override
        protected void onPostExecute( Map<String, CurrencyItem> dataMap) {
            model.setData(dataMap);
            currencyAdapter.refresh(model.getDataList());

            arrayAdapter.clear();
            arrayAdapter.addAll(model.getStringList());
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(currencyAdapter);

        ImageButton button = findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetDataTask().execute();
            }
        });

        arrayAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                new ArrayList<>());
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);

        new GetDataTask().execute();

        Handler handler = new Handler();
        long refreshTime = 15 * 60 * 1000;
        handler.post(new Runnable() {
            @Override
            public void run() {
                new GetDataTask().execute();
                handler.postDelayed(this, refreshTime);

            }
        });
        

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = (String) adapterView.getSelectedItem();
        model.changeOutputCurrency(item);
        currencyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}