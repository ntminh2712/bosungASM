package entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id @Index
    private String email;
    private String linkAvartar;
    private List<Long> activeCodesId;
    private int status;


    public Account(String email, String linkAvartar, List<Long> activeCodesId, int status) {
        this.email = email;
        this.linkAvartar = linkAvartar;
        this.activeCodesId = activeCodesId;
        this.status = status;
    }

    public Account() {

        this.status = 0;
        this.activeCodesId = new ArrayList<>();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkAvartar() {
        return linkAvartar;
    }

    public List<Long> getActiveCodesId() {
        return activeCodesId;
    }

    public void setActiveCodesId(List<Long> activeCodesId) {
        this.activeCodesId = activeCodesId;
    }
    public void addActiveCode(long activeCodeId){
        this.activeCodesId.add(activeCodeId);
    }

    public void setLinkAvartar(String linkAvartar) {
        this.linkAvartar = linkAvartar;
    }

    public boolean isDeactive() {
        return this.status == -1;
    }
}
