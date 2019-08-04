package entity;

import java.util.List;

public final class AccountBuilder {
    private String email;
    private String linkAvartar;
    private List<Long> activeCodesId;
    private int status;

    private AccountBuilder() {
    }

    public static AccountBuilder anAccount() {
        return new AccountBuilder();
    }

    public AccountBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public AccountBuilder withLinkAvartar(String linkAvartar) {
        this.linkAvartar = linkAvartar;
        return this;
    }

    public AccountBuilder withActiveCodesId(List<Long> activeCodesId) {
        this.activeCodesId = activeCodesId;
        return this;
    }

    public AccountBuilder withStatus(int status) {
        this.status = status;
        return this;
    }

    public Account build() {
        Account account = new Account();
        account.setEmail(email);
        account.setLinkAvartar(linkAvartar);
        account.setActiveCodesId(activeCodesId);
        account.setStatus(status);
        return account;
    }
}
