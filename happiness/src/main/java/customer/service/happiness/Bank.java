package customer.service.happiness;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Bank{

  private Long id;
  private String name = "";
  private String code = "";

  public Bank(){

  }

  public Bank(String name, String code){
    setName(name);
    setCode(code);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return this.id;
  }

  public void setId(Long id){

    this.id = id;
  }

  @Column(unique=true, nullable=false)
  public String getName(){
    return this.name;
  }
  public void setName(String name){
    this.name = name;
  }

  @Column(unique=true, nullable=false)
  public String getCode(){
    return this.code;
  }
  public void setCode(String code){
    this.code = code;
  }


  public String toString(){
    return this.getName();
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Bank))
      return false;
    Bank bank = (Bank) o;
    return Objects.equals(this.id, bank.id);
  }

}
