package customer.service.happiness;

//VISA, MASTERCARD or AMEX
public enum Scheme{

    VISA("visa"),
    MASTERCARD("master card"),
    AMEX("amex");

    Scheme(String name) {
        this.name = name;
    }
    private final String name;


    public String getName() {
        return name;
    }


   public static Scheme getSchemeFromName(String name){
        for(Scheme s: Scheme.values())
            if(s.getName().equals(name))
                return s;
        return null;
    }
}
