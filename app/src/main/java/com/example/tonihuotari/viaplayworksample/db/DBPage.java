package com.example.tonihuotari.viaplayworksample.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.example.tonihuotari.viaplayworksample.models.Page;
import com.example.tonihuotari.viaplayworksample.models.Section;

@Table(name = "Page")
public class DBPage extends Model {

    private final static String ROOT_ID = "-1";

    @Column(name = "type")
    private String type;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "sectionId")
    private String sectionId;

    public DBPage() {}

    public DBPage(Page page) {
        this.type = page.getType();
        this.title = page.getTitle();
        this.description = page.getDescription();
        this.sectionId = page.getSectionId() != null ? page.getSectionId() : ROOT_ID;
    }

    public Page toPage() {
        return new Page(this.type, this.title, this.description, this.sectionId);
    }

    public static void savePage(Page page, boolean saveSections) {
        DBPage p = new DBPage(page);

        if(saveSections) {
            DBSection.saveSections(page.getSections());
        }

        p.save();
    }

    public static Page loadPage(String sectionId, boolean withSections) {
        DBPage dPage = new Select().from(DBPage.class).where("sectionId = ?", sectionId != null ? sectionId : ROOT_ID).executeSingle();

        Page page = dPage.toPage();

        if(withSections) {
            page.setSections(DBSection.loadSections());
        }

        return page;
    }

}
