package customer.service.happiness;

public enum Type {
    UNTYPED("NO SET TYPE"),
    DEBIT("debit"),
    CREDIT("credit"),
    SAVING("saving");
    //

    Type(String name) {
        this.name = name;
    }
    private final String name;


    public String getName() {
        return name;
    }


   public static Type getTypeFromName(String name){
        for(Type t: Type.values())
            if(t.getName().equals(name))
                return t;
        return Type.UNTYPED;
    }
}
