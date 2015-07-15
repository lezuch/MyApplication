package pl.edu.wat.myapplication;

public class ItemData {
    private String name;
    private String surname;
    private String imageUrl;

    public ItemData(String name,String surname,String imageUrl){

        this.name = name;
        this.surname =surname;
        this.imageUrl = imageUrl;
    }


    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }


    public String getImageUrl() {
        return imageUrl;
    }
}
