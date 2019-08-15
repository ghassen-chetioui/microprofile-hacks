package io.github.cgh;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config")
public class ConfigEntity {

    @Id
    public String name;
    public String value;
    
}