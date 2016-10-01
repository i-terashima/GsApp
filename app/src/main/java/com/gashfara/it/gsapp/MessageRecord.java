package com.gashfara.it.gsapp;

public class MessageRecord {
    //保存するデータ全てを変数で定義します。
    private String imageUrl;
    private String title;
    private String comment;
    private String id;

    //データを１つ作成する関数です。項目が増えたら増やしましょう。
    public MessageRecord(String id,String imageUrl, String title, String comment) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.comment = comment;
        this.id = id;

    }
    //それぞれの項目を返す関数です。項目が増えたら増やしましょう。
    public String getTitle() {
        return title;
    }
    public String getComment() {
        return comment;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getId() {
        return id;
    }
}
