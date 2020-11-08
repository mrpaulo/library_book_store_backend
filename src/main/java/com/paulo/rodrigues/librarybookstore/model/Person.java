package com.paulo.rodrigues.librarybookstore.model;

import com.paulo.rodrigues.librarybookstore.exceptions.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import static com.paulo.rodrigues.librarybookstore.utils.FormatUtils.isCPF;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author paulo.rodrigues
 */
@Entity
@Table(indexes = {
    @Index(name = "idx_cpf", columnList = "cpf"),
    @Index(name = "idx_name_person", columnList = "name")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class Person implements Serializable {

    @SequenceGenerator(name = "SEQ_PERSON", allocationSize = 1, sequenceName = "person_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSON")
    @Id
    private long id;

    @NotNull
    @Column(length = 100)
    private String name;

    @NotNull
    @Column(unique = true, length = 11)
    private String cpf;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    
    @Column(length = 1)
    private String sex;

    @Column(length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "BIRTH_CITY_ID", referencedColumnName = "ID")
    private City birthCity;

    @ManyToOne
    @JoinColumn(name = "BIRTH_COUNTRY_ID", referencedColumnName = "ID")
    private Country birthCountry;

    @OneToOne
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    private Address adress;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createAt;
    private String createBy;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateAt;
    private String updateBy;

    public void personValidation() throws LibraryStoreBooksException {
        if (name.isEmpty()) {
            throw new LibraryStoreBooksException("Nome deve ser informado!");
        }
        String nuCpf = FormatUtils.removeFormatCPF(cpf);
        if (nuCpf.isEmpty()) {
            throw new LibraryStoreBooksException("CPF deve ser informado!");
        }
        if (!isCPF(nuCpf)) {
            throw new LibraryStoreBooksException("CPF inválido!");
        }
        if (birthdate == null) {
            throw new LibraryStoreBooksException("Data de nascimento inválida, não foi informada ou formato diferente de aaaa-mm-dd!");
        }
        if (birthdate.after(new Date())) {
            throw new LibraryStoreBooksException("Data de nascimento inválida, posterior a hoje!");
        }
        if (!FormatUtils.isEmptyOrNull(email) && email.length() > 100) {
            throw new LibraryStoreBooksException("E-mail com tamanho maior que 100 caracteres!");
        }       
        if (!FormatUtils.isEmptyOrNull(sex) && (sex.length() > 1 || (!sex.equals("M") && !sex.equals("F")))) {
            throw new LibraryStoreBooksException("Somente aceito como sexo M ou F!");
        }
    }

    public void persistAt() {
        if (updateAt == null) {
            setCreateAt(new Date());
            setCreateBy(FormatUtils.getCdUserLogged());
        } else {
            setUpdateAt(new Date());
            setUpdateBy(FormatUtils.getCdUserLogged());
        }
    }

}
