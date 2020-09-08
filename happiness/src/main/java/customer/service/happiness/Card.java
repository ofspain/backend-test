package customer.service.happiness;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Objects;
import java.util.Date;

@Entity
public class Card  implements Serializable{

  private Long id;
  private Scheme scheme = Scheme.VISA;
  private Type type = Type.UNTYPED;
  private Bank bank;

  private String cardNumber;
  private Integer hit = 0;
  private Date expire;

  public Card(){

  }

  public Card(Scheme scheme, Type type, Bank bank, String cardNumber, Date expire){
    this.scheme = scheme;
    this.type = type;
    this.bank = bank;
    this.cardNumber = cardNumber;
    this.expire = expire;
    this.hit = 0;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return this.id;
  }
  public void setId(Long id){

    this.id = id;
  }


  @Enumerated(EnumType.STRING)
  public Type getType(){
    return this.type;
  }

  public void setType(Type type){
    this.type = type;
  }

  @Enumerated(EnumType.STRING)
  public Scheme getScheme(){
    return this.scheme;
  }

  public void setScheme(Scheme scheme){
    this.scheme = scheme;
  }

  @OneToOne
  public Bank getBank(){
    return this.bank;
  }
  public void setBank(Bank bank){
    this.bank = bank;
  }

  @Column(unique=true, nullable=false)
  public String getCardNumber(){
    return this.cardNumber;
  }
  public void setCardNumber(String cardNumber){
    this.cardNumber = cardNumber;
  }

  @Temporal(TemporalType.TIMESTAMP)
  public Date getExpire(){
    return this.expire;
  }
  public void setExpire(Date expire){
    this.expire = expire;
  }


  public Integer getHit(){
    return this.hit;
  }
  public void setHit(Integer hit){
    this.hit = hit;
  }

  public String toString(){
    return String.format("Type is : %s; Expires of number",this.type, this.cardNumber);
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Card))
      return false;
    Card card = (Card) o;
    return Objects.equals(this.id, card.id);
  }


}
