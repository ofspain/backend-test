package customer.service.happiness;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.OneToOne;

@Entity
public class Customer{

  private Long id;
  private String name = "";
  private Card card;

  public Customer(){

  }

  public Customer(String name, Card card){
    setName(name);
    setCard(card);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return this.id;
  }

  public void setId(Long id){

    this.id = id;
  }

  @Column(nullable=false)
  public String getName(){
    return this.name;
  }
  public void setName(String name){
    this.name = name;
  }

  @OneToOne
  public Card getCard(){
    return this.card;
  }
  public void setCard(Card card){
    this.card = card;
  }


  public String toString(){
    return this.getName();
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Customer))
      return false;
    Customer customer = (Customer) o;
    return Objects.equals(this.id, customer.id);
  }

}
