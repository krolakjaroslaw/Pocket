package com.example.hp.pocket.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hp.pocket.model.Link;
import com.example.hp.pocket.LinksAdapter;
import com.example.hp.pocket.api.LinksApiFactory;
import com.example.hp.pocket.R;
import com.example.hp.pocket.model.SettingsPreferences;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PocketListActivity extends AppCompatActivity implements LinksAdapter.ActionListener,
        PopupMenu.OnMenuItemClickListener {
    @BindView(R.id.activity_pocket_list)
    RecyclerView mList;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private Link mLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocket_list);
        ButterKnife.bind(this);

        // wyswietlanie elementow w pionie, jeden za drugim
        mList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshList();
    }

    private void refreshList() {
        LinksApiFactory.get().getLinks().enqueue(new Callback<List<Link>>() {
            @Override
            public void onResponse(Call<List<Link>> call, Response<List<Link>> response) {
                SettingsPreferences prefs = new SettingsPreferences(PocketListActivity.this);
                List<Link> links = response.body();
                if (prefs.isSort()) {
                    // domyslnie lista links jest posortowana malejaco
                    // nie potrzebujemy specjalnie jej sortowac, wystarczy odwrocic kolejnosc elementow
                    Collections.reverse(links);
                }

                for (Iterator<Link> it = links.iterator(); it.hasNext();) {
                    Link element = it.next();
                    boolean shouldRemoveLink = element.getType() == Link.TYPE_LINK && !prefs.isShowLinks();
                    boolean shouldRemovePhone = element.getType() == Link.TYPE_PHONE && !prefs.isShowPhones();
                    if (shouldRemoveLink || shouldRemovePhone) {
                        it.remove();
                    }
                }

                mList.setAdapter(new LinksAdapter(links, PocketListActivity.this));
            }

            @Override
            public void onFailure(Call<List<Link>> call, Throwable t) {
                Toast.makeText(PocketListActivity.this, "Błąd pobierania listy",
                        Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.fab)
    void onFabClick() {
        Intent intent = new Intent(this, CreateElementActivity.class);
        startActivity(intent);
    }

    @OnLongClick(R.id.fab)
    boolean onFabLongClick() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onRowLongClick(Link link) {
        LinksApiFactory.get().deleteLink(link.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                refreshList();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(PocketListActivity.this,
                        "Błąd usuwania !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRowClick(Link link) {
        Intent intent = new Intent(this, EditElementActivity.class);
        intent.putExtra(EditElementActivity.EXTRA_LINK, link);
        startActivity(intent);
    }

    @Override
    public void onActionClick(View anchor, Link link) {
        mLink = link;

        if (link.getType() == link.TYPE_LINK) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.getReference()));
            startActivity(intent);
        } else if (link.getType() == link.TYPE_PHONE) {
            PopupMenu menu  = new PopupMenu(this, anchor);
            menu.setGravity(Gravity.RIGHT);
            menu.inflate(R.menu.action_menu);
            menu.setOnMenuItemClickListener(this);
            menu.show();

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_call) {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:"+mLink.getReference()));
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_sms) {
            Intent intent = new Intent(Intent.ACTION_SENDTO,
                    Uri.parse("sms:"+mLink.getReference()));
            intent.putExtra("sms_body", "Wysłano ze szkolenia !");
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_refresh) {
            refreshList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
