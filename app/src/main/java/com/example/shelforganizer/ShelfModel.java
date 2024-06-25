//THIS IS SIMPLY THE MODEL OF THE CARD VIEW WITH THE CONTENTS
//LIKE THE IMAGE AND THE COURSE NAME
package com.example.shelforganizer;



public class ShelfModel {
    private String shelf_name;



    private int imageID;





    public ShelfModel(String shelf_name,int imageID) {
        this.shelf_name = shelf_name;
        this.imageID=imageID;


    }
    public ShelfModel()
    {
    }

    public void setShelf_name(String shelf_name) {
        this.shelf_name = shelf_name;
    }

    public String getShelf_name()
    {
        return shelf_name;
    }

    public int getImageID() {
        return imageID;
    }


    public void setImageID(int shelfimg) {
       this.imageID=shelfimg;
    }
}
