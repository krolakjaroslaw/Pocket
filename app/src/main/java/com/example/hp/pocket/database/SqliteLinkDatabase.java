package com.example.hp.pocket.database;

import android.content.Context;

import com.example.hp.pocket.model.Link;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class SqliteLinkDatabase implements ILinkDatabase {

    private Dao<Link, Integer> mDao;

    public SqliteLinkDatabase(Context context) {
        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(context);
        ConnectionSource connectionSource = new AndroidConnectionSource(openHelper);
        try {
            mDao = DaoManager.createDao(connectionSource, Link.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Link> getLinks() {
        try {
            return mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void add(Link link) {
        try {
            mDao.create(link);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Link getLink(int id) {
        try {
            return mDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Link link) {
        try {
            mDao.update(link);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Link link) {
        try {
            mDao.delete(link);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
