package com.example.tonihuotari.viaplayworksample.db;

import android.os.AsyncTask;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.example.tonihuotari.viaplayworksample.models.Page;
import com.example.tonihuotari.viaplayworksample.sync.SyncBus;

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

    public static void savePage(final Page page, final boolean saveSections) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                DBPage p = new DBPage(page);

                if(saveSections) {
                    DBSection.saveSections(page.getSections());
                }

                p.save();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                //Broadcasting page available event
                SyncBus.getBus().post(new SyncBus.PageAvailableEvent(page.getSectionId()));
            }
        }.execute();
    }

    public static void loadPage(final String sectionId, final boolean withSections, final DBCallback<Page> cb) {
        new AsyncTask<Void, Void, Page>() {

            @Override
            protected Page doInBackground(Void... params) {
                DBPage dPage = new Select().from(DBPage.class).where("sectionId = ?", sectionId != null ? sectionId : ROOT_ID).executeSingle();

                if(dPage != null) {
                    Page page = dPage.toPage();

                    if(withSections) {
                        page.setSections(DBSection.loadSections());
                    }

                    return page;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Page page) {
                if(page != null) {
                    cb.onResult(page);
                } else {
                    cb.onFailed();
                }

            }
        }.execute();
    }

}
