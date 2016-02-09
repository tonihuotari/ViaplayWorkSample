package com.example.tonihuotari.viaplayworksample.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.tonihuotari.viaplayworksample.models.Section;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Section")
public class DBSection extends Model {

    @Column(name = "_id")
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "href")
    private String href;
    @Column(name = "type")
    private String type;
    @Column(name = "name")
    private String name;
    @Column(name = "templated")
    private String templated;

    public DBSection() {}

    public DBSection(Section section) {
        this.id = section.getId();
        this.title = section.getTitle();
        this.href = section.getHref();
        this.type = section.getType();
        this.name = section.getName();
    }

    Section toSection() {
        return new Section(id, title, href, type, name, templated);
    }

    static void saveSections(List<Section> sections) {
        new Delete().from(DBSection.class).execute();

        for(Section section : sections) {
            DBSection s = new DBSection(section);
            s.save();
        }
    }

    static List<Section> loadSections() {
        List<DBSection> dSections = new Select()
                .from(DBSection.class)
                .execute();

        List<Section> sections = new ArrayList<>();
        for(DBSection dSection : dSections) {
            sections.add(dSection.toSection());
        }

        return sections;
    }


}
