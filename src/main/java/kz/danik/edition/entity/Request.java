package kz.danik.edition.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.List;

@JmixEntity
@Table(name = "EDT_REQUEST")
@Entity(name = "edt_Request")
public class Request extends AbstractDictionary {

    @Column(name = "PRICE", precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "STATUS")
    private String status;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "request")
    private List<ProductRequest> productRequest;

    public List<ProductRequest> getProductRequest() {
        return productRequest;
    }

    public void setProductRequest(List<ProductRequest> productRequest) {
        this.productRequest = productRequest;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestStatus getStatus() {
        return status == null ? null : RequestStatus.fromId(status);
    }

    public void setStatus(RequestStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}