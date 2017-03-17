package com.example.hp.pocket.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.hp.pocket.model.Link;
import com.example.hp.pocket.api.LinksApiFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditElementActivity extends CreateElementActivity {
    public static final String EXTRA_LINK = "id";
    private int mEditId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Link link = (Link) getIntent().getSerializableExtra(EXTRA_LINK);

        if (link == null) {
            // Jezeli nie znaleziono obiektu w tabeli - zamknij ekran i zakoncz dalsze przetwarzanie
            finish();
            return;
        }

        mEditId = link.getId();

        mFormTitle.setText(link.getName());
        mFormReference.setText(link.getReference());
        mFormType.setSelection(getTypeIndex(link.getType()));
    }

    @Override
    protected void saveElement(String title, String reference) {
        LinksApiFactory.get().updateLink(mEditId, title, getSelectedType(), reference)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(EditElementActivity.this, "Wystąpił błąd",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
