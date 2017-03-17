package com.example.hp.pocket.database;

import com.example.hp.pocket.model.Link;

import java.util.List;

public interface ILinkDatabase {
    List<Link> getLinks();

    void add(Link link);

    Link getLink(int id);

    void update(Link link);

    void delete(Link link);
}
